package com.angcyo.uikitdemo.component

import android.os.SystemClock
import com.angcyo.lib.L
import org.junit.Test

/**
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2019/09/05
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class TimeTickTest {

    @Test
    fun testTick() {
        L.i("开始测试....")

        val timeTick = TimeTick()

        timeTick.tickDelay = 100

        timeTick.startTick { millisecond, second, minute, hour, day ->
            L.i("$day:$hour:$minute:$second:$millisecond")
        }

        Thread(Runnable {
            SystemClock.sleep(2000)
            L.i("暂停2s....")
            timeTick.pauseTick()

            SystemClock.sleep(2000)
            timeTick.startTick()

            SystemClock.sleep(4000)
            L.i("暂停4s....")
            timeTick.pauseTick()

            SystemClock.sleep(4000)
            timeTick.startTick()
        }).start()

        SystemClock.sleep(20_1000)
        L.i("结束测试....")
    }
}