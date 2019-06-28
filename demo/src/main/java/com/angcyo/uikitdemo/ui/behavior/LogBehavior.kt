package com.angcyo.uikitdemo.ui.behavior

import android.content.Context
import android.graphics.Rect
import android.os.Parcelable
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v4.view.WindowInsetsCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/28
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
open class LogBehavior<T : View> : CoordinatorLayout.Behavior<T> {
    constructor() : super() {
        L.w("构造方法1")
    }

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.LogBehavior_Layout)
        val test = array.getDimensionPixelOffset(R.styleable.LogBehavior_Layout_behavior_test, 0)
        array.recycle()

        L.w("构造方法2:$test")
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: T,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        L.w("this....dx:$dx dy:$dy")
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: T,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        L.w("this....dxC:$dxConsumed dyC:$dyConsumed dxUC$dxUnconsumed dyUC$dyUnconsumed")
    }

    override fun onSaveInstanceState(parent: CoordinatorLayout, child: T): Parcelable? {
        L.w("this....")
        return super.onSaveInstanceState(parent, child)
    }

    override fun onNestedScrollAccepted(
        coordinatorLayout: CoordinatorLayout,
        child: T,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, type)
        L.w("this....")
    }

    override fun getScrimColor(parent: CoordinatorLayout, child: T): Int {
        L.w("this....")
        return super.getScrimColor(parent, child)
    }

    override fun onNestedFling(
        coordinatorLayout: CoordinatorLayout,
        child: T,
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        L.w("this....")
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: T, layoutDirection: Int): Boolean {
        L.w("this...${child.javaClass.simpleName}...ld:$layoutDirection")
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: T,
        target: View,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        L.w("this....")
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    override fun getInsetDodgeRect(parent: CoordinatorLayout, child: T, rect: Rect): Boolean {
        L.w("this....")
        return super.getInsetDodgeRect(parent, child, rect)
    }

    override fun onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams()
        L.w("this....")
    }

    override fun onRestoreInstanceState(parent: CoordinatorLayout, child: T, state: Parcelable) {
        super.onRestoreInstanceState(parent, child, state)
        L.w("this....")
    }


    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: T, target: View, type: Int) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
        L.w("this....")
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: T, dependency: View): Boolean {
        L.w("this....${child.javaClass.simpleName}...${dependency.javaClass.simpleName}")
        return super.layoutDependsOn(parent, child, dependency)
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: T, dependency: View): Boolean {
        L.w("this....")
        return super.onDependentViewChanged(parent, child, dependency)
    }

    override fun onDependentViewRemoved(parent: CoordinatorLayout, child: T, dependency: View) {
        super.onDependentViewRemoved(parent, child, dependency)
        L.w("this....")
    }

    override fun onRequestChildRectangleOnScreen(
        coordinatorLayout: CoordinatorLayout,
        child: T,
        rectangle: Rect,
        immediate: Boolean
    ): Boolean {
        L.w("this....")
        return super.onRequestChildRectangleOnScreen(coordinatorLayout, child, rectangle, immediate)
    }

    override fun onApplyWindowInsets(
        coordinatorLayout: CoordinatorLayout,
        child: T,
        insets: WindowInsetsCompat
    ): WindowInsetsCompat {
        L.w("this...$insets")
        return super.onApplyWindowInsets(coordinatorLayout, child, insets)
    }

    override fun blocksInteractionBelow(parent: CoordinatorLayout, child: T): Boolean {
        L.w("this....")
        return super.blocksInteractionBelow(parent, child)
    }

    /**[blocksInteractionBelow]*/
    override fun getScrimOpacity(parent: CoordinatorLayout, child: T): Float {
        return super.getScrimOpacity(parent, child).apply {
            L.w("this....scrimOpacity:$this")
        }
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: T, ev: MotionEvent): Boolean {
        L.w("this...${child.javaClass.simpleName}...${MotionEvent.actionToString(ev.actionMasked)}")
        return super.onTouchEvent(parent, child, ev)
    }

    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: T, ev: MotionEvent): Boolean {
        L.w("this...${child.javaClass.simpleName}...${MotionEvent.actionToString(ev.actionMasked)}")
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    override fun onAttachedToLayoutParams(params: CoordinatorLayout.LayoutParams) {
        super.onAttachedToLayoutParams(params)
        L.w("this...")
    }

    /**
     * @param axes 事件的方向:  1:[ViewCompat.SCROLL_AXIS_HORIZONTAL] 2:[ViewCompat.SCROLL_AXIS_VERTICAL]
     * @param type 滚动事件类型: 0:scroll 1:fling
     * */
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: T,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        L.w("this...${target.javaClass.simpleName} axes:$axes type:$type")
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onMeasureChild(
        parent: CoordinatorLayout,
        child: T,
        parentWidthMeasureSpec: Int,
        widthUsed: Int,
        parentHeightMeasureSpec: Int,
        heightUsed: Int
    ): Boolean {
        L.w("this...widthUsed:$widthUsed heightUsed:$heightUsed")
        return super.onMeasureChild(
            parent,
            child,
            parentWidthMeasureSpec,
            widthUsed,
            parentHeightMeasureSpec,
            heightUsed
        )
    }
}