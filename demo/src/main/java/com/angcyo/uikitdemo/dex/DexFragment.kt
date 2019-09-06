package com.angcyo.uikitdemo.dex

import android.os.Bundle
import com.angcyo.uikitdemo.ui.demo.ClassLoaderDemo
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.dslitem.dslCustomItem

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/06
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class DexFragment : ClassLoaderDemo() {
    override fun getFragmentTitle(): CharSequence {
        return "Dex文件中的Fragment"
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        renderDslAdapter {
            dslCustomItem(DexItem())
        }
    }
}