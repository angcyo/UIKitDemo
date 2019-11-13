package com.angcyo.uikitdemo.ui.item

import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/10/16
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class DslDemoItem : DslAdapterItem() {
    init {
        itemLayoutId = R.layout.item_demo_list
    }

    var itemText: CharSequence? = null

    override fun onItemBind(
        itemHolder: RBaseViewHolder,
        itemPosition: Int,
        adapterItem: DslAdapterItem
    ) {
        super.onItemBind(itemHolder, itemPosition, adapterItem)
        itemHolder.tv(R.id.text_view).text = itemText
    }
}