package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.来点数据
import com.angcyo.uiview.less.recycler.RBaseViewHolder

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/28
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class CoordinatorLayoutDemo : AppBaseDslRecyclerFragment() {

    companion object {
        var count = 0
    }

    override fun getContentLayoutId(): Int {
        return when {
            count % 2 == 0 -> R.layout.demo_coordinator_layout2
            else -> R.layout.demo_coordinator_layout
        }.apply {
            count++
        }
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        来点数据()
    }
}