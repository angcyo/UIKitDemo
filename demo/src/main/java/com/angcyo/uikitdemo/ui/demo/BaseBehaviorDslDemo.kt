package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.来点数据
import com.angcyo.uiview.less.base.BaseBehaviorDslRecyclerFragment
import com.angcyo.uiview.less.kotlin.coordinatorParams
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.kotlin.renderEmptyItem
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.widget.behavior.BackgroundBehavior

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/27
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class BaseBehaviorDslDemo : BaseBehaviorDslRecyclerFragment() {

    override fun isFirstNeedLoadData(): Boolean {
        return false
    }

    override fun getBehaviorBgLayoutId(): Int {
        return R.layout.behavior_demo_background
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        enableRefreshAffect()

        viewHolder.vg(R.id.base_behavior_bg_layout).coordinatorParams {
            (behavior as? BackgroundBehavior)?.childHeight = 380 * dpi
        }

        renderDslAdapter {
            renderEmptyItem(340 * dpi)
        }

        来点数据(2, 5)
    }
}