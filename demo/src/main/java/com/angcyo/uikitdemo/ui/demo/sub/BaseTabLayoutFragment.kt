package com.angcyo.uikitdemo.ui.item

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.dsl.tablayout.DslTabLayout

open class BaseTabLayoutFragment : AppBaseDslRecyclerFragment() {

    val tabLayoutList = mutableListOf<DslTabLayout>()

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