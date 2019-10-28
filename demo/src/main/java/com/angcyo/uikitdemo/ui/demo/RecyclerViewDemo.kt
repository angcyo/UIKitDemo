package com.angcyo.uikitdemo.ui.demo

import android.graphics.Color
import android.os.Bundle
import android.widget.CompoundButton
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseTitleFragment
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.recycler.HoverItemDecoration
import com.angcyo.uiview.less.recycler.RBaseItemDecoration
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.*
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

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)
        gridLayoutTest()

        viewHolder.click(R.id.grid_button) {
            gridLayoutTest()
        }

        viewHolder.click(R.id.linear_button) {
            linearLayoutTest()
        }

        //单选 多选 切换.
        viewHolder.click(R.id.multi_box) {
            if (it is CompoundButton) {
                if (it.isChecked) {
                    baseViewHolder.rv(R.id.recycler_view)?.multiModel()
                } else {
                    baseViewHolder.rv(R.id.recycler_view)?.singleModel()
                }
            }

        }

        baseViewHolder.rv(R.id.recycler_view).apply {
            noItemAnim()
            HoverItemDecoration().attachToRecyclerView(this)
        }
    }


    fun DslAdapter.renderImage() {
        renderItem {
            itemLayoutId = R.layout.item_image
        }
    }

    fun DslAdapter.renderImageLittle() {
        renderItem {
            itemLayoutId = R.layout.item_image_little

            itemBind = { itemHolder, itemPosition, adapterItem ->
                itemHolder.tv(R.id.text_view).text = "$itemPosition $itemIsSelected"

                itemHolder.clickItem {
                    adapterItem.itemIsSelected = !adapterItem.itemIsSelected
                }
            }
        }
    }

    fun DslAdapter.renderText() {
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
                    adapterItem.itemGroupExtend = !adapterItem.itemGroupExtend
                }
            }
        }
    }

    fun gridLayoutTest() {

        baseViewHolder.rv(R.id.recycler_view).apply {
            clearItemDecoration {
                it is HoverItemDecoration
            }

            dslAdapter(4) {

                singleModel()

                //addOnSelectorModelListener(SelectModelListener())

                for (i in 0..2) {
                    renderImageLittle()
                }

                for (i in 0..5) {
                    renderText()
                    for (j in 0..5) {
                        renderImageLittle()
                    }
                }

                dslDataFilter = DslDataFilter(this)
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
                for (i in 0..2) {
                    renderImage()
                }

                for (i in 0..5) {
                    renderText()

                    for (j in 0..1) {
                        renderImage()
                    }
                }

                dslDataFilter = DslDataFilter(this)
            }

            addItemDecoration(RBaseItemDecoration(10 * dpi, Color.GREEN))

            addItemDecoration(RBaseItemDecoration(20 * dpi, Color.RED))
        }
    }
}