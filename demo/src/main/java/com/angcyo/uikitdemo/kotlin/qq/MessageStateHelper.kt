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

    private val messageStateMap = ConcurrentHashMap<Long, MessageData>()

    private var handler: Handler? = null
    private var handlerThread: HandlerThread? = null
    private var mainHandler: Handler? = null

    private const val MESSAGE_UPDATE_STATE = 9
    private const val MESSAGE_CHECK_TIMEOUT = 10

    const val MESSAGE_STATE_UNKNOWN = 0
    const val MESSAGE_STATE_NORMAL = 1
    const val MESSAGE_STATE_FINISH = 2
    const val MESSAGE_STATE_TIMEOUT = 3

    /**消息超时间隔时长, 60秒*/
    var MESSAGE_TIMEOUT = 60 * 1000L
    /**消息超时检查时间间隔*/
    var MESSAGE_TIMEOUT_CHECK = 300L

    fun messageStateString(state: Int): String {
        return when (state) {
            MESSAGE_STATE_NORMAL -> "MESSAGE_STATE_NORMAL"
            MESSAGE_STATE_FINISH -> "MESSAGE_STATE_FINISH"
            MESSAGE_STATE_TIMEOUT -> "MESSAGE_STATE_TIMEOUT"
            else -> "MESSAGE_STATE_UNKNOWN"
        }
    }

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
                                val messageData = messageStateMap[msgId] ?: MessageData(msgId)
                                messageData.state = to
                                messageStateMap[msgId] = messageData

                                //主线程回调
                                mainHandler?.post {
                                    listeners.forEach {
                                        it.onMessageStateChanged(msgId, from, to)
                                    }
                                }
                            }

                            if (from == MESSAGE_STATE_FINISH || to == MESSAGE_STATE_FINISH) {
                                //已完全的消息, 不缓存, 节约资源
                                messageStateMap.remove(msgId)
                            }
                        }
                        MESSAGE_CHECK_TIMEOUT -> {
                            //检查消息是否超时
                            messageStateMap.forEach { entry ->
                                val messageData = entry.value
                                if (messageData.state != MESSAGE_STATE_FINISH ||
                                    messageData.state != MESSAGE_STATE_TIMEOUT
                                ) {
                                    val nowTime = System.currentTimeMillis()
                                    if (nowTime - messageData.sendTime >= MESSAGE_TIMEOUT) {
                                        //超时
                                        updateMessageState(messageData.msgId, MESSAGE_STATE_TIMEOUT)
                                    }
                                }
                            }

                            checkMessageTimeout()
                        }
                    }
                    true
                }

                checkMessageTimeout()
            }
        }
        if (mainHandler == null) {
            mainHandler = Handler(Looper.getMainLooper())
        }
    }

    /**停止*/
    fun stop() {
        handler?.removeMessages(MESSAGE_UPDATE_STATE)
        handler?.removeMessages(MESSAGE_CHECK_TIMEOUT)
        handlerThread?.quit()
        handler = null
        handlerThread = null
    }

    /**获取已缓存的消息的状态*/
    fun getMessageState(msgId: Long): Int {
        return messageStateMap[msgId]?.state ?: MESSAGE_STATE_UNKNOWN
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

    private fun checkMessageTimeout(delayMillis: Long = MESSAGE_TIMEOUT_CHECK) {
        handler?.run {
            sendMessageDelayed(obtainMessage(MESSAGE_CHECK_TIMEOUT), delayMillis)
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

private data class MessageData(
    var msgId: Long,
    @MessageState var state: Int = MessageStateHelper.MESSAGE_STATE_UNKNOWN,
    var sendTime: Long = System.currentTimeMillis()
)

interface OnMessageStateListener {
    fun onMessageStateChanged(msgId: Long, formState: Int, toState: Int)
}