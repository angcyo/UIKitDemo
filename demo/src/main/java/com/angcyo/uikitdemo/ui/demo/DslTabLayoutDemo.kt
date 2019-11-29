package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.airbnb.lottie.LottieAnimationView
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.demo.sub.CommonFragment
import com.angcyo.uikitdemo.ui.demo.sub.SegmentFragment
import com.angcyo.uikitdemo.ui.demo.sub.SlidingFragment
import com.angcyo.uiview.less.base.BaseTitleFragment
import com.angcyo.uiview.less.dsl.tablayout.DslTabLayout
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.widget.pager.RFragmentAdapter

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/11/29
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class DslTabLayoutDemo : BaseTitleFragment() {
    override fun getContentLayoutId(): Int {
        return R.layout.fragment_dsl_tab_layout
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        viewHolder.v<DslTabLayout>(R.id.tab_layout).apply {
            configTabLayoutConfig {
                onSelectViewChange = { _, selectList, reselect ->
                    if (!reselect) {
                        selectList.lastOrNull()?.findViewById<LottieAnimationView>(R.id.lottie_view)
                            ?.apply {
                                //                                removeAllUpdateListeners()
//                                addAnimatorUpdateListener {
//                                    //如果使用 高凸模式...需要使用顶层view的[invalidate]才能更新`高凸`部分的视图, 否则不需要
//                                    //这和`高凸`模式的实现机制有关
//                                    (viewHolder.v<DslTabLayout>(R.id.tab_layout).parent as View).invalidate()
//                                }

                                //换了一种 高凸模式的实现方式.
                                playAnimation()
                            }
                    }
                }
            }

            setupViewPager(viewHolder.v(R.id.view_pager))
        }

        viewHolder.v<ViewPager>(R.id.view_pager).adapter = RFragmentAdapter(
            childFragmentManager,
            listOf(SlidingFragment(), CommonFragment(), SegmentFragment())
        )
    }
}