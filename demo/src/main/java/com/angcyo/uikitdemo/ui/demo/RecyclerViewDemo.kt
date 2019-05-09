package com.angcyo.uikitdemo.ui.demo

import android.graphics.Color
import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseTitleFragment
import com.angcyo.uiview.less.kotlin.clearItemDecoration
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.kotlin.dslAdapter
import com.angcyo.uiview.less.kotlin.renderItem
import com.angcyo.uiview.less.recycler.HoverItemDecoration
import com.angcyo.uiview.less.recycler.RBaseItemDecoration
import com.angcyo.uiview.less.recycler.RBaseViewHolder
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


    //分割线的位置
    val overPositionList = mutableListOf<Int>()

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
            HoverItemDecoration().attachToRecyclerView(this) {
                decorationOverLayoutType = {
                    R.layout.item_text
                }

                haveOverDecoration = {
                    overPositionList.contains(it)
                }
//
////                isOverDecorationSame = { _, p1, p2 ->
////                    var same = false
////
////                    for (i in overPositionList.size - 1 downTo 1) {
////                        val l1 = overPositionList[i]
////                        val l2 = overPositionList[i - 1]
////                        if ((p1 < l1 && p2 < l1) || (p1 == l1 && p2 == l1)) {
////                            if (p1 >= l2 && p2 >= l2) {
////                                same = true
////                                break
////                            }
////                        }
////                    }
////
//////                    val minValue = Math.min(p1, p2)
//////                    val maxValue = Math.max(p1, p2)
//////
//////                    for (i in 0 until overPositionList.size) {
//////                        val value = overPositionList[i]
//////                        if (value >= minValue && value < maxValue) {
//////                            same = false
//////                            break
//////                        }
//////                    }
////
////                    same
////                }
            }
        }
    }

    fun gridLayoutTest() {
        overPositionList.clear()

        baseViewHolder.rv(R.id.recycler_view).apply {
            clearItemDecoration {
                it is HoverItemDecoration
            }

            dslAdapter(4) {
                for (i in 0..2) {
                    renderItem {
                        itemLayoutId = R.layout.item_image_little
                    }
                }

                for (i in 0..5) {
                    overPositionList.add(i * 7 + 3)

                    renderItem {
                        itemSpanCount = 4

                        itemLayoutId = R.layout.item_text

                        itemBind = { itemHolder, itemPosition, adapterItem ->
                            itemHolder.tv(R.id.text_view).text = "位置$itemPosition"

                            itemHolder.clickItem {
                                TopToast.show("点击位置:$itemPosition", -1)
                            }

                            itemHolder.click(R.id.check_box) {
                                TopToast.show("CheckBox:$itemPosition", -1)
                            }
                        }
                    }

                    for (i in 0..5) {
                        renderItem {
                            itemLayoutId = R.layout.item_image_little
                        }
                    }
                }
            }
        }
    }

    fun linearLayoutTest() {
        overPositionList.clear()

        baseViewHolder.rv(R.id.recycler_view).apply {
            resetLayoutManager(context, "V")
            clearItemDecoration {
                it is HoverItemDecoration
            }

            dslAdapter {
                for (i in 0..2) {
                    renderItem {
                        itemLayoutId = R.layout.item_image
                    }
                }

                for (i in 0..5) {
                    overPositionList.add(i * 3 + 3)

                    renderItem {
                        itemLayoutId = R.layout.item_text

                        itemBind = { itemHolder, itemPosition, adapterItem ->
                            itemHolder.tv(R.id.text_view).text = "位置$itemPosition"

                            itemHolder.clickItem {
                                TopToast.show("点击位置:$itemPosition", -1)
                            }

                            itemHolder.click(R.id.check_box) {
                                TopToast.show("CheckBox:$itemPosition", -1)
                            }
                        }
                    }

                    for (i in 0..1) {
                        renderItem {
                            itemLayoutId = R.layout.item_image
                        }
                    }
                }
            }

            addItemDecoration(RBaseItemDecoration(10 * dpi, Color.GREEN))

            addItemDecoration(RBaseItemDecoration(20 * dpi, Color.RED))
        }
    }
}