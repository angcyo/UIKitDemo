package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.来点数据
import com.angcyo.uiview.less.kotlin.getColor
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.RRecyclerView

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/07/05
 */

class LayoutManagerDemo : AppBaseDslRecyclerFragment() {
    override fun initRecyclerView(recyclerView: RRecyclerView?) {
        super.initRecyclerView(recyclerView)
        recyclerView?.apply {
            layoutManager = MyLayoutManager()
            setBackgroundColor(getColor(R.color.base_dark_red_tran))
        }
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        来点数据()
    }
}

class MyLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        L.e("generateDefaultLayoutParams...")
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        //super.onLayoutChildren(recycler, state)
        L.e("$state")
    }
}