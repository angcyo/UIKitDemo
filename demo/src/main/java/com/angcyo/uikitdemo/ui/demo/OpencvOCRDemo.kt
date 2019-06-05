package com.angcyo.uikitdemo.ui.demo

import com.angcyo.opencv.CardOcrScanFragment
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.kotlin.toBitmap
import com.angcyo.uiview.less.recycler.item.SingleItem
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/05
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class OpencvOCRDemo : AppBaseItemFragment() {
    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        dslCreateItem {
            singleItemLayoutId = R.layout.demo_open_cv

            singleItemBind = { holder, posInData, singItem ->
                holder.click(R.id.ocr_button) {
                    CardOcrScanFragment.show(parentFragmentManager()) {
                        holder.tv(R.id.text_view).text = "$it"

                        holder.imgV(R.id.image_view).setImageBitmap(it.base64bitmap.toBitmap())
                    }
                }
            }
        }
    }

}