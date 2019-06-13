package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.kotlin.getColor
import com.angcyo.uiview.less.kotlin.nowTime
import com.angcyo.uiview.less.kotlin.renderItem
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.skin.SkinHelper
import com.angcyo.uiview.less.utils.RSpan
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

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        viewHolder.v<RSoftInputLayout>(R.id.base_soft_input_layout)
            .addOnEmojiLayoutChangeListener { isEmojiShow, isKeyboardShow, height ->
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
    }
}