package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.kotlin.renderItem
import com.angcyo.uiview.less.recycler.RBaseViewHolder

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/07/12
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class CustomViewDemo2 : AppBaseDslRecyclerFragment() {
    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        renderDslAdapter {
            renderItem {
                itemLayoutId = R.layout.custom_view1_layout2
            }
        }
    }
}