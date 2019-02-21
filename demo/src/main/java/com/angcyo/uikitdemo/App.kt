package com.angcyo.uikitdemo

import android.os.Bundle
import com.angcyo.uiview.less.RApplication
import com.angcyo.uiview.less.base.BaseTitleFragment
import com.angcyo.uiview.less.base.BaseUI
import com.angcyo.uiview.less.kotlin.getColor

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/02/20
 */
class App : RApplication() {
    override fun onBaseInit() {
        super.onBaseInit()

        BaseUI.uiFragment = object : BaseUI.DefaultUIFragment() {
            override fun initBaseTitleLayout(titleFragment: BaseTitleFragment, arguments: Bundle?) {
                super.initBaseTitleLayout(titleFragment, arguments)
                titleFragment.contentControl().selector().setBackgroundColor(getColor(R.color.line_color))
            }
        }
    }
}