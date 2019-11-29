package com.angcyo.uikitdemo.ui.item

import android.graphics.Color
import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/11/25
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class DslColorItem : DslAdapterItem() {
    init {
        itemLayoutId = R.layout.item_color_layout
    }

    var itemColor = Color.WHITE
    var itemText: CharSequence? = null

    override fun onItemBind(
        itemHolder: RBaseViewHolder,
        itemPosition: Int,
        adapterItem: DslAdapterItem
    ) {
        super.onItemBind(itemHolder, itemPosition, adapterItem)
        itemHolder.itemView.setBackgroundColor(itemColor)
        itemHolder.tv(R.id.text_view).text = itemText
    }
}