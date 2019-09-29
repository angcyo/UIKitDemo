package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.BuildConfig
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapter
import com.angcyo.uiview.less.utils.ScrollHelper

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

open class SocketDemo : AppBaseDslRecyclerFragment() {

    val scrollHelper = ScrollHelper()

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

            if (BuildConfig.DEBUG) {
                for (i in 1..30) {
                    renderTextItem("测试Item....位置:$i")
                }
            }
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
        //(recyclerView?.layoutManager as? LinearLayoutManager)?.reverseLayout = true
        scrollHelper.attach(recyclerView)

        viewHolder.click(R.id.send) {
            viewHolder.ev(R.id.input_edit).string().also {
                if (it.isNotEmpty()) {
                    val intOrNull = it.toIntOrNull()
                    if (intOrNull == null) {
                        //发送文本
                        baseDslAdapter.renderTextItem(it)
                        sendText(it)

                        //scroll(baseDslAdapter.itemCount - 1, true)
                    } else {
                        //输入数字, 滚动到目标位置
                        scroll(intOrNull, false)
                    }
                } else {
                    //空数据, 滚动到底部
                    scroll(baseDslAdapter.itemCount - 1, false)
                }
            }
        }

        scrollHelper.lockPosition {
            scrollAnim = true
        }
        scrollHelper.log(recyclerView)
        //ScrollHelper().lock(recyclerView)
    }

    protected fun scroll(position: Int, isAdd: Boolean) {
        scrollHelper.apply {
            isScrollAnim = isAnimScroll()
            isFromAddItem = isAdd

            baseViewHolder.ev(R.id.url_edit).string().toIntOrNull()?.let {
                scrollType = it
            }

            scroll(position)
        }
    }

    protected fun isAnimScroll(): Boolean = baseViewHolder.cb(R.id.anim_box).isChecked

    public fun DslAdapter.renderTextItem(text: CharSequence? = null) {
        renderItem {
            itemTopInsert = 1 * dpi
            itemLayoutId = R.layout.item_single_text
            itemData = text
            itemBind = { itemHolder, position, _ ->
                itemHolder.tv(R.id.text_view).text = "$itemData $position"
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