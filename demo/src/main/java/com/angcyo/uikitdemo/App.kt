package com.angcyo.uikitdemo

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.angcyo.lib.L
import com.angcyo.opencv.CardOcr
import com.angcyo.rtbs.RTbs
import com.angcyo.uiview.less.base.BaseTitleFragment
import com.angcyo.uiview.less.base.BaseUI
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.kotlin.getColor
import com.angcyo.uiview.less.kotlin.random
import com.angcyo.uiview.less.utils.RNetwork
import com.angcyo.uiview.less.utils.RUtils.randomColor

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

        RNetwork.init(this)

        CardOcr.init(this)
    }
}

/**填充色块*/
fun ViewGroup.appendColorItem(size: Int = 100) {
    for (i in 0 until size) {
        addView(View(context).apply {
            setBackgroundColor(randomColor(random))
        }, -1, 100 * dpi)
    }
}