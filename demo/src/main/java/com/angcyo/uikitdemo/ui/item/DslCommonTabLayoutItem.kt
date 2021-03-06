package com.angcyo.uikitdemo.ui.item

import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.demo.sub.randomColor
import com.angcyo.uiview.less.dsl.tablayout.DslTabLayout
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem


/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/11/23
 */
class DslCommonTabLayoutItem : DslAdapterItem() {

    init {
        itemLayoutId = R.layout.item_common_tab_layout
    }

    override fun onItemBind(
        itemHolder: RBaseViewHolder,
        itemPosition: Int,
        adapterItem: DslAdapterItem
    ) {
        super.onItemBind(itemHolder, itemPosition, adapterItem)

        itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {

            if (itemLayoutId == R.layout.item_common_tab_layout2) {
                setBackgroundColor(randomColor())
            }

            tabIndicator.indicatorDrawable =
                itemHolder.itemView.context.getDrawable(R.drawable.indicator_white_line)
        }
    }
}