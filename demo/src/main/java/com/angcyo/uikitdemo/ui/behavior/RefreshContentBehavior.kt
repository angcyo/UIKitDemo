package com.angcyo.uikitdemo.ui.behavior

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.angcyo.uiview.less.widget.behavior.BaseDependsBehavior

/**
 * 刷新内容 行为
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/11/15
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class RefreshContentBehavior : BaseDependsBehavior<View>() {

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        super.onLayoutChild(parent, child, layoutDirection)

        val lp = child.layoutParams as CoordinatorLayout.LayoutParams
        val left = parent.paddingLeft + lp.leftMargin
        val top = child.top
        val right = left + child.measuredWidth
        val bottom = top + child.measuredHeight

        child.layout(left, top, right, bottom)
        return true
    }

    override fun onLayoutChildAfter(parent: CoordinatorLayout, child: View, layoutDirection: Int) {
        super.onLayoutChildAfter(parent, child, layoutDirection)
    }
}