package com.angcyo.uikitdemo

import android.Manifest
import android.os.Bundle
import com.angcyo.uikitdemo.ui.MainDslFragment
import com.angcyo.uiview.less.RCrashHandler
import com.angcyo.uiview.less.base.BaseAppCompatActivity
import com.angcyo.uiview.less.base.helper.FragmentHelper

class MainActivity : BaseAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //FragmentHelper.restoreShow(this, supportFragmentManager, R.id.fragment_layout, MainFragment::class.java)
        FragmentHelper.restoreOnlyShow(this, supportFragmentManager, R.id.fragment_layout, MainDslFragment::class.java)

        checkPermissions()
    }

    override fun checkCrash() {
        //super.checkCrash()
        RCrashHandler.checkCrash(this)
    }

    override fun needPermissions(): Array<String> {
        return arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WAKE_LOCK
        )
    }
}
