package com.angcyo.uikitdemo.ui.demo

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.kotlin.getColor
import com.angcyo.uiview.less.kotlin.nowTime
import com.angcyo.uiview.less.kotlin.renderItem
import com.angcyo.uiview.less.kotlin.showSoftInput
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.skin.SkinHelper
import com.angcyo.uiview.less.utils.RSpan
import com.angcyo.uiview.less.widget.Button
import com.angcyo.uiview.less.widget.group.RSoftInputLayout

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/13
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class SoftInputDemo : AppBaseDslRecyclerFragment() {

    override fun getContentLayoutId(): Int {
        return R.layout.demo_soft_input_layout
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        floatTitleBar(true, false)

        viewHolder.v<RSoftInputLayout>(R.id.base_soft_input_layout).apply {
            addOnEmojiLayoutChangeListener { isEmojiShow, isKeyboardShow, height ->
                if (isKeyboardShow) {
                    viewHolder.view(R.id.emoji_button).isSelected = false
                }

                baseDslAdapter.renderItem {
                    itemLayoutId = R.layout.base_single_text_layout
                    itemData = nowTime()
                    itemBind = { itemHolder, itemPosition, _ ->
                        itemHolder.tv(R.id.base_text_view).text = RSpan.get("$itemData->\n")
                            .append("isEmojiShow:").append(isEmojiShow.toString())
                            .append(" isKeyboardShow:").append(isKeyboardShow.toString())
                            .append(" height:").append(height.toString())
                            .create()

                        itemHolder.tv(R.id.base_text_view).setTextColor(
                            if (baseDslAdapter.isLast(itemPosition)) SkinHelper.getSkin().themeColorAccent else getColor(
                                R.color.base_text_color
                            )
                        )
                    }
                }

                recyclerView.scrollToLastBottom(true)
                baseDslAdapter.updateAllItem()
            }

            viewHolder.click(R.id.emoji_button) {
                if (it.isSelected) {
                    viewHolder.view(R.id.edit_text).showSoftInput()
                } else {
                    showEmojiLayout()
                }

                it.isSelected = !it.isSelected
            }

            viewHolder.click(R.id.image_view_left) {
                showEmojiLayout()
            }

            viewHolder.click(R.id.image_view_right) {
                //viewHolder.itemView.setHeight(500 * dpi)
                hideEmojiLayout()
            }
        }
    }

    override fun hideSoftInputOnTouchDown(touchDownView: View?): Boolean {
        return touchDownView !is EditText && touchDownView !is Button && touchDownView !is ImageView
    }

    override fun onBackPressed(activity: Activity): Boolean {
        return baseViewHolder.v<RSoftInputLayout>(R.id.base_soft_input_layout).requestBackPressed()
    }
}