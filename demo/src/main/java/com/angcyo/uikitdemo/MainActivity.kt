package com.angcyo.uikitdemo

import android.os.Bundle
import com.angcyo.uikitdemo.ui.MainFragment
import com.angcyo.uiview.less.base.BaseAppCompatActivity
import com.angcyo.uiview.less.base.helper.FragmentHelper

class MainActivity : BaseAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FragmentHelper.restoreShow(this, supportFragmentManager, R.id.fragment_layout, MainFragment::class.java)
    }
}
