package com.angcyo.uikitdemo.component

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import com.angcyo.okdownload.FDown
import com.angcyo.okdownload.FDownListener
import com.angcyo.uiview.less.RApplication
import com.angcyo.uiview.less.kotlin.isFileExists
import com.angcyo.uiview.less.media.RPlayer
import com.angcyo.uiview.less.media.SimplePlayerListener
import com.liulishuo.okdownload.DownloadTask

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

        fun getVoiceLocalPath(url: String): String {
            return FDown.defaultDownloadPath(url)
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

    fun play(activity: Activity?, url: String) {
        playerUI.show(activity)

        if (url.isFileExists()) {
            //本地视频
            player.startPlay(url)
        } else if (FDown.isCompleted(url)) {
            //视频已经下载好了
            player.startPlay(getVoiceLocalPath(url))
        } else {
            //开始下载视频
            download(url) {
                if (player.playState() == RPlayer.STATE_INIT) {
                    player.startPlay(it)
                }
            }
        }
    }

    fun release() {
        stop()
        player.release()
        playerUI.hide()
    }

    fun pause() {
        if (player.isPlaying()) {
            player.pausePlay()
        }
    }

    fun stop() {
        player.stopPlay()
        FDown.cancel(lastDownTaskIt)
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
        return (player.playUrl == url ||
                player.playUrl == getVoiceLocalPath(url)) && player.isPlaying()
    }

    fun isPause(url: String): Boolean {
        return (player.playUrl == url ||
                player.playUrl == getVoiceLocalPath(url)) && player.isPause()
    }

    private var lastDownTaskIt = 0
    private var lastDownUrl = ""

    /**下载文件*/
    private fun download(url: String, callback: (path: String) -> Unit) {
        if (lastDownUrl != url) {
            FDown.cancel(lastDownTaskIt)
        }
        lastDownTaskIt = FDown.down(url, object : FDownListener() {
            override fun onTaskEnd(
                task: DownloadTask,
                isCompleted: Boolean,
                realCause: Exception?
            ) {
                super.onTaskEnd(task, isCompleted, realCause)
                if (isCompleted) {
                    callback.invoke(task.file!!.absolutePath)
                }
            }
        }).id
    }
}