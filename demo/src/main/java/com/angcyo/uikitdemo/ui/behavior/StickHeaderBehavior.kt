package com.angcyo.uikitdemo.ui.behavior

import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.angcyo.lib.L
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.utils.UI
import com.angcyo.uiview.less.widget.behavior.BaseDependsBehavior

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/11/13
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class StickHeaderBehavior : BaseDependsBehavior<View>() {
    init {
        showLog = true
    }

    var childView: View? = null
    var pagerWrapView: View? = null

    /**头部[RecyclerView]*/
    var topRecyclerView: RecyclerView? = null

    /**[ViewPager]中的[RecyclerView]*/
    val bottomRecyclerView: RecyclerView?
        get() = pagerWrapView?.findRecyclerView()

    var _topFlingRecyclerView: RecyclerView? = null
    var _bottomFlingRecyclerView: RecyclerView? = null

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        childView = child
        topRecyclerView = child.findRecyclerView()
        dependency.layoutParams.coordinatorParams {
            if (behavior is StickPagerBehavior) {
                pagerWrapView = dependency
            }
        }
        return super.layoutDependsOn(parent, child, dependency)
    }

    override fun onLayoutAfter(parent: CoordinatorLayout, child: View, layoutDirection: Int) {
        super.onLayoutAfter(parent, child, layoutDirection)
        _offsetTop()
    }

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: View,
        ev: MotionEvent
    ): Boolean {
        if (ev.isDown()) {
            topRecyclerView?.stopScroll()
            bottomRecyclerView?.stopScroll()
            _bottomFlingRecyclerView?.stopScroll()
            _bottomFlingRecyclerView = null
            _topFlingRecyclerView?.stopScroll()
            _topFlingRecyclerView = null
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        super.onStartNestedScroll(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type
        )
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)

        if (dy > 0) {
            //手指向上滚动
            if (target == topRecyclerView) {
                if (!UI.canChildScrollDown(target)) {
                    if (!moveLayout(dy)) {
                        consumed[1] = dy
                    }
                }
            } else if (target == bottomRecyclerView) {
                if (!moveLayout(dy)) {
                    consumed[1] = dy
                }
            }
        } else if (dy < 0) {
            //手指向下滚动
            if (target == topRecyclerView) {
                if (!moveLayout(dy)) {
                    consumed[1] = dy
                }
            } else if (target == bottomRecyclerView) {
                if (!UI.canChildScrollUp(target)) {
                    if (!moveLayout(dy)) {
                        consumed[1] = dy
                    }
                }
            }
        }

//        if (target == topRecyclerView) {
//            if (dy > 0) {
//                if (!moveLayout(dy)) {
//                    consumed[1] = dy
//                }
//            }
//        } else if (target == bottomRecyclerView) {
//            if (dy < 0) {
//                if (!moveLayout(dy)) {
//                    consumed[1] = dy
//                }
//            }
//        }
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type
        )

        //child.offsetTop(-dyUnconsumed, -child.measuredHeight, 0)

        if (dyUnconsumed > 0) {
            if (_bottomFlingRecyclerView == null) {
                val velocityY = topRecyclerView?.getLastVelocity()?.toInt() ?: 0
                L.e("1.....$velocityY")

                if (velocityY != 0 &&
                    moveLayout(dyUnconsumed)
                ) {
                    bottomRecyclerView?.apply {
                        _bottomFlingRecyclerView = this
                        fling(0, velocityY)
                    }
                }
            }
        } else if (dyUnconsumed < 0) {
            if (_topFlingRecyclerView == null &&
                target == bottomRecyclerView
            ) {
                val velocityY = (target as? RecyclerView)?.getLastVelocity()?.toInt() ?: 0
                L.e("2.....$velocityY")

                if (velocityY != 0 &&
                    moveLayout(dyUnconsumed)
                ) {
                    topRecyclerView?.apply {
                        _topFlingRecyclerView = this
                        fling(0, -velocityY)
                    }
                }
            }
        }
    }

    var _currentOffsetTop = 0

    fun _offsetTop() {
        childView?.offsetTopTo(_currentOffsetTop)
        pagerWrapView?.offsetTopTo(childView!!.bottom)
        _currentOffsetTop = childView?.top ?: 0
    }

    /**
     * [offset]布局, 返回值表示, 是否到顶或者到底了
     * */
    fun moveLayout(dy: Int): Boolean {
        if (childView == null) {
            return true
        }
        var result = false

        childView!!.offsetTop(-dy, -childView!!.measuredHeight, 0)
        pagerWrapView?.offsetTopTo(childView!!.bottom)
        _currentOffsetTop = childView!!.top

        result = (dy < 0 && childView!!.top == 0) || (dy > 0 && childView!!.bottom == 0)

        return result
    }
}