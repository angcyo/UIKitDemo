package com.angcyo.uikitdemo

import android.content.Intent
import com.angcyo.uikitdemo.component.RNfc
import com.angcyo.uikitdemo.component.toLog
import com.angcyo.uikitdemo.ui.demo.NfcDemo
import com.angcyo.uiview.less.base.activity.BasePermissionActivity
import com.angcyo.uiview.less.base.helper.FragmentHelper

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/26
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class NfcTechInfoActivity : BasePermissionActivity() {
    override fun toMain() {
        FragmentHelper.restoreOnlyShow(
            this,
            supportFragmentManager,
            R.id.frame_layout,
            NfcDemo::class.java
        )?.first()?.arguments = intent.extras
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val nfcTag = RNfc.getNfcTag(this, intent)
        nfcTag.toLog()

        FragmentHelper.find(supportFragmentManager, NfcDemo::class.java)?.first()?.apply {
            arguments = intent?.extras
            (this as NfcDemo).onFragmentReShow()
        }
    }

    override fun onResume() {
        super.onResume()
        RNfc.enableNfcForegroundDispatch(this)
    }

    override fun onPause() {
        super.onPause()
        RNfc.disableNfcForegroundDispatch(this)
    }
}