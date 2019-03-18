package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseTitleFragment
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.utils.Tip

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/03/13
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class FozuLayoutDemo : AppBaseTitleFragment() {
    override fun getContentLayoutId(): Int {
        return R.layout.fragment_fozu_layout
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        viewHolder.click(R.id.view1) {
            Tip.tip("view1")
        }
        viewHolder.click(R.id.view2) {
            Tip.tip("view2")
        }
        viewHolder.click(R.id.view3) {
            Tip.tip("view3")
        }
        viewHolder.click(R.id.view4) {
            Tip.tip("view4")
        }
        viewHolder.click(R.id.view5) {
            Tip.tip("view5")
        }
    }
}