package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseTitleFragment
import com.angcyo.uikitdemo.ui.widget.DrawCallbackView
import com.angcyo.uiview.less.kotlin.clickIt
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.utils.TopToast

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/05/09
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class TouchDemo : AppBaseTitleFragment() {
    override fun getContentLayoutId(): Int {
        return R.layout.demo_touch_layout
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        val testView = View(mAttachContext)
//        testView.measure(
//            View.MeasureSpec.makeMeasureSpec(200 * dpi, View.MeasureSpec.EXACTLY),
//            View.MeasureSpec.makeMeasureSpec(200 * dpi, View.MeasureSpec.EXACTLY)
//        )
//        testView.layout(0, 0, testView.measuredWidth, testView.measuredHeight)
        //testView.setBackgroundColor(Color.YELLOW)
        testView.setBackgroundResource(R.drawable.orange_selector_ripple)
        testView.clickIt {
            TopToast.tip("test view")
        }

        activity?.apply {
            val parent: ViewGroup = window.findViewById(Window.ID_ANDROID_CONTENT)
            parent.addView(testView, 0, ViewGroup.LayoutParams(50 * dpi, 50 * dpi))
        }

        viewHolder.itemView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(v: View?) {
                L.i("view detach: $v")
            }

            override fun onViewAttachedToWindow(v: View?) {
                L.i("view attached: $v")
            }
        })

//        (viewHolder.itemView as? ViewGroup)?.addView(testView, 0, ViewGroup.LayoutParams(50 * dpi, 50 * dpi))

        viewHolder.view(R.id.view2).setOnTouchListener { v, event ->
            //viewHolder.view(R.id.view1).dispatchTouchEvent(event)
            viewHolder.view(R.id.view1).onTouchEvent(event)
            testView.onTouchEvent(event)

            viewHolder.v<DrawCallbackView>(R.id.draw_callback_view).apply {
                drawCallback = {
                    viewHolder.view(R.id.view1).draw(it)
                    testView.draw(it)
                }

                postInvalidate()
            }

            true
        }

        viewHolder.click(R.id.view1) {
            TopToast.tip("click view 1")
        }
    }
}