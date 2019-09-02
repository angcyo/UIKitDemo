package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.angcyo.http.HttpSubscriber
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.RHost
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.base.helper.ActivityHelper
import com.angcyo.uiview.less.base.helper.FragmentHelper
import com.angcyo.uiview.less.component.FileSelectorFragment
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.picture.transition.transitionFromRect
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.angcyo.uiview.less.utils.RUtils
import com.angcyo.uiview.less.utils.Tip
import com.qihoo360.replugin.RePlugin
import com.qihoo360.replugin.model.PluginInfo
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/04/28
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class RePluginDemo : AppBaseItemFragment() {

    var showFromRect = RUtils.screenCenterRect((100 * dp).toInt(), (200 * dp).toInt())

    init {
        //默认值
        "plugin_name_edit".hawkPutList("com.wayto.smart.community.plugin")
        "plugin_name_edit".hawkPutList("com.wayto.reservoir.patrol.plugin")
        "plugin_name_edit".hawkPutList("com.angcyo.plugin1")
        "plugin_name_edit".hawkPutList("com.angcyo.plugin2")

        "start_activity_edit".hawkPutList("com.wayto.smart.community.plugin.MainActivity")
        "start_activity_edit".hawkPutList("com.wayto.reservoir.patrol.plugin.SplashActivity")
        "start_activity_edit".hawkPutList("com.angcyo.plugin1.MainActivity")
        "start_activity_edit".hawkPutList("com.angcyo.plugin2.MainActivity")

        "start_activity_class".hawkPutList("com.angcyo.uikitdemo.loader.a.ActivityN1NRNTS2")
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (enter) {
            return AlphaAnimation(1f, 1f)
        }
        return null
    }

    override fun getContentLayoutId(): Int {
        return R.layout.base_input_recycler_fragment_layout
    }

    fun updateAdapter(holder: RBaseViewHolder) {
        holder.auto(R.id.plugin_name_edit, "plugin_name_edit".hawkGetList(), true)
        holder.auto(R.id.start_activity_edit, "start_activity_edit".hawkGetList(), true)
        holder.auto(R.id.start_activity_class, "start_activity_class".hawkGetList(), true)
    }

    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        singleItems.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {

                holder.eV(R.id.plugin_name_edit).setInputText("com.wayto.smart.community.plugin")
                holder.eV(R.id.start_activity_edit)
                    .setInputText("com.wayto.smart.community.plugin.MainActivity")

                updateAdapter(holder)

                //预设按钮
                holder.click(R.id.test1) {
                    holder.eV(R.id.plugin_name_edit)
                        .setInputText("com.wayto.smart.community.plugin")
                    holder.eV(R.id.start_activity_edit)
                        .setInputText("com.wayto.smart.community.plugin.Test1Activity")
                }
                holder.click(R.id.test2) {
                    holder.eV(R.id.plugin_name_edit)
                        .setInputText("com.wayto.smart.community.plugin")
                    holder.eV(R.id.start_activity_edit)
                        .setInputText("com.wayto.smart.community.plugin.Test2Activity")
                }
                holder.click(R.id.test3) {
                    holder.eV(R.id.plugin_name_edit).setInputText("com.angcyo.plugin1")
                    holder.eV(R.id.start_activity_edit)
                        .setInputText("com.angcyo.plugin1.MainActivity")
                }
                holder.click(R.id.test4) {
                    holder.eV(R.id.plugin_name_edit).setInputText("com.wayto.plugin1")
                    holder.eV(R.id.start_activity_edit)
                        .setInputText("com.wayto.plugin1.MainActivity")
                }

                //选择插件
                holder.click(R.id.selector_file_button) {
                    FragmentHelper.build(parentFragmentManager())
                        .showFragment(FileSelectorFragment().fileSelectorConfig {
                            showFileMenu = true
                            showFileMd5 = true
                            onFileSelector = {
                                holder.eV(R.id.plugin_path_edit).setInputText(it.absolutePath)
                            }
                        })
                        .defaultEnterAnim()
                        .doIt()
                }

                //启动插件
                holder.click(R.id.start_plugin_button) {
                    if (holder.eV(R.id.plugin_name_edit).checkEmpty() ||
                        holder.eV(R.id.start_activity_edit).checkEmpty()
                    ) {
                        return@click
                    }

                    //插件没有安装过
                    if (!RHost.isPluginInstalled(holder.eV(R.id.plugin_name_edit).string())) {
                        if (holder.eV(R.id.plugin_path_edit).checkEmpty()) {
                            return@click
                        }
                    }

                    "plugin_name_edit".hawkPutList(holder.eV(R.id.plugin_name_edit).string().trim())
                    "start_activity_edit".hawkPutList(holder.eV(R.id.start_activity_edit).string().trim())

                    updateAdapter(holder)

                    RHost.startPlugin(
                        mAttachContext,
                        holder.eV(R.id.plugin_path_edit).string(),
                        holder.eV(R.id.plugin_name_edit).string(),
                        holder.eV(R.id.start_activity_edit).string()
                    ).subscribe(object : HttpSubscriber<PluginInfo>() {
                        override fun onEnd(data: PluginInfo?, error: Throwable?) {
                            super.onEnd(data, error)
                            error?.let {
                                toast_tip(it.message ?: "--")
                            }
                        }
                    })
                }

                //卸载插件
                holder.click(R.id.uninstall_plugin_button) {
                    if (holder.eV(R.id.plugin_name_edit).checkEmpty()) return@click
                    RHost.uninstall(holder.eV(R.id.plugin_name_edit).string().trim())
                    Tip.tip("Ok!")
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.demo_re_plugin
            }
        })

        singleItems.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {

                holder.eV(R.id.start_activity_class)
                    .setInputText("com.angcyo.uikitdemo.loader.a.ActivityN1NRNTS2")

                updateAdapter(holder)

                //启动宿主Activity
                holder.click(R.id.start_activity_button) {
                    if (holder.eV(R.id.start_activity_class).checkEmpty()) return@click

                    "start_activity_class".hawkPutList(holder.eV(R.id.start_activity_class).string().trim())

                    try {

                        val className = holder.eV(R.id.start_activity_class).string().trim()

                        //val forName = Class.forName(className)

                        val componentName = RePlugin.createComponentName(
                            "com.angcyo.uikitdemo",
                            className
                        )

                        ActivityHelper.build(mAttachContext)
                            //.setClass(forName as Class<out Activity>)
                            .setComponent(componentName)
                            .defaultEnterAnim()
                            .doIt()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.demo_re_plugin2
            }
        })
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        transitionFromRect(showFromRect, viewHolder.itemView as ViewGroup)
    }

}