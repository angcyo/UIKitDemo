package com.angcyo.uikitdemo.ui.item

import android.widget.TextView
import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/08/09
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class DslTextItem : DslAdapterItem() {
    init {
        itemLayoutId = R.layout.item_text_layout
    }

    override fun onItemBind(
        itemHolder: RBaseViewHolder,
        itemPosition: Int,
        adapterItem: DslAdapterItem
    ) {
        super.onItemBind(itemHolder, itemPosition, adapterItem)
        itemHolder.v<TextView>(R.id.text_view).text = "文本位置:$itemPosition"
    }
}