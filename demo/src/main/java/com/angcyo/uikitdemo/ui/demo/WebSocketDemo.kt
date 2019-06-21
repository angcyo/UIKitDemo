package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapter
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

class WebSocketDemo : AppBaseDslRecyclerFragment() {

    val websocket: RWebSocket by lazy {
        RWebSocket.create()
    }

    override fun getContentLayoutId(): Int {
        return R.layout.demo_websocket_layout
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

        renderDslAdapter {
            renderTextItem("${nowTime().fullTime()}")
        }

        viewHolder.click(R.id.connect) {
            viewHolder.ev(R.id.url_edit).string().let {
                if (it.isNotEmpty()) {
                    websocket.connect(it)
                }
            }
        }

        viewHolder.click(R.id.send) {
            viewHolder.ev(R.id.input_edit).string().let {
                if (it.isNotEmpty()) {
                    baseDslAdapter.renderTextItem(it)
                    websocket.sendText(it)
                }
            }
        }
    }

    public fun DslAdapter.renderTextItem(text: CharSequence? = null) {
        renderItem {
            itemTopInsert = 1 * dpi
            itemLayoutId = R.layout.item_single_text
            itemBind = { itemHolder, _, _ ->
                itemHolder.tv(R.id.text_view).text = text
            }
        }

        this@WebSocketDemo.recyclerView?.scrollToLastBottom(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        websocket.closeWebSocket()
    }
}