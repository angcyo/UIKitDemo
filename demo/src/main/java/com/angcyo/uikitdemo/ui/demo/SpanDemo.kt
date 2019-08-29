package com.angcyo.uikitdemo.ui.demo

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.recyclerview.widget.RecyclerView
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.dslitem.dslItem
import com.angcyo.uiview.less.resources.RDrawable
import com.angcyo.uiview.less.utils.RSpan

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019-8-12
 */
class SpanDemo : AppBaseDslRecyclerFragment() {

    override fun getRecyclerViewPool(): RecyclerView.RecycledViewPool? {
        return null
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        val spanKey = "span key"
        val spanValue =
            "span value .span value .span value .span value .span value.88888888@qq.com\n"
        val spanValueNum =
            "${spanKey}888888888888888888888888888888888888888888888888@qq.com\n"

        val radius = 5 * dp


        renderDslAdapter {
            dslItem(R.layout.demo_span) {
                itemBind = { itemHolder, _, _ ->
                    itemHolder.tv(R.id.text3).text = span {
                        append("左边文本")
                        customSpan {
                            textSpan {
                                setSpanWeight(0.5f)
                                setForegroundColor(Color.GREEN)
                                //setTextGravity(Gravity.CENTER)
                                setBackground(Color.BLACK, radius)
                                setPaddingHorizontal(2 * dp)
                            }
                        }
                        append("右边文本")
                        customSpan {
                            textSpan {
                                setSpanWeight(0.5f)
                                setForegroundColor(Color.BLUE)
                                setTextGravity(Gravity.CENTER)
                                setBackground(Color.YELLOW, radius)
                            }
                        }

                        append("左边文本1左边文本1左边文本1左边文本1左边文本1左边文本1左边文本1左边文本1")
                        customSpan {
                            textSpan {
                                setSpanWeight(0.5f)
                                setSpanMaxWeight(0.5f)
                                setPaddingHorizontal(2 * dp)
                            }
                        }
                        append("右边文本2右边文本2右边文本2右边文本2右边文本2右边文本2右边文本2右边文本2")
                        customSpan {
                            textSpan {
                                setSpanWeight(0.5f)
                                setSpanMaxWeight(0.5f)
                                setPaddingHorizontal(2 * dp)
                            }
                        }
                    }
                }
            }

            dslItem(R.layout.demo_span) {
                itemBind = { itemHolder, _, _ ->

                    itemHolder.tv(R.id.text3).text = span {
                        append(spanKey)
                        append("18")
                        customSpan {
                            RSpan.TextSpan().apply {
                                setKeepCircleOnTextLength(2)
                                setForegroundColor(Color.BLUE)
                                setTextGravity(Gravity.CENTER)
                                setBackground(Color.YELLOW, radius)
                                setPadding(1 * dp)
                                setFontSize(8 * dp)
//                            setMargin(10 * dp)
//                            setMarginLeft(10 * dp)
//                            setMarginRight(20 * dp)
                            }
                        }
                        append(spanKey)
                        append("88")
                        customSpan {
                            RSpan.TextSpan().apply {
                                setKeepCircleOnTextLength(2)
                                setForegroundColor(Color.RED)
                                setBackground(Color.BLACK, radius)
                                setTextGravity(Gravity.CENTER)
                                setMargin(10 * dp)
                                setPadding(2 * dp)
                                //setMarginHorizontal(10 * dp)
                            }
                        }
                        append(spanKey)
                        append("888")
                        customSpan {
                            RSpan.TextSpan().apply {
                                setKeepCircleOnTextLength(2)
                                setForegroundColor(Color.RED)
                                setBackground(Color.BLACK, radius)
                                setPadding(2 * dp)
                                setTextGravity(Gravity.CENTER)
                            }
                        }
                        append(spanKey)
                    }

                    itemHolder.tv(R.id.text1).text = RSpan.get()
                        .append("2019-08-14")
                        .customSpan {
                            //                        object : AbsoluteSizeSpan(30 * dpi) {
//                            override fun getSize(): Int {
//                                return super.getSize()
//                            }
//
//                            override fun updateDrawState(ds: TextPaint) {
//                                super.updateDrawState(ds)
//                            }
//
//                            override fun updateMeasureState(ds: TextPaint) {
//                                super.updateMeasureState(ds)
//                            }
//                        }

                            //IconTextSpan(mAttachContext, Color.RED, "ABC")
                            RSpan.TextSpan().apply {
                                setForegroundColor(Color.RED)
                                setMarginHorizontal(10 * dp)
                                setMarginVertical(5 * dp)
                                setPaddingLeft(20 * dp)
                                setPaddingRight(10 * dp)
                                setPaddingBottom(5 * dp)
                                setTextGravity(Gravity.CENTER_VERTICAL)
                                setBackgroundDrawable(
                                    RDrawable.get(mAttachContext)
                                        .cornerRadius(5 * dp)
                                        .solidColor(Color.GREEN)
                                        .get()
                                )
                            }

                            //MarginTextSpan(2 * dp, 10 * dp)
                            //ForegroundColorSpan(Color.RED)
                        }

                        .append("14:18:28")
                        .customSpan {
                            RSpan.TextSpan().apply {
                                setForegroundColor(Color.BLUE)
                                setFontSize(30 * dp)
                            }
                        }

                        .append("18")
                        .customSpan {
                            RSpan.TextSpan().apply {
                                setKeepCircleOnTextLength(2)
                                setForegroundColor(Color.BLUE)
                                setTextGravity(Gravity.CENTER)
                                setBackgroundDrawable(
                                    RDrawable.get(mAttachContext)
                                        .circle(Color.YELLOW)
                                        .get()
                                )
                            }
                        }
                        //.append("~~~")

                        .append("88")
                        .customSpan {
                            RSpan.TextSpan().apply {
                                setKeepCircleOnTextLength(2)
                                setForegroundColor(Color.RED)
                                setBackgroundColor(Color.BLACK)
                                setTextGravity(Gravity.CENTER)
                                setBackgroundDrawable(
                                    RDrawable.get(mAttachContext)
                                        .circle(Color.YELLOW)
                                        .get()
                                )
                            }
                        }

                        .append("2019-0\n8-14")
                        .customSpan {
                            RSpan.TextSpan().apply {
                                setForegroundColor(Color.RED)
                                setPaddingLeft(20 * dp)
                                setPaddingRight(10 * dp)
                                setTextGravity(Gravity.CENTER)
                                setBackgroundDrawable(
                                    RDrawable.get(mAttachContext)
                                        .cornerRadius(5 * dp)
                                        .solidColor(getColor(R.color.transparent_dark60))
                                        .get()
                                )
                            }
                        }

                        .append(spanKey)
                        .append(spanValueNum)
                        .customSpan {
                            RSpan.TextSpan().apply {

                            }
                        }
                        .append(spanKey)
                        .append(spanValueNum)
                        .customSpan {
                            RSpan.TextSpan().apply {
                                setForegroundColor(Color.RED)
                            }
                        }
                        .append(spanKey)
                        .append(spanValueNum)
                        .customSpan {
                            RSpan.TextSpan().apply {
                                setBackgroundColor(Color.GREEN)
                            }
                        }
                        .append(spanKey)
                        .append(spanValueNum)
                        .customSpan {
                            RSpan.TextSpan().apply {
                                setBackgroundDrawable(
                                    RDrawable.get(mAttachContext)
                                        .cornerRadius(10 * dp)
                                        .solidColor(Color.BLUE)
                                        .get()
                                )
                            }
                        }


                        .append(spanKey)
                        .customSpan {
                            RSpan.TextSpan().apply {
                                setMinSize(100 * dpi)
                                setBackgroundColor(Color.YELLOW)
                                setForegroundColor(Color.RED)
                            }
                        }
                        .append(spanValueNum)
                        .customSpan {
                            RSpan.TextSpan().apply {
                                setMaxSize(80 * dpi)
                                setForegroundColor(Color.BLUE)
                            }
                        }
                        .create()

                    itemHolder.tv(R.id.text2).text = itemHolder.tv(R.id.text1).text

                }
            }
        }
    }
}