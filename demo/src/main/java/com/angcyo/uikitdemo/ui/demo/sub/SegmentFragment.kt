package com.angcyo.uikitdemo.ui.demo.sub

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.item.DslSegmentTabLayoutItem
import com.angcyo.uikitdemo.ui.item.DslTabViewPagerItem
import com.angcyo.uiview.less.dsl.tablayout.DslTabLayout
import com.angcyo.uiview.less.recycler.RBaseViewHolder

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/11/23
 */
class SegmentFragment : BaseTabLayoutFragment() {
    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        renderDslAdapter {

            DslSegmentTabLayoutItem()() {
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                    }
                }
            }

            DslSegmentTabLayoutItem()() {
                itemLayoutId = R.layout.item_segment_tab_layout2
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                    }
                }
            }

            DslSegmentTabLayoutItem()() {
                itemLayoutId = R.layout.item_segment_tab_layout3
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                    }
                }
            }

            DslSegmentTabLayoutItem()() {
                itemLayoutId = R.layout.item_segment_tab_layout4
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                    }
                }
            }

            DslSegmentTabLayoutItem()() {
                itemLayoutId = R.layout.item_segment_tab_layout5
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                    }
                }
            }

            DslSegmentTabLayoutItem()() {
                itemLayoutId = R.layout.item_segment_tab_layout6
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                    }
                }
            }

            DslSegmentTabLayoutItem()() {
                itemLayoutId = R.layout.item_segment_tab_layout7
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                    }
                }
            }
            DslSegmentTabLayoutItem()() {
                itemLayoutId = R.layout.item_segment_tab_layout_selector
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                    }
                }
            }

            DslTabViewPagerItem(childFragmentManager)() {
                onItemBindOverride = { itemHolder, _, _ ->
                    setViewPager(itemHolder.v(R.id.view_pager))
                }
            }
        }
    }
}