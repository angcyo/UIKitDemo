package com.angcyo.uikitdemo.ui.behavior

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import com.angcyo.uiview.less.kotlin.offsetTopTo
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/28
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
open class AdjustOffsetBehavior(val context: Context, attrs: AttributeSet? = null) :
    LogBehavior<View>(context, attrs) {

    init {
        debug = false
    }

    override fun layoutDependsOn(parent: androidx.coordinatorlayout.widget.CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is androidx.recyclerview.widget.RecyclerView || dependency is SmartRefreshLayout
    }

    override fun onDependentViewChanged(parent: androidx.coordinatorlayout.widget.CoordinatorLayout, child: View, dependency: View): Boolean {
        if (dependency.top in 0..child.measuredHeight) {
            val factor = (child.measuredHeight - dependency.top) * 1f / child.measuredHeight
            val newTop = -(factor / 2 * child.measuredHeight).toInt()

            child.offsetTopTo(newTop)

            return true
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}