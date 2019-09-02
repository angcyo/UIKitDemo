package com.angcyo.uikitdemo.kotlin.qq

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.annotation.IntDef
import java.util.concurrent.ConcurrentHashMap

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/03
 */
object MessageStateHelper {

    /**消息状态改变回调, 主线程*/
    val listeners = mutableSetOf<OnMessageStateListener>()

    private val messageStateMap = ConcurrentHashMap<Long, Int>()

    private var handler: Handler? = null
    private var handlerThread: HandlerThread? = null
    private var mainHandler: Handler? = null

    private const val MESSAGE_UPDATE_STATE = 9
    private const val MESSAGE_CHECK_TIMEOUT = 10

    const val MESSAGE_STATE_UNKNOWN = 0
    const val MESSAGE_STATE_NORMAL = 1
    const val MESSAGE_STATE_FINISH = 2
    const val MESSAGE_STATE_TIMEOUT = 3

    /**开始消息超时检查*/
    fun start() {
        if (handlerThread == null) {
            handlerThread = HandlerThread("MessageStateHelper").apply {
                start()

                handler = Handler(looper) {
                    when (it.what) {
                        MESSAGE_UPDATE_STATE -> {
                            val msgId: Long = it.obj as Long
                            val from = it.arg1
                            val to = it.arg2

                            if (from == MESSAGE_STATE_FINISH) {
                                //已完成的消息, 不允许操作超时
                            } else if (from == to) {
                                //状态相同的消息
                            } else {
                                messageStateMap[msgId] = to

                                //主线程回调
                                mainHandler?.post {
                                    listeners.forEach {
                                        it.onMessageStateChanged(msgId, from, to)
                                    }
                                }
                            }
                        }
                        MESSAGE_CHECK_TIMEOUT -> {

                        }
                    }
                    true
                }
            }
        }
        if (mainHandler == null) {
            mainHandler = Handler(Looper.getMainLooper())
        }
    }

    /**停止*/
    fun stop() {
        handlerThread?.quit()
        handlerThread = null
    }

    /**获取已缓存的消息的状态*/
    fun getMessageState(msgId: Long): Int {
        return messageStateMap[msgId] ?: MESSAGE_STATE_UNKNOWN
    }

    /**更新消息状态*/
    fun updateMessageState(msgId: Long, @MessageState statue: Int) {
        if (handler == null) {
            throw IllegalAccessException("请先调用[start]方法")
        }
        handler?.run {
            sendMessage(obtainMessage(MESSAGE_UPDATE_STATE, getMessageState(msgId), statue, msgId))
        }
    }
}

@IntDef(
    flag = false,
    value = [MessageStateHelper.MESSAGE_STATE_UNKNOWN,
        MessageStateHelper.MESSAGE_STATE_NORMAL,
        MessageStateHelper.MESSAGE_STATE_FINISH,
        MessageStateHelper.MESSAGE_STATE_TIMEOUT]
)
annotation class MessageState {}

interface OnMessageStateListener {
    fun onMessageStateChanged(msgId: Long, formState: Int, toState: Int)
}