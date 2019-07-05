package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.来点数据
import com.angcyo.uiview.less.kotlin.renderItem
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.utils.TopToast

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/28
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class CoordinatorLayoutDemo : AppBaseDslRecyclerFragment() {
    override fun getContentLayoutId(): Int {
        return R.layout.demo_coordinator_layout
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        来点数据()
    }
}