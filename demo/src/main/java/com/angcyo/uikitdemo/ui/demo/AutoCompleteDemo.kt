package com.angcyo.uikitdemo.ui.demo

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.KeyEvent.*
import android.view.WindowManager
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.ListPopupWindow
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.kotlin.appendln
import com.angcyo.uiview.less.kotlin.onFocusChange
import com.angcyo.uiview.less.kotlin.onTextChange
import com.angcyo.uiview.less.kotlin.span
import com.angcyo.uiview.less.recycler.adapter.RArrayAdapter
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.angcyo.uiview.less.skin.SkinHelper
import com.angcyo.uiview.less.widget.AutoEditText
import com.angcyo.uiview.less.widget.ExEditText
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/06
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class AutoCompleteDemo : AppBaseItemFragment() {
    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        dslCreateItem {
            singleItemLayoutId = R.layout.demo_auto_complete
            singleItemBind = { holder, _, _ ->
                val dataList = mutableListOf<String>()
                for (i in 0..10) {
                    for (j in 0..10) {
                        dataList.add("$i${i}1111$j")
                    }
                }

                val adapter = RArrayAdapter(
                    mAttachContext, R.layout.item_single_drop_down_layout,
                    dataList
                )
                val adapter2 = RArrayAdapter(
                    mAttachContext, R.layout.item_single_drop_down_layout,
                    dataList
                )
                val adapter3 = RArrayAdapter(
                    mAttachContext, R.layout.item_single_drop_down_layout,
                    dataList
                )

                holder.v<AutoCompleteTextView>(R.id.input2).setAdapter(adapter)

                holder.v<AutoCompleteTextView>(R.id.input4).apply {
                    setAdapter(adapter2)
                    onFocusChange {
                        if (it) {
                            showDropDown()
                        }
                    }
                }

                holder.v<AutoEditText>(R.id.input_auto).setDataList(dataList)

                val popupList = ListPopupWindow(mAttachContext)
                popupList.setAdapter(adapter3)
                holder.v<ExEditText>(R.id.input3).apply {
                    setEnableMention(true)
                    setOnKeyListener { v, keyCode, event ->
                        var result = false
                        L.i("keyDown:${keyCodeToString(keyCode)} ${actionToString(event.action)}")
//                        if (popupList.isShowing) {
//                            result = if (event.action == KeyEvent.ACTION_DOWN) {
//                                popupList.onKeyDown(keyCode, event)
//                            } else {
//                                popupList.onKeyUp(keyCode, event)
//                            }
//                        }
                        result
                    }

                    onTextChange {
                        val spannableText = text

                        //remove previous spans
                        val oldSpans =
                            spannableText!!.getSpans(0, spannableText.length, Any::class.java)

                        //ComposingText
                        L.i("${selectionStart}:${selectionEnd} 文本改变:$it  ${layout.javaClass.simpleName} ${oldSpans.size}")

                        holder.tv(R.id.text_view).text = span {
                            oldSpans.forEach {
                                append(it.javaClass.simpleName)
                                append(" ")
                                append("${spannableText.getSpanStart(it)}:${spannableText.getSpanEnd(it)}")
                                setForegroundColor(SkinHelper.getSkin().themeColor)
                                append(" ")
                                append(spannableText.getSpanFlags(it).toString())
                                appendln()
                            }
                        }

                        buildString {
                            appendln()
                        }
                    }

                    onFocusChange {
                        if (it) {
                            popupList.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                            popupList.promptPosition = android.widget.ListPopupWindow.POSITION_PROMPT_BELOW
                            popupList.setListSelector(ColorDrawable(Color.RED))
                            popupList.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                            popupList.setDropDownGravity(Gravity.BOTTOM)
                            //popupList.setOnItemClickListener(DropDownItemClickListener())

                            popupList.inputMethodMode = android.widget.ListPopupWindow.INPUT_METHOD_NEEDED

                            popupList.isModal = true
                            popupList.width = -2
                            popupList.height = -2

                            popupList.setOverlapAnchor(false)

                            popupList.anchorView = this
                            //popupList.postShow()

                            popupList.setOnItemClickListener { _, _, position, _ ->
                                setInputText(adapter3.filter.convertResultToString(adapter3.getItem(position)))
                                popupList.dismiss()
                            }

                            popupList.show()
                        } else {
                            popupList.dismiss()
                        }
                    }
                }

            }
        }
    }

    fun actionToString(action: Int): String {
        when (action) {
            ACTION_DOWN -> return "ACTION_DOWN"
            ACTION_UP -> return "ACTION_UP"
            ACTION_MULTIPLE -> return "ACTION_MULTIPLE"
            else -> return action.toString()
        }
    }
}