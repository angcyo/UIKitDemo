package com.angcyo.uikitdemo.component

import android.os.Handler
import android.os.Looper
import com.angcyo.uiview.less.kotlin.nowTime

/**
 * 计时器工具类
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/05
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class TimeTick : Runnable {

    companion object {
        private const val STATE_NORMAL = 0
        private const val STATE_PAUSE = 1
        private const val STATE_STOP = 2
        private const val STATE_START = 3
    }

    /**计时 粒度*/
    private var millisecond = 0L  //毫秒
    private var second = 0L       //秒
    private var minute = 0L       //分
    private var hour = 0L         //时
    private var day = 0L          //天
    private var startTime = 0L    //开始打点时间
    private var pauseTime = 0L    //暂停打点时间
    private var pauseMillisecond = 0L    //暂停的时间和

    private var millisecondAll = 0L
    private var secondAll = 0L
    private var minuteAll = 0L
    private var hourAll = 0L

    private var state = STATE_NORMAL

    private val handler = Handler(Looper.getMainLooper())

    /**打点检查间隔时间, 间隔越短, 粒度越精确*/
    var tickDelay = 1000L

    /**打点更新回调*/
    var onTimeTick: (
        millisecond: Long,
        second: Long,
        minute: Long,
        hour: Long,
        day: Long
    ) -> Unit = { _, _, _, _, _ -> }

    private fun reset() {
        millisecond = 0
        second = 0
        minute = 0
        hour = 0
        day = 0
        pauseTime = 0
        pauseMillisecond = 0

        millisecondAll = 0L
        secondAll = 0L
        minuteAll = 0L
        hourAll = 0L
    }

    /**开始计时*/
    fun startTick(
        timeTick: ((
            millisecond: Long,
            second: Long,
            minute: Long,
            hour: Long,
            day: Long
        ) -> Unit)? = null
    ) {

        timeTick?.run {
            onTimeTick = this
        }

        if (state == STATE_START) {
            return
        }

        if (state == STATE_PAUSE) {
            val nowTime = nowTime()
            pauseMillisecond += nowTime - pauseTime
        } else {
            reset()
            startTime = nowTime()
            pauseTime = startTime
        }

        state = STATE_START
        run()
    }

    /**暂停计时, 保留最后一次时间, 但是下次开始时, 会从暂停位置开始*/
    fun pauseTick() {
        if (state != STATE_START) {
            return
        }
        state = STATE_PAUSE
        pauseTime = nowTime()
    }

    /**停止计时, 保留最后一次时间, 但是下次开始时, 会从0开始*/
    fun stopTick() {
        if (state != STATE_START) {
            return
        }
        state = STATE_STOP
    }

    fun getPrettyTime(): String {
        val builder = buildString {
            if (hourAll > 0) {
                append(hour.s2())
                append(":")
            }
            append(minute.s2())
            append(":")
            append(second.s2())
        }
        return builder
    }

    //tick
    override fun run() {
        val nowTime = nowTime()

        //总共过去的时间 毫秒
        millisecondAll = nowTime - startTime - pauseMillisecond

        //总共过去的时间 秒
        secondAll = millisecondAll / 1000

        //总共过去的时间 分
        minuteAll = secondAll / 60

        //总共过去的时间 时
        hourAll = minuteAll / 60

        millisecond = millisecondAll % 1000
        second = secondAll % 60
        minute = minuteAll % 60
        hour = hourAll
        day = hourAll / 24

        onTimeTick(millisecond, second, minute, hour, day)

        if (state == STATE_START) {
            handler.postDelayed(this, tickDelay)
        }
    }
}

public fun Number.s2(): String = if (this.toLong() >= 10) "$this" else "0$this"