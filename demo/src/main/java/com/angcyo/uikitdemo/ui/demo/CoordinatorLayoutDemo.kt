package com.angcyo.uikitdemo.ui.demo

import android.graphics.Color
import android.os.Bundle
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.来点数据
import com.angcyo.uiview.less.kotlin.coordinatorParams
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.widget.behavior.FollowOffsetBehavior

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/28
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class CoordinatorLayoutDemo : AppBaseDslRecyclerFragment() {

    companion object {
        var count = 0
    }

    private var innerLayoutId = -1
    private val demoCount = 4

    override fun getLayoutId(): Int {
        return R.layout.fragment_behavior_layout
    }

    override fun getTitleBarLayoutId(): Int {
        return if (count % demoCount == 0) {
            R.layout.demo_coordinator_title_layout
        } else {
            super.getTitleBarLayoutId()
        }
    }

    override fun getContentLayoutId(): Int {
        return when {
            count % demoCount == 0 -> super.getContentLayoutId()
            count % demoCount == 1 -> R.layout.demo_coordinator_layout3
            count % demoCount == 2 -> R.layout.demo_coordinator_layout2
            count % demoCount == 3 -> R.layout.demo_coordinator_layout
            else -> super.getContentLayoutId()
        }.apply {
            innerLayoutId = this
            count++

            setTitleString("${this@CoordinatorLayoutDemo.javaClass.simpleName} $count")
        }
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        if (innerLayoutId == super.getContentLayoutId()) {
            titleControl().selector().setBackgroundColor(Color.TRANSPARENT)

            viewHolder.view(R.id.base_title_bar_layout).coordinatorParams {
                behavior = FollowOffsetBehavior(mAttachContext).apply {
                    dependsLayout = viewHolder.view(R.id.base_content_wrapper_layout)
                }
            }
        }

        if (innerLayoutId == R.layout.demo_coordinator_layout3) {
            viewHolder.view(R.id.content_layout).coordinatorParams {
                L.i(behavior)
            }
        }

        来点数据(demoCount, demoCount)
    }
}