package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.component.RNfc
import com.angcyo.uikitdemo.component.toLog
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.R
import com.angcyo.uiview.less.base.helper.FragmentHelper
import com.angcyo.uiview.less.kotlin.copy
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.dslitem.dslTextInfoItem

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/26
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class NfcDemo : AppBaseDslRecyclerFragment() {

    override fun needBackItem(): Boolean {
        return true
    }

    override fun configBackBuilder(builder: FragmentHelper.Builder) {
        builder.setFinishActivity(true)
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        renderDslAdapter {
            dslTextInfoItem {
                itemTag = "first"

                onItemBindOverride = { itemHolder, _, _ ->
                    //文本信息
                    itemHolder.tv(R.id.text_view)?.apply {
                        text = buildString {
                            append("support:")
                            append(RNfc.isNfcSupport())
                            append(" ")

                            append("enable:")
                            append(RNfc.isNfcEnable())
                            append(" ")
                        }
                    }
                }
            }

            dslTextInfoItem {
                itemTopInsert = 1 * dpi
                itemTag = "tag"

                onItemBindOverride = { itemHolder, _, _ ->
                    //文本信息
                    itemHolder.tv(R.id.text_view)?.apply {
                        text = RNfc.getNfcTag(mAttachContext, this@NfcDemo.arguments).toLog()
                        itemInfoText = text
                    }
                }

                itemClickListener = {
                    itemInfoText?.copy()
                }
            }

            dslTextInfoItem {
                itemTopInsert = 1 * dpi
                itemInfoText = "打开设置"
                itemClickListener = {
                    RNfc.startNfcSetting(requireActivity())
                }
            }
        }
    }

    override fun onFragmentNotFirstShow(bundle: Bundle?) {
        super.onFragmentNotFirstShow(bundle)
        notifyItemChangedByTag("first")
        notifyItemChangedByTag("tag")
    }
}