package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseTitleFragment
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.widget.group.RTabLayout

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/08/17
 */
class ViewPager2Demo : AppBaseTitleFragment() {
    override fun getContentLayoutId(): Int {
        return R.layout.demo_view_pager2
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        viewHolder.v<ViewPager2>(R.id.view_pager2).apply {
            viewHolder.tab(R.id.tab_layout).onTabLayoutListener =
                object : RTabLayout.OnTabLayoutListener() {
                    override fun onTabSelector(
                        tabLayout: RTabLayout,
                        fromIndex: Int,
                        toIndex: Int
                    ) {
                        super.onTabSelector(tabLayout, fromIndex, toIndex)
                        setCurrentItem(toIndex, true)
                    }
                }

            val fragmentList = mutableListOf(
                RecyclerViewDemo(),
                SpanDemo(),
                WidgetDemo(),
                SoftInputDemo(),
                ViewPager2Demo()
            )

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)

                    viewHolder.tab(R.id.tab_layout).onPageScrollStateChanged(state)
                    L.i("-->$state")
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                    viewHolder.tab(R.id.tab_layout)
                        .onPageScrolled(position, positionOffset, positionOffsetPixels)
                    L.i("-->$position $positionOffset $positionOffsetPixels")
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewHolder.tab(R.id.tab_layout).onPageSelected(position)

                    L.i("-->$position")

                    lastFragment = fragmentList[position]

                    viewHolder.tab(R.id.tab_layout).setCurrentItem(position, false)
                }
            })


            setPageTransformer(MarginPageTransformer(50 * dpi))

            adapter = object : FragmentStateAdapter(this@ViewPager2Demo) {
                override fun getItemCount(): Int {
                    return fragmentList.size
                }

                override fun createFragment(position: Int): Fragment {
                    return fragmentList[position]
                }
            }
        }
    }
}