package com.angcyo.uikitdemo.ui.demo.sub

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.ui.item.DslDemoItem
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.*
import com.angcyo.uiview.less.recycler.dslitem.DslAdapterStatusItem
import com.angcyo.uiview.less.recycler.dslitem.dslItem
import kotlin.random.Random.Default.nextInt

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/10/16
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class GroupGridDemoActivity : AppBaseDslRecyclerFragment() {

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        val spanCount = 4
        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (!baseDslAdapter.dslAdapterStatusItem.isNoStatus() ||
                    baseDslAdapter.getItemData(position)?.itemIsGroupHead == true
                ) {
                    spanCount
                } else {
                    1
                }
            }
        }

        recyclerView.layoutManager = GridLayoutManager(mAttachContext, spanCount).apply {
            this.spanSizeLookup = spanSizeLookup
        }

        baseDslAdapter.setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_LOADING)

        baseViewHolder.postDelay(1000) {
            baseDslAdapter.setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_NONE)

            renderDslAdapter {

                for (i in 0..4) {
                    dslItem(R.layout.item_group_head) {
                        itemIsGroupHead = true //启动分组折叠
                        itemGroups = mutableListOf("group${i + 1}")
                        itemTopInsert = 10 * dpi

                        onSetItemOffset = {
                            itemGroupParams.apply {
                                if (isEdgeLeft()) {
                                    it.left = 10 * dpi
                                }
                                if (isEdgeRight()) {
                                    it.right = 10 * dpi
                                }
                                if (isEdgeGroupBottom()) {
                                    it.bottom = 10 * dpi
                                }
                            }
                        }

                        onItemBindOverride = { itemHolder, itemPosition, adapterItem ->
                            itemHolder.tv(R.id.fold_button).text =
                                if (itemGroupExtend) "折叠 $itemPosition" else "展开 $itemPosition"

                            itemHolder.tv(R.id.text_view).text =
                                "分组${i + 1}" +
                                        "    :${itemHolder.adapterPosition}/${itemHolder.layoutPosition}" +
                                        "    :${spanSizeLookup.getSpanIndex(
                                            itemPosition,
                                            spanCount
                                        )}/${spanSizeLookup.getSpanSize(itemPosition)}/${spanSizeLookup.getSpanGroupIndex(
                                            itemPosition,
                                            spanCount
                                        )}"

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

                    for (j in 0..nextInt(4, 14)) {
                        dslItem(DslDemoItem()) {
                            itemGroups = mutableListOf("group${i + 1}")
                            itemText = "我是第${i + 1}组的第 $j 条数据"

                            onSetItemOffset = {
                                itemGroupParams.apply {
                                    if (isEdgeLeft()) {
                                        it.left = 10 * dpi
                                    }
                                    if (isEdgeRight()) {
                                        it.right = 10 * dpi
                                    }
                                    if (isEdgeGroupBottom()) {
                                        it.bottom = 10 * dpi
                                    }
                                }
                            }

                            onItemBindOverride = { itemHolder, itemPosition, _ ->
                                itemGroupParams.apply {

                                    when {
                                        isEdgeGroupHorizontal() -> itemHolder.itemView
                                            .setBackgroundResource(R.drawable.shape_group_footer)
                                        isEdgeGroupLeftBottom() -> itemHolder.itemView
                                            .setBackgroundResource(R.drawable.shape_left_bottom)
                                        isEdgeGroupRightBottom() -> itemHolder.itemView
                                            .setBackgroundResource(R.drawable.shape_right_bottom)
                                        else -> itemHolder.itemView
                                            .setBackgroundColor(resources.getColor(R.color.default_base_white))
                                    }
                                }

                                itemHolder.tv(R.id.text_view).text =
                                    "${spanSizeLookup.getSpanIndex(
                                        itemPosition,
                                        spanCount
                                    )}/${spanSizeLookup.getSpanSize(itemPosition)}/${spanSizeLookup.getSpanGroupIndex(
                                        itemPosition,
                                        spanCount
                                    )}"
                            }
                        }
                    }
                }
            }
        }
    }
}
