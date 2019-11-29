package com.angcyo.uikitdemo.ui.demo.sub

import android.graphics.Color
import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.item.DslCommonTabLayoutItem
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
class CommonFragment : BaseTabLayoutFragment() {

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        renderDslAdapter {

            DslCommonTabLayoutItem()() {
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_NONE
                    }
                }
            }

            DslCommonTabLayoutItem()() {
                itemTopInsert = 10 * dpi
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorWidth = 4 * dpi
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_TOP
                        tabIndicator.indicatorColor = randomColor()
                        configTabLayoutConfig {
                            tabSelectColor = tabIndicator.indicatorColor
                        }
                    }
                }
            }

            DslCommonTabLayoutItem()() {
                itemTopInsert = 10 * dpi
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorWidth = 20 * dpi
                        tabIndicator.indicatorEnableFlow = true
                        tabIndicator.indicatorColor = randomColor()
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_BOTTOM

                        configTabLayoutConfig {
                            tabEnableGradientColor = true
                            tabSelectColor = tabIndicator.indicatorColor
                        }
                    }
                }
            }

            //2...

            DslCommonTabLayoutItem()() {
                itemLayoutId = R.layout.item_common_tab_layout2
                itemTopInsert = 10 * dpi
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorWidth = 20 * dpi
                        tabIndicator.indicatorEnableFlow = true
                        tabIndicator.indicatorColor = Color.WHITE
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_TOP

                        configTabLayoutConfig {
                            tabEnableGradientColor = true
                            tabSelectColor = tabIndicator.indicatorColor
                        }
                    }
                }
            }

            DslCommonTabLayoutItem()() {
                itemLayoutId = R.layout.item_common_tab_layout2
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorWidth = 20 * dpi
                        tabIndicator.indicatorEnableFlow = true
                        tabIndicator.indicatorColor = Color.WHITE
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_BOTTOM

                        configTabLayoutConfig {
                            tabEnableGradientColor = true
                            tabSelectColor = tabIndicator.indicatorColor
                        }
                    }
                }
            }

            DslCommonTabLayoutItem()() {
                itemLayoutId = R.layout.item_common_tab_layout2
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorWidth = -2
                        tabIndicator.indicatorHeight = -1
                        tabIndicator.indicatorHeightOffset = -20 * dpi
                        tabIndicator.indicatorWidthOffset = -10 * dpi
                        tabIndicator.indicatorEnableFlow = true
                        tabIndicator.indicatorColor = Color.parseColor("#40000000")
                        tabIndicator.indicatorDrawable =
                            getDrawable(R.drawable.indicator_round_background_tran)
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_BACKGROUND

                        configTabLayoutConfig {
                            tabEnableGradientColor = true
                        }
                    }
                }
            }

            DslCommonTabLayoutItem()() {
                itemLayoutId = R.layout.item_common_tab_layout2
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.v<DslTabLayout>(R.id.tab_layout).apply {
                        addTabLayout(this)
                        tabIndicator.indicatorWidth = -2
                        tabIndicator.indicatorHeight = -1
                        tabIndicator.indicatorHeightOffset = -20 * dpi
                        tabIndicator.indicatorWidthOffset = -10 * dpi
                        tabIndicator.indicatorColor = Color.parseColor("#40000000")
                        tabIndicator.indicatorStyle = DslTabIndicator.INDICATOR_STYLE_BACKGROUND
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