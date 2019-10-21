package com.angcyo.uikitdemo

import android.content.Intent
import com.angcyo.uiview.less.base.activity.BasePermissionActivity
import com.angcyo.uiview.less.base.helper.ActivityHelper

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/10/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class HandleNfcTech : BasePermissionActivity() {
    override fun toMain() {
        ActivityHelper.build(this)
            .setClass(NfcTechInfoActivity::class.java)
            .setBundle(null, intent.extras)
            .configIntent {
                it.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            .finishSelf()
            .start()
    }
}