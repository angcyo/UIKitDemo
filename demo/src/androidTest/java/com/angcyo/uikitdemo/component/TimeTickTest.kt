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

    fun String?.orEmpty(): String = if (this.isNullOrEmpty()) "isEmpty" else this
    fun (String?).orEmpty2(): String = if (this.isNullOrEmpty()) "isEmpty2" else this

    @Test
    fun testTick() {
        var test: String? = null
        L.i("开始测试...." + test.orEmpty())
        test = "test"
        L.i("开始测试...." + test.orEmpty())
        test = null
        L.i("开始测试...." + test.orEmpty())

        test = null
        L.i("开始测试2....${test.orEmpty()}")
        test = "test"
        L.i("开始测试2....${test.orEmpty()}")
        test = null
        L.i("开始测试2....${test.orEmpty()}")


        var test2: String? = null
        L.i("开始测试2....${test2.orEmpty()}")
        test = "test"
        L.i("开始测试2....${test2.orEmpty()}")
        test = null
        L.i("开始测试2....${test2.orEmpty()}")
        return

        1/0

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