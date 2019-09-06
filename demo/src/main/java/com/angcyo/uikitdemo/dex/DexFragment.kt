package com.angcyo.uikitdemo.dex

import android.os.Bundle
import android.view.ViewGroup
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.demo.ClassLoaderDemo
import com.angcyo.uiview.less.kotlin.getData
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

    override fun onPostCreateView(
        container: ViewGroup?,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onPostCreateView(container, arguments, savedInstanceState)

        baseViewHolder.tv(R.id.result_text_view)?.text = "数据接收:${getData(String::class.java)}"
    }
}