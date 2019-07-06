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
import com.angcyo.uiview.less.recycler.adapter.DslAdapter
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem
import com.angcyo.uiview.less.recycler.adapter.DslDateFilter
import com.angcyo.uiview.less.recycler.adapter.RBaseAdapter

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
            setBackgroundColor(getColor(R.color.transparent_dark20))
        }
    }

    override fun onCreateAdapter(datas: MutableList<DslAdapterItem>?): RBaseAdapter<DslAdapterItem> {
        return object : DslAdapter(mAttachContext, datas) {
            init {
                dslDateFilter = DslDateFilter(this)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RBaseViewHolder {
                L.e("onCreateViewHolder...测试消息 viewType:$viewType")
                return super.onCreateViewHolder(parent, viewType)
            }

            override fun onBindViewHolder(holder: RBaseViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                L.e("onBindViewHolder...测试消息 position:$position")
            }
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

        if (itemCount == 0) {//没有Item，界面空着吧
            removeAndRecycleAllViews(recycler)
            return
        }
        if (state.isPreLayout) {//state.isPreLayout()是支持动画的
            return
        }
        //onLayoutChildren方法在RecyclerView 初始化时 会执行两遍
        detachAndScrapAttachedViews(recycler);
        //初始化
//        mVerticalOffset = 0;
//        mFirstVisiPos = 0;
//        mLastVisiPos = getItemCount();
//
//        //初始化时调用 填充childView
//        fill(recycler, state);

        val firstItemTop = paddingTop
        var left = paddingLeft
        var top = firstItemTop
        var right = width - paddingRight
        var bottom = 0

        val firstItemPosition = 0
        for (position in firstItemPosition until itemCount) {

            //从各种缓存池中, 获取指定位置的 [View]
            val childView = recycler.getViewForPosition(position)
            val childLayoutParams: RecyclerView.LayoutParams = childView.layoutParams as RecyclerView.LayoutParams

            //将[child]添加到[RecyclerView]上
            addView(childView)

            //测量[child], 会考虑 [ItemDecoration] 所占得空间
            measureChildWithMargins(childView, 0, 0)

            top += childLayoutParams.topMargin
            bottom = top + getDecoratedMeasuredHeight(childView) + childLayoutParams.bottomMargin
            //布局[child]
            layoutDecoratedWithMargins(childView, left, top, right, bottom)

            if (bottom - firstItemTop >= height - paddingTop - paddingBottom) {
                //[RecyclerView] 没有空间可以布局了
                break
            } else {
                top = bottom
            }
        }
    }
}