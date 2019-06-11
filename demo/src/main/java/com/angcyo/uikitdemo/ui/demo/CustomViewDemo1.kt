package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.ui.widget.DYProgressBar
import com.angcyo.uikitdemo.ui.widget.HSProgressView
import com.angcyo.uiview.less.kotlin.renderItem
import com.angcyo.uiview.less.recycler.RBaseViewHolder

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/11
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class CustomViewDemo1 : AppBaseDslRecyclerFragment() {
    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        baseDslAdapter.apply {
            renderItem {
                itemLayoutId = R.layout.custom_view1_layout1

                itemBind = { itemHolder, _, _ ->
                    itemHolder.v<DYProgressBar>(R.id.dy_progress_bar).startAnimator()
                    itemHolder.v<HSProgressView>(R.id.hs_progress_view1).baseDraw.setProgress(50)
                    itemHolder.v<HSProgressView>(R.id.hs_progress_view2).startAnimator()
                }
            }
        }
    }
}