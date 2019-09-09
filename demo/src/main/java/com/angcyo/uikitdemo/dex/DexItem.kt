package com.angcyo.uikitdemo.dex

import com.angcyo.uikitdemo.BuildConfig
import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.kotlin.hawkGet
import com.angcyo.uiview.less.kotlin.hawkGetList
import com.angcyo.uiview.less.kotlin.setInputText
import com.angcyo.uiview.less.kotlin.toast_tip
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/06
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class DexItem : DslAdapterItem() {
    init {
        itemLayoutId = R.layout.demo_class_loader
    }

    override fun onItemBind(
        itemHolder: RBaseViewHolder,
        itemPosition: Int,
        adapterItem: DslAdapterItem
    ) {
        super.onItemBind(itemHolder, itemPosition, adapterItem)

        itemHolder.eV(R.id.class_name_edit)
            .setInputText("class_name_edit".hawkGetList().first())
        itemHolder.eV(R.id.method_name_edit)
            .setInputText("method_name_edit".hawkGetList().first())
        itemHolder.eV(R.id.dex_path_edit)
            .setInputText("dex_path_edit".hawkGet())

        itemHolder.tv(R.id.result_text_view).text = buildString {
            append("BuildTime:")
            append(BuildConfig.BUILD_TIME)
            appendln()
            append("hashCode:")
            append(this@DexItem.javaClass.hashCode())
            appendln()
            append("加载类名:")
            append(this@DexItem.javaClass.name)
            appendln()
            append("来自:")
            append(this@DexItem.javaClass.classLoader?.javaClass?.name)
        }

        itemHolder.click(R.id.selector_file_button) {
            toast_tip("选择文件")
        }

        itemHolder.click(R.id.start_invoke_button) {
            toast_tip("调用方法测试")
        }

        itemHolder.click(R.id.start_fragment_button) {
            toast_tip("启动Fragment测试")
        }

        itemHolder.click(R.id.start_item_button) {
            toast_tip("追加Item测试")
        }
    }
}