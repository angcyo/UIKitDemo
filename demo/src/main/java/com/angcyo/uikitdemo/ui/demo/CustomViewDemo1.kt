package com.angcyo.uikitdemo.ui.demo

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.style.LineBackgroundSpan
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.ui.widget.DYProgressBar
import com.angcyo.uiview.less.draw.view.HSProgressView
import com.angcyo.uiview.less.kotlin.renderItem
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.utils.RSpan

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/11
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class CustomViewDemo1 : AppBaseDslRecyclerFragment() {
    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.GREEN

        baseDslAdapter.apply {
            renderItem {
                itemLayoutId = R.layout.custom_view1_layout1

                itemBind = { itemHolder, _, _ ->
                    itemHolder.v<DYProgressBar>(R.id.dy_progress_bar).startAnimator()
                    itemHolder.v<HSProgressView>(R.id.hs_progress_view1).baseDraw.setProgress(50)
                    itemHolder.v<HSProgressView>(R.id.hs_progress_view2).startAnimator()

                    itemHolder.tv(R.id.span_text).text = RSpan.get("ang\ncyo")
                        .setSpans(object : LineBackgroundSpan {
                            override fun drawBackground(
                                canvas: Canvas,
                                p: Paint,
                                left: Int,
                                right: Int,
                                top: Int,
                                baseline: Int,
                                bottom: Int,
                                text: CharSequence?,
                                start: Int,
                                end: Int,
                                lnum: Int
                            ) {
                                val r = p.measureText((text ?: "").subSequence(start, end).toString())
                                canvas.drawRect(
                                    left.toFloat(),
                                    top.toFloat(),
                                    r,
                                    baseline.toFloat() + p.descent(),
                                    paint
                                )
                            }
                        })
                        .append("                         ang\ncyo")
                        .setForegroundColor(Color.YELLOW)
                        .append("                         ang\ncyo")
                        .setBackgroundColor(Color.RED)
                        .append(" \nangcyo")
                        .setForegroundColor(Color.RED)
                        .append(" \nangc\nyo")
                        .setSpans(RSpan.TextSpan(-1, Color.YELLOW, Color.BLACK))
                        .append(" \nangc\nyo")
                        .setSpans(object : LineBackgroundSpan {
                            override fun drawBackground(
                                canvas: Canvas,
                                p: Paint,
                                left: Int,
                                right: Int,
                                top: Int,
                                baseline: Int,
                                bottom: Int,
                                text: CharSequence?,
                                start: Int,
                                end: Int,
                                lnum: Int
                            ) {
                                canvas.drawRect(
                                    left.toFloat(),
                                    top.toFloat(),
                                    right.toFloat(),
                                    baseline.toFloat(),
                                    paint
                                )
                            }

                        })
                        .create()
                }
            }
        }
    }
}