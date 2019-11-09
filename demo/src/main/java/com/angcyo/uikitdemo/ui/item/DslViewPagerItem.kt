package com.angcyo.uikitdemo.ui.item

import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem
import com.angcyo.uiview.less.widget.pager.RFragmentAdapter

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/11/09
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class DslViewPagerItem : DslAdapterItem() {
    init {
        itemLayoutId = R.layout.app_view_pager_item
    }

    var pagerAdapter: RFragmentAdapter? = null

    override fun onItemBind(
        itemHolder: RBaseViewHolder,
        itemPosition: Int,
        adapterItem: DslAdapterItem
    ) {
        super.onItemBind(itemHolder, itemPosition, adapterItem)

        itemHolder.rpager(R.id.view_pager).apply {
            itemHolder.tab(R.id.tab_layout).setupViewPager(this)
            adapter = pagerAdapter
        }
    }
}