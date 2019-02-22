package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseTitleFragment
import com.angcyo.uikitdemo.ui.fragment.TextFragment
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.widget.group.RTabLayout
import com.angcyo.uiview.less.widget.pager.RFragmentAdapter

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/02/21
 */
class TabLayoutDemo : AppBaseTitleFragment() {
    override fun getContentLayoutId(): Int {
        return R.layout.demo_tab_layout
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        viewHolder.rpager(R.id.view_pager).apply {
            adapter =
                RFragmentAdapter(
                    childFragmentManager,
                    listOf(
                        TextFragment.get("Fragment 1"),
                        TextFragment.get("Fragment 2"),
                        TextFragment.get("Fragment 3"),
                        TextFragment.get("Fragment 4"),
                        TextFragment.get("Fragment 5"),
                        TextFragment.get("Fragment 6"),
                        TextFragment.get("Fragment 7"),
                        TextFragment.get("Fragment 8")
                    )
                )
                    .apply {
                        setParentFragment(this@TabLayoutDemo)
                    }

            viewHolder.tab(R.id.tab8).setupViewPager(this)
            viewHolder.tab(R.id.tab9).setupViewPager(this)
            viewHolder.tab(R.id.tab9).onTabLayoutListener = RTabLayout.DefaultTabLayoutListener(this)
        }
    }
}