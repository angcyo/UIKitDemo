package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseTitleFragment
import com.angcyo.uikitdemo.ui.behavior.StickHeaderBehavior
import com.angcyo.uikitdemo.ui.demo.sub.LoadMoreActivity
import com.angcyo.uiview.less.kotlin.coordinatorParams
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.widget.pager.RFragmentAdapter

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/12/02
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class LinkageBehaviorDemo2 : AppBaseTitleFragment() {
    override fun getContentLayoutId(): Int {
        return R.layout.demo_linkage_behavior_layout2
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

//        val topRecyclerView = viewHolder.rv(R.id.top_recycler_view)
//        topRecyclerView.adapter = DslAdapter().apply {
//            来点数据()
//        }
//
//        topRecyclerView.coordinatorParams {
//            behavior = StickHeaderOldBehavior()
//        }

        val topWrapLayout = viewHolder.view(R.id.top_wrap_layout)
        topWrapLayout.coordinatorParams {
            behavior = StickHeaderBehavior(mAttachContext)
        }

        val viewPager = viewHolder.pager(R.id.view_pager)
        viewPager.adapter = RFragmentAdapter(
            childFragmentManager,
            listOf(
                RecyclerViewDemo(),
                LoadMoreActivity(),
                SpanDemo(),
                WidgetDemo(),
                SoftInputDemo(),
                ViewPager2Demo()
            )
        )

        viewHolder.tab(R.id.tab_layout).setupViewPager(viewPager)

    }
}