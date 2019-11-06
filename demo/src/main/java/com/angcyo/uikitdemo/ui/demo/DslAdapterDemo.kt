package com.angcyo.uikitdemo.ui.demo

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.ui.demo.sub.*
import com.angcyo.uikitdemo.ui.item.DslDemoItem
import com.angcyo.uiview.less.base.BaseFragment
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.kotlin.renderEmptyItem
import com.angcyo.uiview.less.kotlin.show
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem
import com.angcyo.uiview.less.recycler.dslitem.DslAdapterStatusItem
import com.angcyo.uiview.less.recycler.dslitem.dslItem

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/10/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class DslAdapterDemo : AppBaseDslRecyclerFragment() {
    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        renderDslAdapter {
            setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_LOADING)

            /**
             * 扩展方式 追加[DslAdapterItem]
             * */
            dslItem(DslDemoItem()) {
                itemText = "情感图状态使用示例"
                onItemClick = {
                    start(AdapterStatusActivity::class.java)
                }
                itemTopInsert = 2 * dpi //控制顶部分割线的高度
            }

            dslItem(DslDemoItem()) {
                itemText = "加载更多使用示例"
                onItemClick = {
                    start(LoadMoreActivity::class.java)
                }
                itemTopInsert = 4 * dpi
            }

            /**
             * [invoke]运算符重载方式 追加[DslAdapterItem]
             * */
            DslDemoItem()() {
                itemText = "群组(线性布局)功能示例"
                onItemClick = {
                    start(GroupDemoActivity::class.java)
                }
                itemTopInsert = 4 * dpi
            }

            DslDemoItem()() {
                itemText = "群组(网格布局)功能示例"
                onItemClick = {
                    start(GroupGridDemoActivity::class.java)
                }
                itemTopInsert = 4 * dpi
            }

            DslDemoItem()() {
                itemText = "单选/多选示例"
                onItemClick = {
                    start(SelectorDemoActivity::class.java)
                }
                itemTopInsert = 4 * dpi
            }

            DslDemoItem()() {
                itemText = "StaggeredGridLayout(拖拽/滑动删除示例)"
                onItemClick = {
                    start(StaggeredGridLayoutActivity::class.java)
                }
                itemTopInsert = 4 * dpi
            }

            renderEmptyItem()

            dslItem(R.layout.item_demo_list) {
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.itemView.setBackgroundColor(Color.TRANSPARENT)
                    itemHolder.tv(R.id.text_view).apply {
                        gravity = Gravity.CENTER
                        text = "以下是分割线展示"
                    }
                }
            }

            /**
             * [plus]运算符重载方式 追加[DslAdapterItem]
             * */
            this + DslDemoItem().apply {
                itemText = "顶部的分割线是红色"
                itemTopInsert = 8 * dpi
                itemDecorationColor = Color.RED //控制分割线的颜色
            } + DslDemoItem().apply {
                itemText = "只绘制偏移量的分割线"
                itemTopInsert = 8 * dpi
                itemLeftOffset = 60 * dpi
                itemDecorationColor = Color.BLUE
                onlyDrawOffsetArea = true
            } + DslDemoItem().apply {
                itemText = "自定义Drawable的分割线"
                itemBottomInsert = 20 * dpi
                itemDecorationDrawable = resources.getDrawable(R.drawable.shape_decoration)
            } + DslDemoItem().apply {
                itemText = "上下都有的分割线"
                itemTopInsert = 8 * dpi
                itemBottomInsert = 8 * dpi
                itemDecorationColor = Color.GREEN
            }

            /**
             * [minus]运算符重载方式, 移除[DslAdapterItem]
             * */
            this - DslAdapterItem() - DslAdapterItem() - DslAdapterItem() - DslAdapterItem()

            renderEmptyItem()

            //模拟网络延迟
            viewHolder.postDelay(1000) {
                //设置情感图状态, 正常
                setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_NONE)
            }
        }
    }
}

fun BaseFragment.start(cls: Class<out Fragment>) {
    show(cls)
}