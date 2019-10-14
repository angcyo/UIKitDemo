package com.angcyo.uikitdemo.ui.demo

import android.graphics.Color
import android.os.Bundle
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.widget.RDrawTextView

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/07/12
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class CustomViewDemo2 : AppBaseDslRecyclerFragment() {
    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        renderDslAdapter {
            renderItem {
                itemLayoutId = R.layout.custom_view1_layout2
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.click(R.id.button1) {
                        itemHolder.tv(R.id.button1).text = itemHolder.view(R.id.test1)
                            .getLocationInParent(itemHolder.view(R.id.wrap_layout)).toString()
                    }
                    itemHolder.click(R.id.button2) {
                        itemHolder.tv(R.id.button2).text = itemHolder.view(R.id.test2)
                            .getLocationInParent(itemHolder.view(R.id.wrap_layout)).toString()
                    }

                    itemHolder.v<RDrawTextView>(R.id.draw_text1).apply {
                        drawText.textSize = 30 * dp
                        drawText.setDrawText(span {
                            append("第一行的文本内容测试.")
                            setForegroundColor(Color.WHITE)
                            appendLine()

                            append("第二行的文本内容测试.")
                            setForegroundColor(Color.RED)
                            setFontSize(30 * dpi)
                        })

                        anim(duration = 3_000) {
                            drawText.drawTextSize = 30 * dp - 20 * dp * it
                        }

                        L.i("bind................this")
                    }
                }
            }
        }
    }
}