package com.angcyo.uikitdemo.ui

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.base.BaseFragment
import com.angcyo.uiview.less.base.helper.FragmentHelper
import com.angcyo.uiview.less.kotlin.dslAdapter
import com.angcyo.uiview.less.kotlin.renderItem
import com.angcyo.uiview.less.recycler.RBaseViewHolder

/**
 * 权限申请界面
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/05/29
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class PermissionFragment : BaseFragment() {

    companion object {
        fun show(fragmentManager: FragmentManager, layoutId: Int, config: PermissionConfig.() -> Unit) {
            FragmentHelper.build(fragmentManager)
                .parentLayoutId(layoutId)
                .showFragment(PermissionFragment().apply {
                    permissionConfig.config()
                })
                .noAnim()
                .doIt()
        }
    }

    var permissionConfig = PermissionConfig()

    override fun getLayoutId(): Int {
        return R.layout.fragment_permission_layout
    }

    override fun initBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.initBaseView(viewHolder, arguments, savedInstanceState)
        viewHolder.tv(R.id.permission_title).text = permissionConfig.permissionTitle

        viewHolder.rv(R.id.recycler_view).dslAdapter {
            permissionConfig.permissions.forEachIndexed { index, permission ->
                renderItem {
                    itemLayoutId = R.layout.item_permission_layout

                    itemBind = { itemHolder, itemPosition, _ ->
                        itemHolder.imgV(R.id.image_view).setImageResource(permissionConfig.permissionIco[itemPosition])
                        itemHolder.tv(R.id.text_view).text = permissionConfig.permissionDes[itemPosition]
                    }
                }
            }
        }

        viewHolder.click(R.id.enable_button) {
            permissionConfig.onEnablePermissionRequest(this)
        }
    }

    override fun canSwipeBack(): Boolean {
        return false
    }

    override fun onBackPressed(activity: Activity): Boolean {
        return false
    }
}

class PermissionConfig {
    var permissions = arrayOf<String>()
    var permissionDes = arrayOf<String>()
    var permissionIco = arrayOf<Int>()
    var permissionTitle = "为了更好的服务体验, 程序需要以下权限"

    var onEnablePermissionRequest: (fragment: PermissionFragment) -> Unit = {}
}