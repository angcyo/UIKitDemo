package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapter
import com.angcyo.uiview.less.utils.ScrollLockHelper

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

open class SocketDemo : AppBaseDslRecyclerFragment() {

    override fun getContentLayoutId(): Int {
        return R.layout.demo_websocket_layout
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        //tcp 地址不能用 / 结尾
        viewHolder.auto(
            R.id.url_edit,
            mutableListOf("tcp://116.7.249.36:1883", "ws://116.7.249.36:8083/mqtt"),
            true
        )

        renderDslAdapter {
            renderTextItem("${nowTime().fullTime()}")
        }

        viewHolder.click(R.id.disconnect) {
            disconnect()
        }

        viewHolder.click(R.id.connect) {
            viewHolder.ev(R.id.url_edit).string().let {
                if (it.isNotEmpty()) {
                    connect(it)
                }
            }
        }

        viewHolder.click(R.id.send) {
            viewHolder.ev(R.id.input_edit).string().let {
                if (it.isNotEmpty()) {
                    baseDslAdapter.renderTextItem(it)
                    sendText(it)
                }

                this@SocketDemo.recyclerView?.scrollToLastBottom(false)
            }
        }

        //ScrollLockHelper().lock(recyclerView)
    }

    public fun DslAdapter.renderTextItem(text: CharSequence? = null) {
        renderItem {
            itemTopInsert = 1 * dpi
            itemLayoutId = R.layout.item_single_text
            itemData = text
            itemBind = { itemHolder, _, _ ->
                itemHolder.tv(R.id.text_view).text = itemData?.toString()
            }
        }

        //this@SocketDemo.recyclerView?.scrollToLastBottom(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        disconnect()
    }

    open fun disconnect() {

    }

    open fun connect(url: String) {
        baseDslAdapter.renderTextItem("开始连接.$url")
    }

    open fun sendText(text: String) {

    }
}