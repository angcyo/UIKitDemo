package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.component.RDexClassLoader
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.base.helper.FragmentHelper
import com.angcyo.uiview.less.component.FileSelectorFragment
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem
import com.angcyo.uiview.less.recycler.dslitem.dslCustomItem
import com.angcyo.uiview.less.recycler.dslitem.dslItem

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/06
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
open class ClassLoaderDemo : AppBaseDslRecyclerFragment() {

    init {
        //默认值
        "class_name_edit".hawkPutList("com.angcyo.uikitdemo.dex.JavaDexClass")
        "class_name_edit".hawkPutList("com.angcyo.uikitdemo.dex.KotlinDexClass")
        "class_name_edit".hawkPutList("com.angcyo.uikitdemo.dex.DexItem")
        "class_name_edit".hawkPutList("com.angcyo.uikitdemo.dex.DexFragment")

        "method_name_edit".hawkPutList("javaDex")
        "method_name_edit".hawkPutList("kotlinDex2")
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        renderDslAdapter {
            dslItem(R.layout.demo_class_loader) {
                itemBind = { itemHolder, _, _ ->

                    itemHolder.auto(R.id.class_name_edit, "class_name_edit".hawkGetList(), true)
                    itemHolder.auto(R.id.method_name_edit, "method_name_edit".hawkGetList(), true)

                    itemHolder.eV(R.id.class_name_edit)
                        .setInputText("class_name_edit".hawkGetList().first())
                    itemHolder.eV(R.id.method_name_edit)
                        .setInputText("method_name_edit".hawkGetList().first())
                    itemHolder.eV(R.id.dex_path_edit)
                        .setInputText("dex_path_edit".hawkGet())

                    //选择Dex
                    itemHolder.click(R.id.selector_file_button) {
                        FragmentHelper.build(parentFragmentManager())
                            .showFragment(FileSelectorFragment().fileSelectorConfig {
                                showFileMenu = true
                                showFileMd5 = true
                                onFileSelector = {
                                    itemHolder.eV(R.id.dex_path_edit).setInputText(it.absolutePath)
                                    "dex_path_edit".hawkPut(it.absolutePath)
                                }
                            })
                            .defaultEnterAnim()
                            .doIt()
                    }

                    //调用方法
                    itemHolder.click(R.id.start_invoke_button) {
                        if (itemHolder.checkEmpty(
                                R.id.dex_path_edit,
                                R.id.class_name_edit,
                                R.id.method_name_edit
                            )
                        ) {
                            return@click
                        }

                        "class_name_edit".hawkPutList(itemHolder.eV(R.id.class_name_edit).string())
                        "method_name_edit".hawkPutList(itemHolder.eV(R.id.method_name_edit).string())

                        val loader = RDexClassLoader.create(
                            mAttachContext,
                            itemHolder.eV(R.id.dex_path_edit).string()
                        )

                        loader.invokeWith(itemHolder.eV(R.id.class_name_edit).string()) {
                            L.i("方法返回:${loader.invokeMethod(
                                this, itemHolder.eV(R.id.method_name_edit).string(),
                                arrayOf(Int::class.java, String::class.java), 100, "angcyo"
                            )}".apply {
                                itemHolder.tv(R.id.result_text_view).text = this
                            })
                        }
                    }

                    //启动Fragment
                    itemHolder.click(R.id.start_fragment_button) {
                        if (itemHolder.checkEmpty(
                                R.id.dex_path_edit,
                                R.id.class_name_edit
                            )
                        ) {
                            return@click
                        }

                        "class_name_edit".hawkPutList(itemHolder.eV(R.id.class_name_edit).string())

                        val loader = RDexClassLoader.create(
                            mAttachContext,
                            itemHolder.eV(R.id.dex_path_edit).string()
                        )

                        loader.invokeWith(itemHolder.eV(R.id.class_name_edit).string()) {
                            (this as? Fragment)?.run { show(this) }
                        }
                    }

                    //显示DslAdapterItem
                    itemHolder.click(R.id.start_item_button) {
                        if (itemHolder.checkEmpty(
                                R.id.dex_path_edit,
                                R.id.class_name_edit
                            )
                        ) {
                            return@click
                        }

                        "class_name_edit".hawkPutList(itemHolder.eV(R.id.class_name_edit).string())

                        val loader = RDexClassLoader.create(
                            mAttachContext,
                            itemHolder.eV(R.id.dex_path_edit).string()
                        )

                        loader.invokeWith(itemHolder.eV(R.id.class_name_edit).string()) {
                            (this as? DslAdapterItem)?.run { dslCustomItem(this) }

                        }
                    }
                }
            }
        }
    }
}