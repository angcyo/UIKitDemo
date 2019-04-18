package com.angcyo.uikitdemo.component

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.component.widget.VoiceView
import com.angcyo.uiview.less.base.helper.ViewGroupHelper
import com.angcyo.uiview.less.kotlin.extName
import com.angcyo.uiview.less.kotlin.getDrawable
import com.angcyo.uiview.less.kotlin.noExtName
import com.angcyo.uiview.less.media.RPlayer
import com.angcyo.uiview.less.media.SimplePlayerListener
import com.angcyo.uiview.less.resources.ResUtil
import com.angcyo.uiview.less.widget.ExEditText
import java.io.File

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/04/18
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class RecordLayoutControl(val parent: ViewGroup) {
    private val helper: ViewGroupHelper = ViewGroupHelper.build(parent)

    private val recordControl: RecordControl by lazy {
        RecordControl()
    }
    private val player: RPlayer by lazy {
        RPlayer()
    }

    /**
     * 录制音频的文件
     * */
    var recordFile: File? = null

    /**
     * 录制时长, 毫秒
     * */
    var recordTime = 0L


    init {

        //图示icon
        parent.findViewById<ImageView>(R.id.voice_switch_view)
            .setImageDrawable(ResUtil.colorFilter(getDrawable(R.mipmap.icon_record), Color.BLACK))

        //图示icon
        parent.findViewById<ImageView>(R.id.text_switch_view)
            .setImageDrawable(ResUtil.colorFilter(getDrawable(R.mipmap.icon_kbd), Color.BLACK))

        parent.findViewById<ImageView>(R.id.voice_cancel_view)
            .setImageDrawable(ResUtil.colorFilter(getDrawable(R.mipmap.icon_cancel), Color.BLACK))

        //touch事件监听
        recordControl.wrap(parent.findViewById(R.id.voice_input_view), parent.context as Activity, {
            player.stopPlay()
        }) {
            recordTime = recordControl.recordUI.currentRecordTime
            val time = (recordTime / 1000).toInt()
            recordFile = File(
                it.parent,
                "${it.name.noExtName()}_t_${time}.${it.name.extName()}"
            )
            it.renameTo(recordFile)

            showVoiceLayout()

            parent.findViewById<TextView>(R.id.voice_time_view).text = "$time'"
        }

        //切换布局
        helper.click(R.id.voice_switch_view) {
            showVoiceInputLayout()
        }

        //切换布局
        helper.click(R.id.text_switch_view) {
            cancelRecordFile()
            showTextInputLayout()
        }

        //切换布局
        helper.click(R.id.voice_cancel_view) {
            player.stopPlay()
            showVoiceInputLayout()
        }

        //播放录音
        helper.click(R.id.voice_wrap_layout) {
            if (showUrl != null) {
                player.click(showUrl)
            } else {
                recordFile?.let {
                    if (it.exists()) {
                        player.click(it.absolutePath)
                    }
                }
            }
        }

        //播放状态监听
        player.onPlayListener = object : SimplePlayerListener() {
            override fun onPlayProgress(progress: Int, duration: Int) {
                super.onPlayProgress(progress, duration)
                showPlayTime(progress.toLong())
            }

            override fun onPlayStateChange(playUrl: String, from: Int, to: Int) {
                super.onPlayStateChange(playUrl, from, to)
                if (to == RPlayer.STATE_PLAYING) {
                    parent.findViewById<VoiceView>(R.id.voice_tip_view).play()
                } else {
                    parent.findViewById<VoiceView>(R.id.voice_tip_view).stop()

                    if (urlDuration != -1L) {
                        showPlayTime(urlDuration)
                    } else {
                        showPlayTime(recordTime)
                    }
                }
            }
        }
    }

    fun cancelRecordFile() {
        recordFile = null
        recordTime = 0L
        parent.findViewById<TextView>(R.id.voice_time_view).text = ""
    }

    fun showTextInputLayout() {
        helper.visibilityAllId(View.GONE).visibleId(R.id.text_input_wrap_layout)
    }

    fun showVoiceInputLayout() {
        helper.visibilityAllId(View.GONE).visibleId(R.id.voice_input_wrap_layout)
    }

    fun showVoiceLayout() {
        helper.visibilityAllId(View.GONE).visibleId(R.id.voice_wrap_layout)
    }

    /**
     * 单纯用来展示文本, 可以控制是否允许输入
     * @param noEdit 不可编辑
     * */
    fun singleShowText(text: CharSequence, noEdit: Boolean = true) {
        showTextInputLayout()
        (helper.view(R.id.text_input_edit) as ExEditText).apply {
            setInputText(text)
            isNoEditMode = noEdit
        }
        helper.view(R.id.voice_switch_view)?.visibility = View.GONE
    }

    var showUrl: String? = null
    var urlDuration = -1L
    /**
     * 单纯用来显示播放音频
     * @param url 音频地址
     * @param duration 音频时长, 毫秒
     * */
    fun singleShowVoice(url: String, duration: Long) {
        if (showUrl != null) {
            player.stopPlay()
        }
        showUrl = url
        urlDuration = duration

        showVoiceLayout()
        helper.view(R.id.voice_cancel_view)?.visibility = View.GONE

        showPlayTime(duration)
    }

    fun showPlayTime(time: Long /*毫秒*/) {
        val t = (time / 1000)
        parent.findViewById<TextView>(R.id.voice_time_view).text = "$t'"
    }

    fun release() {
        recordControl.release()
        player.release()
    }
}