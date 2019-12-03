package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseTitleFragment
import com.angcyo.uikitdemo.ui.behavior.StickHeaderBehavior
import com.angcyo.uikitdemo.ui.demo.sub.LoadMoreActivity
import com.angcyo.uikitdemo.来点数据
import com.angcyo.uiview.less.kotlin.coordinatorParams
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapter
import com.angcyo.uiview.less.widget.pager.RFragmentAdapter

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/12/02
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class LinkageBehaviorRVDemo : AppBaseTitleFragment() {
    override fun getContentLayoutId(): Int {
        return R.layout.demo_linkage_behavior_layout_rv
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        val stickHeaderBehavior = StickHeaderBehavior(mAttachContext)

        val topRecyclerView = viewHolder.rv(R.id.top_recycler_view)
        topRecyclerView.adapter = DslAdapter().apply {
            来点数据()
        }

        topRecyclerView.coordinatorParams {
            behavior = stickHeaderBehavior
        }

        viewHolder.click(R.id.close_view) {
            stickHeaderBehavior.stickToClose()
        }

        viewHolder.click(R.id.open_view) {
            stickHeaderBehavior.stickToOpen()
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