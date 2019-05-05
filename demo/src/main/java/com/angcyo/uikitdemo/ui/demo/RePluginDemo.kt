package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.angcyo.http.HttpSubscriber
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.RHost
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.base.helper.FragmentHelper
import com.angcyo.uiview.less.component.FileSelectorFragment
import com.angcyo.uiview.less.kotlin.dp
import com.angcyo.uiview.less.picture.transition.transitionFromRect
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.angcyo.uiview.less.utils.RUtils
import com.angcyo.uiview.less.utils.Tip
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

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (enter) {
            return AlphaAnimation(1f, 1f)
        }
        return null
    }

    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        singleItems.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {

                holder.exV(R.id.plugin_name_edit).setInputText("com.wayto.smart.community.plugin")
                holder.exV(R.id.start_activity_edit).setInputText("com.wayto.smart.community.plugin.MainActivity")

                //预设按钮
                holder.click(R.id.test1) {
                    holder.exV(R.id.plugin_name_edit).setInputText("com.wayto.smart.community.plugin")
                    holder.exV(R.id.start_activity_edit).setInputText("com.wayto.smart.community.plugin.Test1Activity")
                }
                holder.click(R.id.test2) {
                    holder.exV(R.id.plugin_name_edit).setInputText("com.wayto.smart.community.plugin")
                    holder.exV(R.id.start_activity_edit).setInputText("com.wayto.smart.community.plugin.Test2Activity")
                }
                holder.click(R.id.test3) {
                    holder.exV(R.id.plugin_name_edit).setInputText("com.angcyo.plugin1")
                    holder.exV(R.id.start_activity_edit).setInputText("com.angcyo.plugin1.MainActivity")
                }
                holder.click(R.id.test4) {
                    holder.exV(R.id.plugin_name_edit).setInputText("com.wayto.plugin1")
                    holder.exV(R.id.start_activity_edit).setInputText("com.wayto.plugin1.MainActivity")
                }

                //选择插件
                holder.click(R.id.selector_file_button) {
                    FragmentHelper.build(parentFragmentManager())
                        .showFragment(FileSelectorFragment().fileSelectorConfig {
                            onFileSelector = {
                                holder.exV(R.id.plugin_path_edit).setInputText(it.absolutePath)
                            }
                        })
                        .defaultEnterAnim()
                        .doIt()
                }

                //启动插件
                holder.click(R.id.start_plugin_button) {
                    if (holder.exV(R.id.plugin_name_edit).checkEmpty() ||
                        holder.exV(R.id.start_activity_edit).checkEmpty()
                    ) {
                        return@click
                    }

                    //插件没有安装过
                    if (!RHost.isPluginInstalled(holder.exV(R.id.plugin_name_edit).string())) {
                        if (holder.exV(R.id.plugin_path_edit).checkEmpty()) {
                            return@click
                        }
                    }

                    RHost.startPlugin(
                        mAttachContext,
                        holder.exV(R.id.plugin_path_edit).string(),
                        holder.exV(R.id.plugin_name_edit).string(),
                        holder.exV(R.id.start_activity_edit).string()
                    ).subscribe(object : HttpSubscriber<PluginInfo>() {
                    })
                }

                //卸载插件
                holder.click(R.id.uninstall_plugin_button) {
                    if (holder.exV(R.id.plugin_name_edit).checkEmpty()) return@click
                    RHost.uninstall(holder.exV(R.id.plugin_name_edit).string().trim())
                    Tip.tip("Ok!")
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.demo_re_plugin
            }

        })
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        transitionFromRect(showFromRect, viewHolder.itemView as ViewGroup)
    }

}