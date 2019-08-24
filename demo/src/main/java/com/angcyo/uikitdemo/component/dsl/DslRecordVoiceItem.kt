package com.angcyo.uikitdemo.component.dsl

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.component.PlayControl
import com.angcyo.uikitdemo.component.RecordUI
import com.angcyo.uikitdemo.component.dsl.VoicePlayControl.isPlaying
import com.angcyo.uikitdemo.component.widget.VoiceView
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.kotlin.frameParams
import com.angcyo.uiview.less.kotlin.setWidth
import com.angcyo.uiview.less.media.RPlayer
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem
import com.angcyo.uiview.less.utils.RUtils
import com.luck.picture.lib.entity.LocalMedia

/**
 * 语音展示item
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/08/23
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

open class DslRecordVoiceItem : DslAdapterItem() {
    init {
        itemLayoutId = R.layout.dsl_record_voice
    }

    /**媒体结构*/
    var itemLocalMedia: LocalMedia? = null

    /**媒体时长 s*/
    var itemRecordVoiceDuration = -1

    /**最小item宽度*/
    var itemMinWidth = 80 * dpi

    /**允许的最大录制时长*/
    var maxRecordTime = -1L

    /**可以用来控制显示浮窗播放器*/
    var activity: AppCompatActivity? = null

    /**界面destroy周期监听*/
    var lifecycle: Lifecycle? = null
        get() {
            if (field == null) {
                return activity?.lifecycle
            }
            return field
        }

    /**是否显示删除按钮*/
    var itemShowDelete = false

    /**重力*/
    var itemGravity: Int = Gravity.RIGHT or Gravity.CENTER_VERTICAL

    var onItemDelete: (LocalMedia) -> Boolean = { true }

    private val activityDestroyObserver: ActivityDestroyObserver by lazy {
        ActivityDestroyObserver()
    }

    private val onPlayerStatusChangeListener: (url: String, status: Int) -> Unit = { url, status ->
        if (status == RPlayer.STATE_PAUSE ||
            status == RPlayer.STATE_COMPLETION ||
            status == RPlayer.STATE_STOP
        ) {
            VoicePlayControl.playControl?.onPlayerStatusChangeListener = null
        }
        playStatus = status
        updateAdapterItem()
    }

    private var playStatus = RPlayer.STATE_NORMAL

    override var onItemViewAttachedToWindow: (itemHolder: RBaseViewHolder) -> Unit = {
        lifecycle?.addObserver(activityDestroyObserver)
        itemLocalMedia?.let {
            if (isPlaying(it.loadUrl)) {
                playStatus = RPlayer.STATE_PLAYING
            }
        }
    }

    override var onItemViewDetachedToWindow: (itemHolder: RBaseViewHolder) -> Unit = {
        lifecycle?.removeObserver(activityDestroyObserver)
        if (VoicePlayControl.playControl != null && itemLocalMedia != null) {
            if (VoicePlayControl.playControl?.player?.playUrl == itemLocalMedia?.loadUrl ||
                VoicePlayControl.playControl?.player?.playUrl == PlayControl.getVoiceLocalPath(
                    itemLocalMedia?.loadUrl ?: ""
                )
            ) {
                VoicePlayControl.playControl?.onPlayerStatusChangeListener = null
            }
        }
    }

    override fun onItemBind(
        itemHolder: RBaseViewHolder,
        itemPosition: Int,
        adapterItem: DslAdapterItem
    ) {

        //缓存时长
        if (itemRecordVoiceDuration == -1) {
            itemLocalMedia?.let {
                itemRecordVoiceDuration = RecordUI.getRecordTime(it.loadUrl)
            }
        }

        //恢复播放状态
        itemLocalMedia?.let {
            if (isPlaying(it.loadUrl)) {
                playStatus = RPlayer.STATE_PLAYING

                VoicePlayControl.playControl?.onPlayerStatusChangeListener =
                    onPlayerStatusChangeListener
            }
        }

        itemHolder.tv(R.id.text_view).text = "$itemRecordVoiceDuration\""

        //播放语音
        itemHolder.clickItem {
            itemLocalMedia?.let {
                if (isPlaying(it.loadUrl)) {
                    VoicePlayControl.stop()
                    return@clickItem
                }

                //上一个语音播放状态清除
                VoicePlayControl.playControl?.onPlayerStatusChangeListener?.invoke(
                    it.loadUrl,
                    RPlayer.STATE_STOP
                )

                VoicePlayControl.playVoice(activity, it.loadUrl)

                //监听本次语音播放状态
                VoicePlayControl.playControl?.onPlayerStatusChangeListener =
                    onPlayerStatusChangeListener
            }
        }

        //智能根据录音时长, 设置item的宽度
        var itemWidth = itemMinWidth
        if (itemRecordVoiceDuration > 0 && maxRecordTime > 0) {
            val ratio = itemRecordVoiceDuration * 1f / maxRecordTime
            itemWidth = when {
                ratio < 0.5 -> {
                    (itemMinWidth + RUtils.getScreenWidth() * 3 / 4 * ratio).toInt()
                }
                ratio < 0.8 -> {
                    (itemMinWidth + RUtils.getScreenWidth() / 3 * ratio).toInt()
                }
                else -> {
                    (itemMinWidth + RUtils.getScreenWidth() / 2 * ratio).toInt()
                }
            }
        }
        itemHolder.view(R.id.content_wrap_layout).frameParams {
            width = itemWidth
            gravity = itemGravity
        }

        //播放状态判断
        if (itemLocalMedia == null || VoicePlayControl.playControl == null || playStatus != RPlayer.STATE_PLAYING) {
            itemHolder.v<VoiceView>(R.id.voice_view).stop()
        } else {
            if (playStatus == RPlayer.STATE_PLAYING) {
                itemHolder.v<VoiceView>(R.id.voice_view).play()
            }
        }

        //删除
        itemHolder.visible(R.id.delete_view, itemShowDelete)
        itemHolder.click(R.id.delete_view) {
            itemLocalMedia?.let {
                if (onItemDelete(it)) {
                    itemDslAdapter?.deleteItem(this)
                }
            }
        }
    }

    inner class ActivityDestroyObserver : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun release() {
            VoicePlayControl.release()
        }
    }
}

object VoicePlayControl {
    var playControl: PlayControl? = null

    fun playVoice(activity: Activity?, url: String) {
        if (playControl == null) {
            playControl = PlayControl()
        }
        playControl?.play(activity, url)
    }

    fun stop() {
        playControl?.stop()
    }

    fun release() {
        playControl?.release()
        playControl = null
    }

    fun isPlaying(url: String): Boolean {
        return playControl?.isPlaying(url) == true
    }
}