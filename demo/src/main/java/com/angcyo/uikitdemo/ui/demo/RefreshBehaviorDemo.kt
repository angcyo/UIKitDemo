package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseTitleFragment
import com.angcyo.uikitdemo.ui.behavior.RefreshContentBehavior
import com.angcyo.uikitdemo.ui.behavior.RefreshHeaderBehavior
import com.angcyo.uikitdemo.来点数据
import com.angcyo.uiview.less.kotlin.coordinatorParams
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapter

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/11/15
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class RefreshBehaviorDemo : AppBaseTitleFragment() {
    override fun getContentLayoutId(): Int {
        return R.layout.demo_refresh_behavior_layout
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        viewHolder.view(R.id.header_view).coordinatorParams {
            behavior = RefreshHeaderBehavior()
        }
        viewHolder.rv(R.id.recycler_view).apply {
            coordinatorParams {
                behavior = RefreshContentBehavior()
            }
            adapter = DslAdapter().apply {
                来点数据()
            }
        }
    }
}