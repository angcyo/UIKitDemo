package com.angcyo.uikitdemo.component

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import com.angcyo.okdownload.FDown
import com.angcyo.uiview.less.RApplication
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

    companion object {
        /**
         * 请求拿到音频焦点
         */
        fun requestAudioFocus(context: Context) {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.requestAudioFocus(
                null,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
            )//请求焦点
        }

        /**
         * 释放音频焦点
         */
        fun abandonAudioFocus(context: Context) {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.abandonAudioFocus(null)//放弃焦点
        }
    }

    /**
     * 播放状态改变回调
     * */
    var onPlayerStatusChangeListener: ((url: String, status: Int) -> Unit)? = null

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
                        RPlayer.STATE_PLAYING -> {
                            requestAudioFocus(RApplication.getApp())
                            playerUI.setShowStatus(PlayerUI.STATUS_PLAYING)
                        }
                        RPlayer.STATE_COMPLETION -> {
                            abandonAudioFocus(RApplication.getApp())
                            playerUI.setShowStatus(PlayerUI.STATUS_COMPLETE)
                        }
                        RPlayer.STATE_PAUSE -> {
                            abandonAudioFocus(RApplication.getApp())
                            playerUI.setShowStatus(PlayerUI.STATUS_PAUSE)
                        }
                        else -> {
                            abandonAudioFocus(RApplication.getApp())
                            playerUI.setShowStatus(PlayerUI.STATUS_LOADING)
                        }
                    }

                    onPlayerStatusChangeListener?.invoke(playUrl, to)
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
        if (player.isPlaying()) {
            player.pausePlay()
        }
    }

    fun resume() {
        player.resumePlay()
    }

    fun replay() {
        player.replay()
    }

    /**
     * 指定的url, 是否正在播放中
     * */
    fun isPlaying(url: String): Boolean {
        return (player.playUrl == url /*||
                player.playUrl == FDown.Builder.defaultDownloadPath(url)*/) && player.isPlaying()
    }

    fun isPause(url: String): Boolean {
        return (player.playUrl == url /*||
                player.playUrl == FDown.Builder.defaultDownloadPath(url)*/) && player.isPause()
    }

//    fun download(url: String, onFilePath: ((String) -> Unit) = {}) {
//        if (url.startsWith("http")) {
//            FDown.build(url).download(object : FDownListener() {
//                override fun onCompleted(task: BaseDownloadTask) {
//                    super.onCompleted(task)
//                    onFilePath.invoke(task.path)
//                }
//            })
//        } else {
//            File(url).apply {
//                if (exists()) {
//                    onFilePath.invoke(absolutePath)
//                }
//            }
//        }
//    }
}