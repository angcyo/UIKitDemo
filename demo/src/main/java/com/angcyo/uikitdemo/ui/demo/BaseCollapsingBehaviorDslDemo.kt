package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.来点数据
import com.angcyo.uiview.less.base.BaseBehaviorDslRecyclerFragment
import com.angcyo.uiview.less.recycler.RBaseViewHolder

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019-10-12
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class BaseCollapsingBehaviorDslDemo : BaseBehaviorDslRecyclerFragment() {

    companion object {
        var index = 0
    }

    init {
        isCollapseTitle = true
    }

    override fun isFirstNeedLoadData(): Boolean {
        return false
    }

    override fun getFragmentTitle(): CharSequence {
        return ""
    }

    override fun getCollapseTitleBarBackgroundLayoutId(): Int {
        return if (index++ % 2 == 0) {
            R.layout.collapsing_title_background_layout
        } else {
            super.getCollapseTitleBarBackgroundLayoutId()
        }
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        enableRefreshAffect()

        来点数据(2, 5)
    }

    override fun onFragmentFirstShow(bundle: Bundle?) {
        super.onFragmentFirstShow(bundle)

        baseViewHolder.postDelay(1000) {
            setTitleString("测试标题")
        }
        baseViewHolder.postDelay(2000) {
            setTitleString("测试标题1")
            来点数据(2, 5)
        }
    }
}