package com.angcyo.uikitdemo.ui.demo

import com.angcyo.mqtt.Mqtt

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class MqttDemo : SocketDemo() {

    val mqtt: Mqtt by lazy {
        Mqtt(mAttachContext).apply {
            userName = "admin"
            password = "public"

            onConnectComplete = { _, _ ->
                baseDslAdapter.renderTextItem("已连接.$serverURI")
                baseDslAdapter.renderTextItem("订阅:$topic")

                mqtt.subscribe(topic)
            }

            onConnectionLost = {
                baseDslAdapter.renderTextItem("连接断开.$serverURI")
            }

            onMessageArrived = { topic, message ->
                baseDslAdapter.renderTextItem("接收:$topic ->\n$message")
            }

            onDeliveryComplete = {
                baseDslAdapter.renderTextItem("消息已送达.")
            }
        }
    }

    val topic = "oboa/topic/#"
    val sendTopic = "oboa/topic/03"

    override fun disconnect() {
        super.disconnect()
        mqtt.disconnect()
    }

    override fun sendText(text: String) {
        super.sendText(text)
        mqtt.publish(sendTopic, text)
    }

    override fun connect(url: String) {
        super.connect(url)
        mqtt.connect(url)
    }
}