package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.来点数据
import com.angcyo.uiview.less.base.BaseBehaviorDslRecyclerFragment
import com.angcyo.uiview.less.recycler.RBaseViewHolder

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019-10-12
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class BaseCollapsingBehaviorDslDemo : BaseBehaviorDslRecyclerFragment() {

    init {
        isCollapseTitle = true
    }

    override fun isFirstNeedLoadData(): Boolean {
        return false
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        enableRefreshAffect()

        来点数据(2, 5)
    }
}