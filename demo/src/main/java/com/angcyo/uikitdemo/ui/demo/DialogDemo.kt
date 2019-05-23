package com.angcyo.uikitdemo.ui.demo

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.kotlin.dialog.*
import com.angcyo.uiview.less.kotlin.toColor
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.angcyo.uiview.less.resources.RDrawable
import com.angcyo.uiview.less.utils.TopToast
import com.bigkoo.pickerview.view.WheelTime
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

                        items = mutableListOf(
                            "Item1",
                            "Item2",
                            "Item3",
                            "Item4",
                            "Item5",
                            "Item6",
                            "Item7",
                            "Item8",
                            "Item9",
                            "Item10"
                        )

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
                            false
                        }
                    }
                }

                holder.click(R.id.menu_choice_dialog) {
                    singleChoiceDialog {
                        dialogTitle = "小分队出发"

                        items = mutableListOf("Item1", "Item2", "Item3")

                        onChoiceItemList = { _, indexList ->
                            TopToast.show(indexList.toString())
                            false
                        }
                    }
                }

                holder.click(R.id.menu_multi_dialog) {
                    multiChoiceDialog {
                        dialogTitle = "弟弟"

                        items = mutableListOf("Item1", "Item2", "Item3")

                        defaultSelectorIndexList = mutableListOf(0, 2)

                        onChoiceItemList = { _, indexList ->
                            TopToast.show(indexList.toString())
                            false
                        }
                    }
                }

                holder.click(R.id.input_single_dialog) {
                    inputDialog {
                        showSoftInput = true

                        hintInputString = "客官输入点东西吧..."

                        onInputResult = { dialog, inputText ->
                            TopToast.show(inputText)
                            false
                        }
                    }
                }

                holder.click(R.id.input_multi_dialog) {
                    multiInputDialog {
                        dialogTitle = "请输入"

                        onInputResult = { dialog, inputText ->
                            TopToast.show(inputText)
                            false
                        }
                    }
                }

                holder.click(R.id.grid_dialog) {
                    gridDialog {
                        appendItem {
                            gridItemIcon = R.drawable.ic_building_collect
                            gridItemBgDrawable = RDrawable.get(requireContext()).circle("#3796F6".toColor()).get()
                            gridItemText = "走 访"
                        }
                        appendItem {
                            gridItemIcon = R.drawable.ic_building_collect
                            gridItemBgDrawable = RDrawable.get(requireContext()).circle("#00BA8A".toColor()).get()
                            gridItemText = "添加人口"
                        }
                        appendItem {
                            gridItemIcon = R.drawable.ic_building_collect
                            gridItemBgDrawable = RDrawable.get(requireContext()).circle("#F5BA00".toColor()).get()
                            gridItemText = "注销人口"
                        }
                        appendItem {
                            gridItemIcon = R.drawable.ic_building_collect
                            gridItemBgDrawable = RDrawable.get(requireContext()).circle("#00BA8A".toColor()).get()
                            gridItemText = "房屋信息"
                        }
                        appendItem {
                            gridItemIcon = R.drawable.ic_building_collect
                            gridItemBgDrawable = RDrawable.get(requireContext()).circle("#3796F6".toColor()).get()
                            gridItemText = "房屋相册"
                        }
                    }
                }

                holder.click(R.id.all_dialog) {
                    dateDialog {
                        dialogTitle = "日期时间"
                        type = booleanArrayOf(true, true, true, true, true, true)
                        onDateSelectListener = { dialog, date ->
                            TopToast.show(WheelTime.dateFormat.format(date))
                            false
                        }
                    }
                }

                holder.click(R.id.date_dialog) {
                    dateDialog {
                        dialogTitle = "出生日期"
                        onDateSelectListener = { dialog, date ->
                            TopToast.show(WheelTime.dateFormat.format(date))
                            false
                        }
                    }
                }

                holder.click(R.id.time_dialog) {
                    dateDialog {
                        dialogTitle = "时间选择"
                        type = booleanArrayOf(false, false, false, true, true, true)
                        onDateSelectListener = { dialog, date ->
                            TopToast.show(WheelTime.dateFormat.format(date))
                            false
                        }
                    }
                }

                //popup
                holder.click(R.id.normal_popup) {
                    popupWindow(it) {
                        layoutId = R.layout.item_dialog_demo
                        background = ColorDrawable(Color.RED)
                        onDismiss = {
                            L.i("...dismiss...")
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