package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angcyo.lib.L
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.来点数据
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.kotlin.getColor
import com.angcyo.uiview.less.kotlin.recycleScrapList
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.RRecyclerView
import com.angcyo.uiview.less.recycler.adapter.DslAdapter
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem
import com.angcyo.uiview.less.recycler.adapter.DslDateFilter
import com.angcyo.uiview.less.recycler.adapter.RBaseAdapter
import kotlin.math.max


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
            setPadding(10 * dpi, 10 * dpi, 10 * dpi, 10 * dpi)
            layoutManager = MyLayoutManager2()
            setBackgroundColor(getColor(com.angcyo.uikitdemo.R.color.transparent_dark20))
        }
    }

    override fun onCreateAdapter(datas: MutableList<DslAdapterItem>?): RBaseAdapter<DslAdapterItem> {
        return object : DslAdapter(mAttachContext, datas) {
            init {
                dslDateFilter = DslDateFilter(this)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RBaseViewHolder {
                L.e("onCreateViewHolder...viewType:$viewType")
                return super.onCreateViewHolder(parent, viewType)
            }

            override fun onBindViewHolder(holder: RBaseViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                L.e("onBindViewHolder...position:$position")
            }
        }
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        来点数据()
    }
}

class MyLayoutManager2 : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(-2, -2)
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    var scrollOffset = 0
    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        val realDy = fill(recycler, state, dy)
        scrollOffset += realDy
        return realDy
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (state.itemCount == 0 || state.isPreLayout) {
            removeAndRecycleAllViews(recycler)
            return
        }

        fill(recycler, state)
    }

    private fun fill(recycler: RecyclerView.Recycler, state: RecyclerView.State, dy: Int = 0): Int {
        var offsetTop = paddingTop

        //第一个布局的顶部位置, 和最后一个布局的底部位置
        var firstLayoutTop = 0
        var lastLayoutBottom = 0

        var firstPosition = 0
        var lastPosition = 0

        if (childCount > 0) {
            val firstView = getChildAt(0)
            val lastView = getChildAt(childCount - 1)

            firstPosition = getPosition(firstView!!)
            lastPosition = getPosition(lastView!!)

            firstLayoutTop = getDecoratedTop(firstView)
            lastLayoutBottom = getDecoratedBottom(lastView)
        }

        detachAndScrapAttachedViews(recycler)

        if (dy > 0) {
            //
        }

        return 0
    }
}

