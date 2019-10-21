package com.angcyo.uikitdemo.ui.demo.sub

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.ui.item.DslDemoItem
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.dslitem.DslAdapterStatusItem
import com.angcyo.uiview.less.recycler.adapter.isFirstPosition
import com.angcyo.uiview.less.recycler.adapter.isLastPosition
import com.angcyo.uiview.less.recycler.adapter.isOnlyOne
import com.angcyo.uiview.less.recycler.dslitem.dslItem
import kotlin.random.Random

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/10/16
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class GroupDemoActivity : AppBaseDslRecyclerFragment() {

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        recyclerView.setPadding(10 * dpi, 0, 10 * dpi, 0)

        baseDslAdapter.setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_LOADING)

        baseViewHolder.postDelay(1000) {
            baseDslAdapter.setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_NONE)

            renderDslAdapter {

                for (i in 0..Random.nextInt(2, 6)) {
                    dslItem(R.layout.item_group_head) {
                        itemIsGroupHead = true //启动分组折叠
                        itemIsHover = false //关闭悬停
                        itemGroups = mutableListOf("group${i + 1}")
                        itemTopInsert = 10 * dpi

                        onItemBindOverride = { itemHolder, itemPosition, adapterItem ->
                            itemHolder.tv(R.id.fold_button).text =
                                if (itemGroupExtend) "折叠 $itemPosition" else "展开 $itemPosition"

                            itemHolder.tv(R.id.text_view).text = "分组${i + 1}"

                            itemHolder.click(R.id.fold_button) {
                                itemGroupExtend = !itemGroupExtend
                            }

                            itemGroupParams.apply {
                                if (isOnlyOne()) {
                                    itemHolder.itemView
                                        .setBackgroundResource(R.drawable.shape_group_all)
                                } else if (isFirstPosition()) {
                                    itemHolder.itemView
                                        .setBackgroundResource(R.drawable.shape_group_header)
                                } else {
                                    itemHolder.itemView
                                        .setBackgroundColor(resources.getColor(R.color.colorAccent))
                                }
                            }
                        }
                    }

                    for (j in 0..Random.nextInt(4, 14)) {
                        dslItem(DslDemoItem()) {
                            itemGroups = mutableListOf("group${i + 1}")
                            itemText = "我是第${i + 1}组的第 $j 条数据"

                            onItemBindOverride = { itemHolder, _, _ ->
                                itemGroupParams.apply {
                                    if (isLastPosition()) {
                                        itemHolder.itemView
                                            .setBackgroundResource(R.drawable.shape_group_footer)
                                    } else {
                                        itemHolder.itemView
                                            .setBackgroundColor(resources.getColor(R.color.default_base_white))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
