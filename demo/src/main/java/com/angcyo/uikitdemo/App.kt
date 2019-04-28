package com.angcyo.uikitdemo

import android.os.Bundle
import com.angcyo.lib.L
import com.angcyo.rtbs.RTbs
import com.angcyo.uiview.less.base.BaseTitleFragment
import com.angcyo.uiview.less.base.BaseUI
import com.angcyo.uiview.less.kotlin.getColor

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/02/20
 */
class App : PluginHostApplication() {

    companion object {
        fun isApk() = BuildConfig.FLAVOR == "apk"
    }

    override fun onBaseInit() {
        super.onBaseInit()

        L.init(!isApk(), "angcyo_" + hashCode())

        BaseUI.uiFragment = object : BaseUI.DefaultUIFragment() {
            override fun initBaseTitleLayout(titleFragment: BaseTitleFragment, arguments: Bundle?) {
                super.initBaseTitleLayout(titleFragment, arguments)
                titleFragment.contentControl().selector().setBackgroundColor(getColor(R.color.line_color))
            }
        }

        RTbs.init(this, BuildConfig.SHOW_DEBUG)
    }
}