@Deprecated("未修复滚动")
class MyLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        L.e("generateDefaultLayoutParams...")
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    /**保存总共滚动偏移量*/
    var scrollVerticalOffset = 0L

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        if (itemCount == 0 || dy == 0) {
            return 0
        }
        val realOffset = fillVertical(recycler, state, dy)
        offsetChildrenVertical(-realOffset)
        return realOffset
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

        //填充childView
        fillVertical(recycler, state, 0)
    }

    /**记录完成一次布局后, 顶部[item]的[position], 和底部[item]的[position]*/
    var lastTopPosition = RecyclerView.NO_POSITION
    var lastBottomPosition = RecyclerView.NO_POSITION
    /**记录完成一次布局后, 顶部[item]的[height], 和底部[item]的[height], 包含[分割线]和[margin]*/
    var lastTopViewHeight = 0
    var lastBottomViewHeight = 0

    /**
     * 填充布局的时候, 一定要明确, 需要填充的第一个[item]的[position], 和 最后一个用完屏幕空间[item]的[position]
     * 然后for循环, 一个一个布局.
     * 并且要考虑 当前滚动的偏移量哦
     *
     * @param dy 当前偏移的距离
     * @return 针对 dy 偏移距离, 返回修正后, 允许偏移的最大距离 (滚动边界处理)
     * */
    fun fillVertical(recycler: RecyclerView.Recycler, state: RecyclerView.State, dy: Int): Int {

        L.e("before...child count = " + childCount + ";scrap count = ${recycler.scrapList.size}")
        //onLayoutChildren方法在RecyclerView 初始化时 会执行两遍
        detachAndScrapAttachedViews(recycler)
        L.e("after....child count = " + childCount + ";scrap count = ${recycler.scrapList.size}")


        //此次滚动后, 临时保存的总共偏移距离
        var offsetVertical = scrollVerticalOffset + dy
        //此次滚动, 能够允许偏移的最大距离
        var offsetDy = dy

        var firstLayoutItemPosition: Int

        //第一步: 根据滚动偏移, 计算出第一个需要布局item的position.
        //如果, 每个item的高度都一样, 并且不考虑 [itemDecoration] 的话. 那就太简单了. position=offsetVertical/itemHeight
        //这里, 给自己挖个坑. 支持不同高度的item, 并且支持[itemDecoration].

        //...瞎几把乱写+闭幕思过之后, 我想到了一个简洁方法.
        //垂直滚动, 无非就2个方向, 向上滚, 向下滚.
        //只要假设即向上, 也向下. 不就完了? 多余的之后回收一下就行了.


//        if (lastTopPosition == RecyclerView.NO_POSITION || childCount == 0) {
//            //之前还没有布局过, 那肯定从0开始
//            firstLayoutItemPosition = 0
//        } else {
//            //之前已经布局过
//            firstLayoutItemPosition = lastTopPosition
//
//            //界面上第一个[child]
//            val firstChildView = getChildAt(0)!!
//            val layoutParams: RecyclerView.LayoutParams = firstChildView.layoutParams as RecyclerView.LayoutParams
//            if (dy > 0) {
//                //手指向上滑动
//                val firstBottom = getDecoratedBottom(firstChildView) + layoutParams.bottomMargin
//                if (offsetDy >= firstBottom) {
//                    //当前的偏移量, 已经足以让第一个[child]滚出屏幕
//                    firstLayoutItemPosition = getPosition(firstChildView) + 1
//                }
//            } else if (dy < 0) {
//                //手指向下滑动
//
//                val firstTop = getDecoratedTop(firstChildView) - layoutParams.topMargin
//                if (firstTop > offsetDy) {
//                    //当前的偏移量, 已经足以让第一个[child]滚出屏幕
//                    firstLayoutItemPosition = getPosition(firstChildView) - 1
//                    offsetDy = firstTop
//                }
//            } else {
//
//            }
//        }


        if (lastTopPosition == RecyclerView.NO_POSITION) {
            //之前还没有布局过, 那肯定从0开始
            firstLayoutItemPosition = 0
        } else {
            //之前已经布局过
            firstLayoutItemPosition = lastTopPosition

            if (dy < 0) {
                //手指向下滑动

                val firstChildView = recycler.getViewForPosition(lastTopPosition)
                val layoutParams: RecyclerView.LayoutParams = firstChildView.layoutParams as RecyclerView.LayoutParams

                val firstTop = getDecoratedTop(firstChildView) - layoutParams.topMargin
                if (firstTop > offsetDy) {
                    //当前的偏移量, 已经足以让第一个[child]滚出屏幕
                    firstLayoutItemPosition = getPosition(firstChildView) - 1
                    offsetDy = firstTop
                }
            }
        }

        //上面只算出了开始[position], 结束的[position]通过循环, 动态计算

        val firstItemTop: Int = (paddingTop - scrollVerticalOffset).toInt()
        var left = paddingLeft
        var top = firstItemTop
        var right = width - paddingRight
        var bottom = 0

        lastTopPosition = max(firstLayoutItemPosition, 0)

        L.w("开始布局:$lastTopPosition  偏移:$scrollVerticalOffset")
        for (position in lastTopPosition until itemCount) {

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

            L.w(
                "pos:$position l:$left t:$top r:$right b:$bottom " +
                        "w:${getDecoratedMeasuredWidth(childView)} h:${getDecoratedMeasuredHeight(childView)} " +
                        "dy:$offsetDy sy:$scrollVerticalOffset"
            )

            if (top <= 0 && bottom > 0) {
                lastTopPosition = position
            }

            if (bottom >= height - paddingBottom) {
                //[RecyclerView] 没有空间可以布局了
                lastBottomPosition = position
                break
            } else {
                top = bottom
            }
        }

        L.w("结束布局:$lastTopPosition $lastBottomPosition -> ${lastBottomPosition - lastTopPosition} $childCount")

        recycleScrapList(recycler)

        L.e("childCount= [" + childCount + "]" + ",[recycler.getScrapList().size():" + recycler.scrapList.size)

        scrollVerticalOffset += offsetDy

        return offsetDy
    }
}