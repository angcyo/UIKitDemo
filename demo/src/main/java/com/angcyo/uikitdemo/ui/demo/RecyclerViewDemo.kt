package com.angcyo.uikitdemo.ui.demo

import android.graphics.Color
import android.os.Bundle
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseTitleFragment
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.recycler.HoverItemDecoration
import com.angcyo.uiview.less.recycler.RBaseItemDecoration
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslDateFilter
import com.angcyo.uiview.less.utils.TopToast

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/05/08
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class RecyclerViewDemo : AppBaseTitleFragment() {
    override fun getContentLayoutId(): Int {
        return R.layout.demo_recycler_view
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)
        gridLayoutTest()

        viewHolder.click(R.id.grid_button) {
            gridLayoutTest()
        }

        viewHolder.click(R.id.linear_button) {
            linearLayoutTest()
        }

        baseViewHolder.rv(R.id.recycler_view).apply {
            noItemAnim()
            HoverItemDecoration().attachToRecyclerView(this)
        }
    }

    fun gridLayoutTest() {

        baseViewHolder.rv(R.id.recycler_view).apply {
            clearItemDecoration {
                it is HoverItemDecoration
            }

            dslAdapter(4) {
                val dslAdapter = this
                for (i in 0..2) {
                    renderItem {
                        itemLayoutId = R.layout.item_image_little
                    }
                }

                for (i in 0..5) {

                    renderItem {
                        itemSpanCount = 4

                        itemIsGroupHead = true

                        itemLayoutId = R.layout.item_text

                        itemBind = { itemHolder, itemPosition, adapterItem ->
                            L.i("bind...$itemPosition")

                            itemHolder.tv(R.id.text_view).text = "位置$itemPosition"

                            itemHolder.clickItem {
                                TopToast.show("点击位置:$itemPosition", -1)
                            }

                            itemHolder.cV(R.id.check_box).isChecked = !adapterItem.itemGroupExtend

                            itemHolder.click(R.id.check_box) {
                                //TopToast.show("CheckBox:$itemPosition", -1)
                                dslAdapter.foldItem(adapterItem, adapterItem.itemGroupExtend)
                            }
                        }
                    }

                    for (i in 0..5) {
                        renderItem {
                            itemLayoutId = R.layout.item_image_little
                        }
                    }
                }

                dslDateFilter = DslDateFilter(this)
            }
        }
    }

    fun linearLayoutTest() {

        baseViewHolder.rv(R.id.recycler_view).apply {
            resetLayoutManager(context, "V")
            clearItemDecoration {
                it is HoverItemDecoration
            }

            dslAdapter {
                val dslAdapter = this

                for (i in 0..2) {
                    renderItem {
                        itemLayoutId = R.layout.item_image
                    }
                }

                for (i in 0..5) {

                    renderItem {
                        itemLayoutId = R.layout.item_text

                        itemIsGroupHead = true

                        itemBind = { itemHolder, itemPosition, adapterItem ->
                            itemHolder.tv(R.id.text_view).text = "位置$itemPosition"

                            itemHolder.clickItem {
                                TopToast.show("点击位置:$itemPosition", -1)
                            }

                            itemHolder.cV(R.id.check_box).isChecked = !adapterItem.itemGroupExtend

                            itemHolder.click(R.id.check_box) {
                                //TopToast.show("CheckBox:$itemPosition", -1)
                                dslAdapter.foldItem(adapterItem, adapterItem.itemGroupExtend)
                            }
                        }
                    }

                    for (i in 0..1) {
                        renderItem {
                            itemLayoutId = R.layout.item_image
                        }
                    }
                }

                dslDateFilter = DslDateFilter(this)
            }

            addItemDecoration(RBaseItemDecoration(10 * dpi, Color.GREEN))

            addItemDecoration(RBaseItemDecoration(20 * dpi, Color.RED))
        }
    }
}