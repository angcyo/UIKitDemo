package com.angcyo.uikitdemo.ui.item

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.demo.sub.getColorFragmentList
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/11/25
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class DslTabViewPagerItem(val fragmentManager: FragmentManager) : DslAdapterItem() {
    init {
        itemLayoutId = R.layout.item_view_pager_layout
    }

    val fragmentList = getColorFragmentList(10)

    override fun onItemBind(
        itemHolder: RBaseViewHolder,
        itemPosition: Int,
        adapterItem: DslAdapterItem
    ) {
        super.onItemBind(itemHolder, itemPosition, adapterItem)

        itemHolder.v<ViewPager>(R.id.view_pager).apply {
            adapter =
                object : FragmentStatePagerAdapter(fragmentManager) {
                    override fun getItem(position: Int): Fragment {
                        return fragmentList[position]
                    }

                    override fun getCount(): Int {
                        return fragmentList.size
                    }
                }

            itemHolder.clickItem {
                currentItem = if (currentItem >= 5) 0 else 5
            }
        }
    }
}