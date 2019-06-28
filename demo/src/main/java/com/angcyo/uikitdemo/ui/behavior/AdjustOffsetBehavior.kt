package com.angcyo.uikitdemo.ui.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/28
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
open class AdjustOffsetBehavior(val context: Context, attrs: AttributeSet? = null) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is RecyclerView || dependency is SmartRefreshLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        if (dependency.top in 0..child.measuredHeight) {
            val factor = (child.measuredHeight - dependency.top) * 1f / child.measuredHeight
            val newTop = -(factor / 2 * child.measuredHeight).toInt()

            ViewCompat.offsetTopAndBottom(child, newTop - child.top)

            return true
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}