package com.angcyo.uikitdemo.component

import android.app.Activity
import com.angcyo.uiview.less.media.RPlayer
import com.angcyo.uiview.less.media.SimplePlayerListener

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/04/17
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class PlayControl {
    val playerUI: PlayerUI by lazy {
        PlayerUI().apply {
            onPlayerClickListener = { _, currentStatus ->
                if (currentStatus == PlayerUI.STATUS_PLAYING) {
                    pause()
                } else if (currentStatus == PlayerUI.STATUS_PAUSE) {
                    resume()
                } else if (currentStatus == PlayerUI.STATUS_COMPLETE) {
                    replay()
                }
            }
        }
    }

    val player: RPlayer by lazy {
        RPlayer().apply {
            onPlayListener = object : SimplePlayerListener() {
                override fun onPlayStateChange(playUrl: String, from: Int, to: Int) {
                    super.onPlayStateChange(playUrl, from, to)

                    when (to) {
                        RPlayer.STATE_PLAYING -> playerUI.setShowStatus(PlayerUI.STATUS_PLAYING)
                        RPlayer.STATE_COMPLETION -> playerUI.setShowStatus(PlayerUI.STATUS_COMPLETE)
                        RPlayer.STATE_PAUSE -> playerUI.setShowStatus(PlayerUI.STATUS_PAUSE)
                        else -> playerUI.setShowStatus(PlayerUI.STATUS_LOADING)
                    }
                }
            }
        }
    }

    fun play(activity: Activity, url: String) {
        playerUI.show(activity)
        player.startPlay(url)
    }

    fun release() {
        player.release()
        playerUI.hide()
    }

    fun pause() {
        player.pausePlay()
    }

    fun resume() {
        player.resumePlay()
    }

    fun replay() {
        player.replay()
    }
}