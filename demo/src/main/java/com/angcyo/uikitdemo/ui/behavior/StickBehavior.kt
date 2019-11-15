package com.angcyo.uikitdemo.ui.behavior

import android.graphics.Color
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.angcyo.uiview.less.widget.behavior.BaseDependsBehavior

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/11/13
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class StickBehavior : BaseDependsBehavior<View>() {
    init {
        showLog = true
    }

    override fun getScrimColor(parent: CoordinatorLayout, child: View): Int {
        super.getScrimColor(parent, child)
        return Color.RED
    }

    override fun getScrimOpacity(parent: CoordinatorLayout, child: View): Float {
        super.getScrimOpacity(parent, child)
        return 0f
    }
}