package com.angcyo.uikitdemo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.angcyo.lib.L

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/07/10
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class TouchViewGroup(context: Context, attributeSet: AttributeSet? = null) : FrameLayout(context, attributeSet) {
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        L.i("ViewGroup:dispatchTouchEvent:${MotionEvent.actionToString(event.action)}")
        return super.dispatchTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        L.i("ViewGroup:onInterceptTouchEvent:${MotionEvent.actionToString(event.action)}")
        return super.onInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        L.i("ViewGroup:onTouchEvent:${MotionEvent.actionToString(event.action)}")
        return super.onTouchEvent(event)
    }
}

class TouchView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        L.i("View:dispatchTouchEvent:${MotionEvent.actionToString(event.action)}")
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        L.i("View:onTouchEvent:${MotionEvent.actionToString(event.action)}")
        return super.onTouchEvent(event)
    }
}