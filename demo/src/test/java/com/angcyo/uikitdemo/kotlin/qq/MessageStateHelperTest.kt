package com.angcyo.uikitdemo.kotlin.qq

import android.os.SystemClock
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2019/09/02
 */
class MessageStateHelperJavaTest {

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
                println(
                    "消息状态改变:$msgId ${MessageStateHelper.messageStateString(formState)}->${MessageStateHelper.messageStateString(
                        toState
                    )}"
                )
            }
        })
        MessageStateHelper.start()

        for (l in 0..10L) {
            MessageStateHelper.updateMessageState(l, MessageStateHelper.MESSAGE_STATE_NORMAL)
        }

        SystemClock.sleep(60 * 1000L)
        println("${Thread.currentThread().name} ....test....end")
    }
}