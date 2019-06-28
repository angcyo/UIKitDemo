package com.angcyo.uikitdemo.ui.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import com.angcyo.uiview.less.kotlin.offsetTop
import com.angcyo.uiview.less.utils.UI
import kotlin.math.min

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/28
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
open class OffsetBehavior(val context: Context, attrs: AttributeSet? = null) : LogBehavior<View>(context, attrs) {

    //当前Top偏移量
    var offsetTop = -1

    init {
        debug = false
    }

    /**顶部最大偏移距离*/
    private fun getOffsetTopMax(parent: CoordinatorLayout, child: View): Int {
        var offsetTop = 0
        val firstChild = parent.getChildAt(0)
        if (firstChild != child) {
            offsetTop = firstChild.measuredHeight
        }
        return offsetTop
    }

    /**
     * 关闭嵌套的内嵌滚动
     * [android:nestedScrollingEnabled="false"]
     * */
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
//        if (target != directTargetChild) {
//            //关闭嵌套的内嵌滚动
//            target.isNestedScrollingEnabled = false
//        }
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        val offsetTopMax = getOffsetTopMax(parent, child)
        if (offsetTopMax > 0) {
            if (offsetTop == -1) {
                offsetTop = offsetTopMax
            }
            val layoutParams: CoordinatorLayout.LayoutParams = child.layoutParams as CoordinatorLayout.LayoutParams

            val top = offsetTop + layoutParams.topMargin
            child.layout(
                layoutParams.leftMargin,
                top,
                parent.measuredWidth - parent.paddingRight - layoutParams.rightMargin,
                top + child.measuredHeight
            )
            return true
        } else {
            return super.onLayoutChild(parent, child, layoutDirection)
        }
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

        if (dy < 0) {
            //手指往下滑动
            if (!UI.canChildScrollUp(target)) {
                //RecyclerView的顶部没有滚动空间
                val offsetTopMax = getOffsetTopMax(coordinatorLayout, child)

                if (offsetTop < offsetTopMax) {
                    val consumedY = min(-dy, offsetTopMax - offsetTop)
                    consumed[1] = -consumedY

                    child.offsetTop(consumedY)
                    offsetTop = child.top
                }
            }
        } else if (dy > 0) {
            //手指往上滑动
            if (offsetTop > 0) {
                val consumedY = min(dy, offsetTop)
                consumed[1] = consumedY

                child.offsetTop(-consumedY)
                offsetTop = child.top
            }
        }
    }
}