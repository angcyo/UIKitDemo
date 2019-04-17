package com.angcyo.uikitdemo.component

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.component.widget.CircleLoadingView
import com.angcyo.uikitdemo.component.widget.VoiceView
import com.angcyo.uiview.less.base.helper.ViewGroupHelper

/**
 * 播放音频提示UI
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/04/17
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class PlayerUI {
    companion object {
        const val STATUS_LOADING = 0
        const val STATUS_PLAYING = 1
        const val STATUS_PAUSE = 2
        const val STATUS_ERROR = 3
        const val STATUS_COMPLETE = 4
    }

    var parent: ViewGroup? = null
    var rootLayout: ViewGroup? = null

    var currentShowStatus = -1

    var onPlayerClickListener: ((playerUI: PlayerUI, currentStatus: Int) -> Unit)? = null

    /**
     * 显示界面
     * */
    fun show(activity: Activity, status: Int = STATUS_LOADING) {
        if (rootLayout != null) {
            //已经显示
            return
        }
        parent = activity.window.findViewById(Window.ID_ANDROID_CONTENT)

        rootLayout = LayoutInflater.from(activity)
            .inflate(R.layout.layout_player_ui, parent, false) as? ViewGroup

        parent?.addView(rootLayout)

        rootLayout?.setOnClickListener {
            onPlayerClickListener?.invoke(this, currentShowStatus)
        }

        setShowStatus(status)
    }

    /**
     * 隐藏界面
     * */
    fun hide() {
        parent?.removeView(rootLayout)
        rootLayout = null
        parent = null
    }

    /**
     * 切换界面显示状态
     * */
    fun setShowStatus(status: Int) {
        if (currentShowStatus == status) {
            return
        }

        currentShowStatus = status

        val loadingView = rootLayout?.findViewById<CircleLoadingView>(R.id.loading_tip)
        val voiceView = rootLayout?.findViewById<VoiceView>(R.id.voice_tip)

        when (status) {
            STATUS_LOADING -> {
                //资源加载中
                ViewGroupHelper.build(rootLayout).visibilityAllId(
                    View.GONE,
                    R.id.voice_tip
                ).visibleId(R.id.loading_tip, R.id.voice_tip)

                loadingView?.isMultiMode = true
                voiceView?.stop()
            }
            STATUS_PLAYING -> {
                //播放中
                ViewGroupHelper.build(rootLayout).visibilityAllId(
                    View.GONE,
                    R.id.loading_tip
                ).visibleId(R.id.pause_tip, R.id.loading_tip)
                loadingView?.isMultiMode = false
            }
            STATUS_COMPLETE -> {
                //播放完成
                ViewGroupHelper.build(rootLayout).visibilityAllId(
                    View.GONE
                ).visibleId(R.id.play_tip)
            }
            STATUS_PAUSE -> {
                //暂停中
                ViewGroupHelper.build(rootLayout).visibilityAllId(
                    View.GONE
                ).visibleId(R.id.play_tip)
            }
            STATUS_ERROR -> {
                ViewGroupHelper.build(rootLayout).visibilityAllId(
                    View.GONE,
                    R.id.voice_tip
                ).visibleId(R.id.loading_tip)
                voiceView?.stop()
                loadingView?.isMultiMode = true
            }
        }
    }
}