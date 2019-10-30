package com.angcyo.uikitdemo.ui.demo.sub

import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.ui.item.dslImageItem
import com.angcyo.uikitdemo.来点数据
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.kotlin.setHeight
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem
import com.angcyo.uiview.less.recycler.dslitem.DslAdapterStatusItem
import com.angcyo.uiview.less.recycler.dslitem.dslItem
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019-10-30
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class StaggeredGridLayoutActivity : AppBaseDslRecyclerFragment() {

    override fun getRecyclerViewPool(): RecyclerView.RecycledViewPool? {
        return null
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        recyclerView.layoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)

        renderDslAdapter {
            setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_LOADING)

            baseViewHolder.postDelay(1000) {
                setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_NONE)

                dslImageItem {
                    itemSpanCount = -1
                    marginVertical(4 * dpi, 2 * dpi)
                    marginHorizontal(4 * dpi, 2 * dpi)
                }

                dslImageItem {
                    itemSpanCount = -1
                    marginVertical(4 * dpi, 2 * dpi)
                    marginHorizontal(4 * dpi, 2 * dpi)
                }

                val random = Random(SystemClock.uptimeMillis())
                for (i in 0..100) {
                    dslItem(ColorItem()) {
                        itemColor = randomColor(random)
                        marginVertical(4 * dpi, 2 * dpi)
                        marginHorizontal(4 * dpi, 2 * dpi)
                    }
                }

                来点数据()
            }

        }
    }
}

fun randomColor(random: Random): Int {
    return randomColor(random, 120, 250)
}

/**
 * 随机颜色, 设置一个最小值, 设置一个最大值, 第三个值在这2者之间随机改变
 */
fun randomColor(random: Random, minValue: Int, maxValue: Int): Int {
    val list = mutableListOf<Int>()
    while (list.size < 3) {
        val a = minValue + random.nextInt(maxValue - minValue)
        list.add(a)
    }
    return Color.rgb(list[0], list[1], list[2])
}

class ColorItem : DslAdapterItem() {
    init {
        itemLayoutId = R.layout.item_color_item
    }

    var itemColor = Color.WHITE

    override fun onItemBind(
        itemHolder: RBaseViewHolder,
        itemPosition: Int,
        adapterItem: DslAdapterItem
    ) {
        super.onItemBind(itemHolder, itemPosition, adapterItem)
        itemHolder.itemView.apply {
            setBackgroundColor(itemColor)
            setHeight(if (itemPosition % 2 == 0) 200 * dpi else 150 * dpi)
        }
        itemHolder.tv(R.id.text_view).text = "Position:$itemPosition"
    }
}