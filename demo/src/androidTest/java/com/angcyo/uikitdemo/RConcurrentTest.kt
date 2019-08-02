package com.angcyo.uikitdemo

import android.os.SystemClock
import com.angcyo.lib.L
import com.angcyo.uiview.less.utils.RConcurrent
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/08/01
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class RConcurrentTest {
    @Before
    fun setUp() {
        L.i("start...")
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testMain() {
        L.i("${Thread.currentThread().name} 测试...")

        val concurrent = RConcurrent {
            L.i("任务执行完成...")
        }
        concurrent.addTask(ConcurrentTask("T1"))
        concurrent.addTask(ConcurrentTask("T2"))
        concurrent.addTask(ConcurrentTask("T3"))
        concurrent.addTask(ConcurrentTask("T4"))
        concurrent.addTask(ConcurrentTask("T5"))
        concurrent.addTask(ConcurrentTask("T6"))
        concurrent.addTask(ConcurrentTask("T7"))

        Thread {
            for (i in 0..10) {
                concurrent.addTask(ConcurrentTask("T${8 + i}"))
                SystemClock.sleep(60)
            }
        }.start()

        SystemClock.sleep(400_000)

        concurrent.release()
        L.i("end....")
    }
}

class ConcurrentTask(val name: String) : Runnable {
    override fun run() {
        L.d("$name 任务执行中...")
    }
}