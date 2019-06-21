package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.angcyo.RWebSocket
import com.neovisionaries.ws.client.angcyo.RWebSocketListener

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class WebSocketDemo : SocketDemo() {

    val websocket: RWebSocket by lazy {
        RWebSocket.create()
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        websocket.addListener(object : RWebSocketListener() {
            override fun disConnectWebsocket(code: Int, message: String?) {
                super.disConnectWebsocket(code, message)
                baseDslAdapter.renderTextItem("连接中断:$code $message")
            }

            override fun connectSuccessWebsocket(webSocket: WebSocket, isReconnect: Boolean) {
                super.connectSuccessWebsocket(webSocket, isReconnect)
                baseDslAdapter.renderTextItem("连接成功.$isReconnect")
            }

            override fun onTextMessage(websocket: WebSocket, data: String?) {
                super.onTextMessage(websocket, data)
                baseDslAdapter.renderTextItem("消息: $data")
            }
        })
    }

    override fun disconnect() {
        super.disconnect()
        websocket.closeWebSocket()
    }

    override fun sendText(text: String) {
        super.sendText(text)
        websocket.sendText(text)
    }

    override fun connect(url: String) {
        super.connect(url)
        websocket.connect(url)
    }
}