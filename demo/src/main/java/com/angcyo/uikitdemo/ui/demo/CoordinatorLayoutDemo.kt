package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.kotlin.renderItem
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.utils.TopToast

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/28
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class CoordinatorLayoutDemo : AppBaseDslRecyclerFragment() {
    override fun getContentLayoutId(): Int {
        return R.layout.demo_coordinator_layout
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        renderDslAdapter {
            for (i in 0..5) {
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
                    }
                }

                for (j in 0..5) {
                    renderItem {
                        itemLayoutId = R.layout.item_image_little
                    }
                }
            }
        }
    }
}