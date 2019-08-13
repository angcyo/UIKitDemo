package com.angcyo.uikitdemo.ui.demo

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import android.widget.RadioGroup
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.base.helper.ActivityHelper
import com.angcyo.uiview.less.kotlin.dialog.*
import com.angcyo.uiview.less.kotlin.getColor
import com.angcyo.uiview.less.kotlin.toColor
import com.angcyo.uiview.less.kotlin.toast_tip
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.angcyo.uiview.less.resources.RDrawable
import com.angcyo.uiview.less.utils.RUtils
import com.angcyo.uiview.less.utils.TopToast
import com.bigkoo.pickerview.view.WheelTime
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.RCalendarView
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/05/10
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class DialogDemo : AppBaseItemFragment() {

    var dialogType = BaseDialogConfig.DIALOG_TYPE_APPCOMPAT

    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        singleItems.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                holder.v<RadioGroup>(R.id.flow_style)
                    .setOnCheckedChangeListener { group, checkedId ->
                        dialogType = when (checkedId) {
                            R.id.style_alert -> BaseDialogConfig.DIALOG_TYPE_ALERT_DIALOG
                            R.id.style_sheet -> BaseDialogConfig.DIALOG_TYPE_BOTTOM_SHEET_DIALOG
                            else -> BaseDialogConfig.DIALOG_TYPE_APPCOMPAT
                        }
                    }

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

                        dialogType = this@DialogDemo.dialogType
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

                        dialogType = this@DialogDemo.dialogType
                    }
                }

                holder.click(R.id.normal_ios_dialog2) {
                    normalIosDialog {
                        dialogMessage = "内容"

                        positiveButton { dialog, _ ->
                            TopToast.show("Test...")

                            dialog.cancel()
                        }

                        negativeButton { dialog, _ ->
                            dialog.dismiss()
                        }

                        dialogType = this@DialogDemo.dialogType
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

                        onItemClick = { _, _, item ->
                            TopToast.show(item as CharSequence)
                            false
                        }

                        dialogType = this@DialogDemo.dialogType
                    }
                }

                holder.click(R.id.item_dialog_full) {
                    itemsDialog {
                        dialogWidth = -1
                        //指定dialogHeight 可以解决状态栏变黑, 但是~高度的计算会受到影响...
                        dialogHeight = RUtils.getScreenHeight(activity)
                        dialogBgDrawable = ColorDrawable(Color.TRANSPARENT)
                        dialogTitle = "标题标题标题标题标题(全屏)"
                        windowFlags = intArrayOf(
                            WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        )

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

                        onItemClick = { _, _, item ->
                            TopToast.show(item as CharSequence)
                            false
                        }

                        dialogType = this@DialogDemo.dialogType

                        dialogInit = { dialog, dialogViewHolder ->
                            ActivityHelper.enableLayoutFullScreen(dialog.window, true)

                            dialogViewHolder.itemView.apply {
                                fitsSystemWindows = false
                                setBackgroundColor(Color.GREEN)
                                setPadding(0, 0, 0, 0)
                                layoutParams = WindowManager.LayoutParams(-1, -2)
                            }
                        }
                    }
                }

                holder.click(R.id.menu_dialog) {
                    menuDialog {
                        dialogTitle = "你要干啥?"

                        items = mutableListOf("Item1", "Item2", "Item3")

                        onItemClick = { _, _, item ->
                            TopToast.show(item as CharSequence)
                            false
                        }

                        dialogType = this@DialogDemo.dialogType
                    }
                }

                holder.click(R.id.menu_ico_dialog) {
                    menuDialog {
                        //dialogTitle = "你要干啥?"

                        items = mutableListOf("Item1", "Item2", "Item3")
                        itemIcons =
                            mutableListOf(R.drawable.ic_delete_photo, R.drawable.ic_delete_photo)

                        onItemClick = { _, _, item ->
                            TopToast.show(item as CharSequence)
                            false
                        }

                        dialogType = this@DialogDemo.dialogType
                    }
                }

                holder.click(R.id.menu_ico2_dialog) {
                    menuDialog {
                        //dialogTitle = "你要干啥?"

                        items = mutableListOf("Item1", "Item2", "Item3")
                        itemIcons =
                            mutableListOf(R.drawable.ic_delete_photo, R.drawable.ic_delete_photo)
                        itemTextGravity = Gravity.LEFT or Gravity.CENTER_VERTICAL

                        onItemClick = { _, _, item ->
                            TopToast.show(item as CharSequence)
                            false
                        }

                        dialogType = this@DialogDemo.dialogType
                    }
                }

                holder.click(R.id.wheel_dialog) {
                    wheelDialog {
                        dialogTitle = "今晚谁陪朕?"

                        wheelItems = mutableListOf("Item1", "Item2", "Item3")

                        wheelCyclic = false

                        defaultIndex = 1

                        onWheelItemSelector = { _, _, item ->
                            TopToast.show(item as CharSequence)
                            false
                        }

                        dialogType = this@DialogDemo.dialogType
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

                        dialogType = this@DialogDemo.dialogType
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

                        dialogType = this@DialogDemo.dialogType
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

                        dialogType = this@DialogDemo.dialogType
                    }
                }

                holder.click(R.id.input_multi_dialog) {
                    multiInputDialog {
                        dialogTitle = "请输入"

                        onInputResult = { dialog, inputText ->
                            TopToast.show(inputText)
                            false
                        }

                        dialogType = this@DialogDemo.dialogType
                    }
                }

                holder.click(R.id.grid_dialog) {
                    gridDialog {
                        appendItem {
                            gridItemIcon = R.drawable.ic_building_collect
                            gridItemBgDrawable =
                                RDrawable.get(requireContext()).circle("#3796F6".toColor()).get()
                            gridItemText = "走 访"
                        }
                        appendItem {
                            gridItemIcon = R.drawable.ic_building_collect
                            gridItemBgDrawable =
                                RDrawable.get(requireContext()).circle("#00BA8A".toColor()).get()
                            gridItemText = "添加人口"
                        }
                        appendItem {
                            gridItemIcon = R.drawable.ic_building_collect
                            gridItemBgDrawable =
                                RDrawable.get(requireContext()).circle("#F5BA00".toColor()).get()
                            gridItemText = "注销人口"
                        }
                        appendItem {
                            gridItemIcon = R.drawable.ic_building_collect
                            gridItemBgDrawable =
                                RDrawable.get(requireContext()).circle("#00BA8A".toColor()).get()
                            gridItemText = "房屋信息"
                        }
                        appendItem {
                            gridItemIcon = R.drawable.ic_building_collect
                            gridItemBgDrawable =
                                RDrawable.get(requireContext()).circle("#3796F6".toColor()).get()
                            gridItemText = "房屋相册"
                        }

                        dialogType = this@DialogDemo.dialogType
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

                        dialogType = this@DialogDemo.dialogType
                    }
                }

                holder.click(R.id.date_dialog) {
                    dateDialog {
                        dialogTitle = "出生日期"
                        onDateSelectListener = { dialog, date ->
                            TopToast.show(WheelTime.dateFormat.format(date))
                            false
                        }

                        dialogType = this@DialogDemo.dialogType
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

                        dialogType = this@DialogDemo.dialogType
                    }
                }

                holder.click(R.id.option_dialog) {
                    optionDialog {
                        dialogTitle = "多级选项选择"
                        onLoadOptionList = { options, level, callback, _ ->
                            callback(loadOptionList(level))
                        }
                        onCheckOptionEnd = { options, level ->
                            options.size == 4
                        }
                        onOptionResult = { _, optionList ->
                            toast_tip(RUtils.connect(optionList))
                            false
                        }

                        dialogType = this@DialogDemo.dialogType
                    }
                }
                holder.click(R.id.option_dialog2) {
                    optionDialog {
                        dialogTitle = "多级选项选择(半默认)"
                        optionList = mutableListOf("1级a", "2级b")
                        onLoadOptionList = { options, level, callback, _ ->
                            callback(loadOptionList(level))
                        }
                        onCheckOptionEnd = { options, level ->
                            options.size == 4
                        }
                        onOptionResult = { _, optionList ->
                            toast_tip(RUtils.connect(optionList))
                            false
                        }

                        dialogType = this@DialogDemo.dialogType
                    }
                }
                holder.click(R.id.option_dialog3) {
                    optionDialog {
                        dialogTitle = "多级选项选择(全默认)"
                        optionList = mutableListOf("1级a", "2级b", "3级c", "4级d")
                        onLoadOptionList = { options, level, callback, _ ->
                            callback(loadOptionList(level))
                        }
                        onCheckOptionEnd = { options, level ->
                            options.size == 4
                        }
                        onOptionResult = { _, optionList ->
                            toast_tip(RUtils.connect(optionList))
                            false
                        }

                        dialogType = this@DialogDemo.dialogType
                    }
                }

                //日历接收
                val calendarResult = { _: Dialog, calendarList: MutableList<Calendar> ->
                    toast_tip(buildString {
                        append("始:")
                        CalendarDialogConfig.ymd(this, calendarList[0])
                        appendln()
                        append("止:")
                        CalendarDialogConfig.ymd(this, calendarList[1])
                    })
                    false
                }
                holder.click(R.id.calendar_dialog) {
                    calendarDialog {
                        dialogTitle = "日历选择"
                        onCalendarResult = calendarResult
                        dialogType = this@DialogDemo.dialogType
                    }
                }

                holder.click(R.id.calendar_dialog1) {
                    calendarDialog {
                        dialogTitle = "日历选择对话框(带默认1)"
                        setCalendarRange(2018, 2020)

                        calendarList = mutableListOf(RCalendarView.today())

                        onCalendarResult = calendarResult
                        dialogType = this@DialogDemo.dialogType
                    }
                }

                holder.click(R.id.calendar_dialog2) {
                    calendarDialog {
                        dialogTitle = "日历选择对话框(带默认2)"

                        //设置日历选择范围
                        setCalendarRange(2018, 2020, 4, 8)

                        //设置日历默认选择范围
                        calendarList = mutableListOf(Calendar(2019, 2, 1), Calendar(2019, 5, 1))

                        onCalendarResult = calendarResult
                        dialogType = this@DialogDemo.dialogType
                    }
                }

                //popup
                holder.click(R.id.bottom)
                {
                    popupWindow(it) {
                        layoutId = R.layout.item_dialog_demo
                        background = ColorDrawable(Color.RED)
                        exactlyHeight = true
                        onDismiss = {
                            L.i("...dismiss...")
                            toast_tip("...dismiss...")
                        }
                        xoff = 200
                        yoff = 200
                    }
                }
                holder.click(R.id.bottom_popup) {
                    popupWindow(it) {
                        layoutId = R.layout.item_dialog_demo
                        background = ColorDrawable(Color.RED)
                        exactlyHeight = true
                        onDismiss = {
                            L.i("...dismiss...")
                            toast_tip("...dismiss...")
                        }
                        xoff = 200
                        yoff = 200
                    }
                }
                holder.click(R.id.normal_popup) {
                    popupWindow(it) {
                        layoutId = R.layout.item_dialog_demo
                        background = ColorDrawable(Color.RED)
                        onDismiss = {
                            L.i("...dismiss...")
                            toast_tip("...dismiss...")
                        }
                    }
                }

                holder.click(R.id.width_full_popup) {
                    popupWindow(it) {
                        layoutId = R.layout.item_dialog_demo
                        background = ColorDrawable(Color.RED)
                        width = -1
                        onDismiss = {
                            L.i("...dismiss...")
                            toast_tip("...dismiss...")
                        }
                    }
                }

                holder.click(R.id.full_popup) {
                    popupWindow(it) {
                        layoutId = R.layout.item_dialog_demo
                        background = ColorDrawable(Color.RED)
                        width = -1
                        height = -1
                        onDismiss = {
                            L.i("...dismiss...")
                            toast_tip("...dismiss...")
                        }
                        popupInit = { popupWindow, popupViewHolder ->
                            popupWindow.isClippingEnabled = false
                        }
                    }
                }

                holder.click(R.id.full_popup2) {
                    popupWindow(it) {
                        layoutId = R.layout.item_dialog_demo
                        width = -1
                        background = ColorDrawable(getColor(R.color.transparent_dark40))
                        exactlyHeight = true
                        onDismiss = {
                            L.i("...dismiss...")
                            toast_tip("...dismiss...")
                        }
                    }
                }

                holder.click(R.id.full_popup_title) {
                    popupWindow(titleBarLayout) {
                        layoutId = R.layout.item_dialog_demo
                        width = -1
                        background = ColorDrawable(getColor(R.color.transparent_dark40))
                        exactlyHeight = true
                        onDismiss = {
                            L.i("...dismiss...")
                            toast_tip("...dismiss...")
                        }

                        popupInit = { popupWindow, popupViewHolder ->
                            popupViewHolder.view(R.id.flow_style).setBackgroundColor(Color.RED)
                            popupViewHolder.view(R.id.flow_1).setBackgroundColor(Color.RED)
                        }
                    }
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_dialog_demo
            }
        })
    }

    fun loadOptionList(level: Int): MutableList<Any> {
        val mutableList = when (level) {
            0 -> mutableListOf("1级级级级级级级a", "1级级级级级级级b", "1级级级级级级c", "1级d", "1级e")
            1 -> mutableListOf("2级a", "2级级级级级级级级级级b", "2级c", "2级d", "2级e")
            2 -> mutableListOf("3级a", "3级级级级级级级级级b", "3级c", "3级d", "3级e")
            3 -> mutableListOf("4级a", "4级级级级级级级级级级b", "4级c", "4级d", "4级级级级级级级级e")
            else -> mutableListOf(
                "${level + 1}级a",
                "${level + 1}级b",
                "${level + 1}级c",
                "${level + 1}级d",
                "${level + 1}级e"
            )
        } as MutableList<Any>

        return mutableList
    }
}