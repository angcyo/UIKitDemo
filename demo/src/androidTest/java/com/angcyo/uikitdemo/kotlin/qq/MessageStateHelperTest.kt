package com.angcyo.uikitdemo.kotlin.qq

import android.os.SystemClock
import com.angcyo.lib.L
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2019/09/03
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class MessageStateHelperTest {
    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testState() {
        println("${Thread.currentThread().name} ....test....start")

        MessageStateHelper.MESSAGE_TIMEOUT = 5 * 1000L
        MessageStateHelper.listeners.add(object : OnMessageStateListener {
            override fun onMessageStateChanged(msgId: Long, formState: Int, toState: Int) {
                if (toState == MessageStateHelper.MESSAGE_STATE_TIMEOUT) {
                    L.e(
                        "消息超时:$msgId ${MessageStateHelper.messageStateString(formState)}->${MessageStateHelper.messageStateString(
                            toState
                        )}"
                    )
                } else {
                    L.i(
                        "消息状态:$msgId ${MessageStateHelper.messageStateString(formState)}->${MessageStateHelper.messageStateString(
                            toState
                        )}"
                    )
                }
            }
        })
        MessageStateHelper.start()

        for (l in 0..10L) {
            MessageStateHelper.updateMessageState(l, MessageStateHelper.MESSAGE_STATE_NORMAL)
            SystemClock.sleep(2 * 1000L)
        }

        SystemClock.sleep(60 * 1000L)
        MessageStateHelper.stop()

        println("${Thread.currentThread().name} ....test....end")
    }
}