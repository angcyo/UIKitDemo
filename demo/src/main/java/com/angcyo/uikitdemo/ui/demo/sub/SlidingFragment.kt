package com.angcyo.uikitdemo.ui.demo.sub

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.item.DslSlidingTabLayoutItem
import com.angcyo.uikitdemo.ui.item.DslTabViewPagerItem
import com.angcyo.uiview.less.dsl.tablayout.DslTabIndicator
import com.angcyo.uiview.less.dsl.tablayout.DslTabLayout
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.kotlin.getDrawable
import com.angcyo.uiview.less.recycler.RBaseViewHolder

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/11/23
 */
class SlidingFragment : BaseTabLayoutFragment() {
    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        renderDslAdapter {

            //指示器在顶部
            DslSlidingTabLayoutItem()() {
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorWidth = 4 * dpi
                        tabIndicator.indicatorEnableFlow = true
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_TOP
                    }
                }
            }

            DslSlidingTabLayoutItem()() {
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorWidth = -2
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_TOP
                    }
                }
            }

            DslSlidingTabLayoutItem()() {
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorWidth = -1
                        tabIndicator.indicatorHeight = 4 * dpi
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_TOP
                    }
                }
            }

            //指示器在底部
            DslSlidingTabLayoutItem()() {
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorWidth = 10 * dpi
                        tabIndicator.indicatorHeight = 10 * dpi
                        tabIndicator.indicatorDrawable =
                            getDrawable(R.drawable.ic_triangle)
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_BOTTOM
                    }
                }
            }

            DslSlidingTabLayoutItem()() {
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorWidth = -2
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_BOTTOM
                    }
                }
            }

//            DslSlidingTabLayoutItem()() {
//                onItemBindOverride = { itemHolder, _, _ ->
//                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
//                        addTabLayout(this)
//                        tabIndicator.indicatorWidth = 10 * dpi
//                        tabIndicator.indicatorEnableFlow = true
//                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_BOTTOM
//                    }
//                }
//            }

            DslSlidingTabLayoutItem()() {
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorWidth = -1
                        tabIndicator.indicatorHeight = 4 * dpi
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_BOTTOM
                    }
                }
            }

            //指示器在背部
            DslSlidingTabLayoutItem()() {
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorDrawable =
                            getDrawable(R.drawable.indicator_round_background_tran)
                        tabIndicator.indicatorHeight = -2
                        tabIndicator.indicatorWidth = -2
                        tabIndicator.indicatorWidthOffset = 20 * dpi
                        tabIndicator.indicatorHeightOffset = -15 * dpi
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_BACKGROUND
                    }
                }
            }
            DslSlidingTabLayoutItem()() {
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorDrawable =
                            getDrawable(R.drawable.ic_love)
                        tabIndicator.indicatorHeight = -1
                        tabIndicator.indicatorWidth = 40 * dpi
                        tabIndicator.indicatorHeight = 40 * dpi
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_BACKGROUND
                    }
                }
            }

            //其他特性
            DslSlidingTabLayoutItem()() {
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorWidth = 30 * dpi
                        tabIndicator.indicatorHeight = 6 * dpi
                        tabIndicator.indicatorYOffset = 20 * dpi
                        tabIndicator.indicatorEnableFlow = true
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_BOTTOM

                        setTabLayoutConfig {
                            tabEnableGradientScale = true
                            tabEnableGradientColor = true
                            tabSelectColor = resources.getColor(R.color.colorAccent)
                            tabDeselectColor = resources.getColor(R.color.colorPrimaryDark)
                        }
                    }
                }
            }
//            DslSlidingTabLayoutItem()() {
//                onItemBindOverride = { itemHolder, _, _ ->
//                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
//                        addTabLayout(this)
//                        tabIndicator.indicatorWidth = 10 * dpi
//                        tabIndicator.indicatorEnableFlow = true
//                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_BOTTOM
//
//                        setTabLayoutConfig {
//                            tabEnableGradientScale = true
//                            tabEnableGradientColor = true
//                            tabSelectColor = resources.getColor(R.color.colorAccent)
//                        }
//                    }
//                }
//            }
            DslSlidingTabLayoutItem()() {
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorWidth = 10 * dpi
                        tabIndicator.indicatorEnableFlow = true
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_BOTTOM
                        tabIndicator.indicatorColor = randomColor()

                        setTabLayoutConfig {
                            tabEnableGradientScale = true
                            tabEnableGradientColor = true
                            tabEnableTextBold = true
                        }
                    }
                }
            }

            DslTabViewPagerItem(childFragmentManager)() {
                onItemBindOverride = { itemHolder, _, _ ->
                    setViewPager(itemHolder.v(R.id.view_pager))
                }
            }
        }
    }
}