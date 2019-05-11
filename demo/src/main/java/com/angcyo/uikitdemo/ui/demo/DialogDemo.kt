package com.angcyo.uikitdemo.ui.demo

import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.kotlin.dialog.*
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.angcyo.uiview.less.utils.TopToast
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/05/10
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class DialogDemo : AppBaseItemFragment() {
    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        singleItems.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                holder.click(R.id.normal_dialog) {
                    normalDialog {
                        dialogTitle = "标题"
                        dialogMessage = "内容"

                        positiveButton { dialog, _ ->
                            TopToast.show("Test...")

                            dialog.cancel()
                        }

                        negativeButton { dialog, _ ->
                            dialog.dismiss()
                        }
                    }
                }

                holder.click(R.id.normal_ios_dialog) {
                    normalIosDialog {
                        dialogTitle = "标题"
                        dialogMessage = "内容"

                        positiveButton { dialog, _ ->
                            TopToast.show("Test...")

                            dialog.cancel()
                        }

                        negativeButton { dialog, _ ->
                            dialog.dismiss()
                        }
                    }
                }

                holder.click(R.id.item_dialog) {
                    itemsDialog {
                        dialogTitle = "标题标题标题标题标题"

                        items = mutableListOf("Item1", "Item2", "Item3")

                        onItemClick = { _, index, item ->
                            TopToast.show(item as CharSequence)
                            false
                        }
                    }
                }

                holder.click(R.id.menu_dialog) {
                    menuDialog {
                        dialogTitle = "你要干啥?"

                        items = mutableListOf("Item1", "Item2", "Item3")

                        onItemClick = { _, index, item ->
                            TopToast.show(item as CharSequence)
                            false
                        }
                    }
                }

                holder.click(R.id.wheel_dialog) {
                    wheelDialog {
                        dialogTitle = "今晚谁陪朕?"

                        wheelItems = mutableListOf("Item1", "Item2", "Item3")

                        wheelCyclic = false

                        defaultIndex = 1

                        onWheelItemSelector = { _, index, item ->
                            TopToast.show(item as CharSequence)
                        }
                    }
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_dialog_demo
            }

        })
    }

}