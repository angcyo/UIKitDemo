package com.angcyo.uikitdemo.ui.demo.sub

import android.graphics.Color
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
class SelectorDemoActivity : AppBaseDslRecyclerFragment() {

    override fun getContentLayoutId(): Int {
        return R.layout.activity_selector_demo
    }

    //固定选中
    val fixedItemList = mutableListOf<DslAdapterItem>()
    var isSelectorAll = false

    override fun enableRefreshAffect() {
        //super.enableRefreshAffect()
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        //滑动选择支持
        recyclerView.addOnItemTouchListener(SlidingSelectorHelper(mAttachContext, baseDslAdapter))

        //单选/多选 监听
        baseDslAdapter.itemSelectorHelper.onItemSelectorListener = object :
            OnItemSelectorListener {
            override fun onSelectorItemChange(
                selectorItems: MutableList<DslAdapterItem>,
                selectorIndexList: MutableList<Int>,
                isSelectorAll: Boolean,
                selectorParams: SelectorParams
            ) {
                super.onSelectorItemChange(
                    selectorItems,
                    selectorIndexList,
                    isSelectorAll,
                    selectorParams
                )

                this@SelectorDemoActivity.isSelectorAll = isSelectorAll

                baseViewHolder.tv(R.id.tip_view).text = when {
                    isSelectorAll -> "全部选中, 共 ${selectorItems.size} 项"
                    selectorItems.isEmpty() -> "未选中"
                    else -> "选中: ${selectorItems.size} 项"
                }
            }
        }

        //控制按钮事件
        baseViewHolder.click(R.id.normal) {
            baseDslAdapter.itemSelectorHelper.selectorAll(SelectorParams(selector = false.toSelectOption()))
            baseDslAdapter.normalModel()
        }
        baseViewHolder.click(R.id.single) {
            baseDslAdapter.itemSelectorHelper.selectorAll(SelectorParams(selector = false.toSelectOption()))
            baseDslAdapter.singleModel()
        }
        baseViewHolder.click(R.id.multi) {
            baseDslAdapter.itemSelectorHelper.selectorAll(SelectorParams(selector = false.toSelectOption()))
            baseDslAdapter.multiModel()
        }
        baseViewHolder.click(R.id.all) {
            baseDslAdapter.multiModel()
            baseDslAdapter.itemSelectorHelper.selectorAll(SelectorParams(selector = (!isSelectorAll).toSelectOption()))
        }

        //设置span size
        val spanCount = 4
        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (!baseDslAdapter.dslAdapterStatusItem.isInAdapterStatus() ||
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

        //渲染adapter数据
        renderDslAdapter {
            //默认的选择模式
            itemSelectorHelper.selectorModel = MODEL_MULTI

            //切换到加载中...
            setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_LOADING)

            //模拟网络操作
            baseViewHolder.postDelay(1000) {
                setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_NONE)

                onBaseRefresh(null)
            }
        }
    }

    override fun onBaseLoadData() {
        super.onBaseLoadData()
        renderDslAdapter {

            baseDslAdapter.clearItems()

            for (i in 0..nextInt(160, 360)) {
                dslItem(DslDemoItem()) {

                    //初始化固定列表
                    if (i < 10 && i % 3 == 0) {
                        fixedItemList.add(this)
                    }

                    onSetItemOffset = {
                        val offset = 10 * dpi
                        it.set(0, offset, offset, 0)
                        itemGroupParams.apply {
                            if (isEdgeLeft()) {
                                it.left = 10 * dpi
                            }
                            if (isEdgeGroupBottom()) {
                                it.bottom = 10 * dpi
                            }
                        }
                    }
                    onItemBindOverride = { itemHolder, itemPosition, adapterItem ->
                        itemHolder.itemView.apply {
                            setBackgroundColor(
                                when {
                                    fixedItemList.contains(adapterItem) -> Color.GRAY
                                    itemIsSelected -> Color.GREEN
                                    else -> Color.WHITE
                                }
                            )
                        }
                        itemHolder.tv(R.id.text_view).apply {
                            height = 100 * dpi
                            text =
                                "选我 $itemPosition \n${if (itemIsSelected) "true" else "false"}"
                        }
                    }
                    onItemClick = {
                        updateItemSelector(!itemIsSelected)
                    }
                }
            }

            dslDateFilter?.also {
                it.addDispatchUpdatesListener(object : OnDispatchUpdatesListener {
                    override fun onDispatchUpdatesAfter(dslAdapter: DslAdapter) {
                        it.removeDispatchUpdatesListener(this)
                        //固定选项
                        itemSelectorHelper.fixedSelectorItemList = fixedItemList
                    }
                })
            }
        }
    }
}
