package com.angcyo.uikitdemo.ui.demo.sub

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.dsl.tablayout.DslTabLayout

open class BaseTabLayoutFragment : AppBaseDslRecyclerFragment() {

    val tabLayoutList = mutableListOf<DslTabLayout>()

    override fun getRecyclerViewPool(): RecyclerView.RecycledViewPool? {
        return null
    }

    fun addTabLayout(tabLayout: DslTabLayout) {
        if (!tabLayoutList.contains(tabLayout)) {
            tabLayoutList.add(tabLayout)
        }
    }

    fun setViewPager(viewPager: ViewPager) {
        tabLayoutList.forEach {
            it.setupViewPager(viewPager)
        }
    }
}