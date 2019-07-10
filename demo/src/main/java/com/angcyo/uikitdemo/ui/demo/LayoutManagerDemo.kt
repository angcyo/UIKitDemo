package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.angcyo.lib.L
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.ui.recycler.*
import com.angcyo.uikitdemo.来点数据
import com.angcyo.uiview.less.kotlin.dp
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.kotlin.getColor
import com.angcyo.uiview.less.kotlin.recycleScrapList
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.RRecyclerView
import com.angcyo.uiview.less.recycler.adapter.DslAdapter
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem
import com.angcyo.uiview.less.recycler.adapter.DslDateFilter
import com.angcyo.uiview.less.recycler.adapter.RBaseAdapter
import com.angcyo.uiview.less.widget.RSpinner
import com.dingmouren.layoutmanagergroup.echelon.EchelonLayoutManager
import com.dingmouren.layoutmanagergroup.picker.PickerLayoutManager
import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager
import com.dingmouren.layoutmanagergroup.slide.ItemTouchHelperCallback
import com.dingmouren.layoutmanagergroup.slide.SlideLayoutManager
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/07/05
 */

class LayoutManagerDemo : AppBaseDslRecyclerFragment() {

    override fun getContentLayoutId(): Int {
        return com.angcyo.uikitdemo.R.layout.demo_layout_manager
    }

    override fun initDslRecyclerView(recyclerView: RRecyclerView?) {
        //super.initDslRecyclerView(recyclerView)
        recyclerView?.apply {
            //setRecycledViewPool(getRecyclerViewPool())
            addItemDecoration(baseDslItemDecoration)
            //hoverItemDecoration.attachToRecyclerView(this)

            setPadding(10 * dpi, 10 * dpi, 10 * dpi, 10 * dpi)
            layoutManager = MyLayoutManager()
            layoutManager = MyLayoutManager2()
            layoutManager = MyLayoutManager3()

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
                //L.e("onBindViewHolder...position:$position")
            }
        }
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        来点数据()

        val layoutList = mutableListOf(
            "MyLayoutManager3",//0
            "CircleLayoutManager",//1
            "CircleScaleLayoutManager",//2
            "GalleryLayoutManager",//3
            "LoopLayoutManager",//4
            "RotateLayoutManager",//5
            "ScaleLayoutManager",//6
            "FocusLayoutManager",//7
            "LinearLayoutManager",//8
            "GridLayoutManager",//9
            "StaggeredGridLayoutManager",//10
            "TurnLayoutManager",//11
            "CarouselLayoutManager",//12
            "FlowLayoutManager",//13
            "OverLayCardLayoutManager",//14
            //https://github.com/DingMouRen/LayoutManagerGroup
            "EchelonLayoutManager",//15
            "SkidRightLayoutManager",//16
            "SlideLayoutManager",//17
            "PickerLayoutManager",//18
            "ViewPagerLayoutManager",//19
            "GalleryLayoutManager2",//20
            "MyLayoutManager3"
        )

        val renRenCallback = RenRenCallback()
        val renRenItemTouchHelper = ItemTouchHelper(renRenCallback)

        val slideItemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(baseAdapter, baseAdapter.allDatas))

        viewHolder.v<RSpinner>(com.angcyo.uikitdemo.R.id.spinner).setStrings(layoutList) {
            recyclerView.recycledViewPool.clear()
            recyclerView.onFlingListener = null
            renRenItemTouchHelper.attachToRecyclerView(null)
            slideItemTouchHelper.attachToRecyclerView(null)

            recyclerView.layoutManager = when (it) {
                1 -> CircleLayoutManager(mAttachContext)
                2 -> CircleScaleLayoutManager(mAttachContext)
                3 -> GalleryLayoutManager(mAttachContext, 100 * dpi)
                4 -> LoopLayoutManager(mAttachContext).apply {
                    //infinite = true
                }
                5 -> RotateLayoutManager(mAttachContext, 100 * dpi)
                6 -> ScaleLayoutManager(mAttachContext, 100 * dpi)
                7 -> FocusLayoutManager.Builder()
                    .layerPadding(20 * dp)
                    .normalViewGap(20 * dp)
                    .focusOrientation(FocusLayoutManager.FOCUS_TOP)
                    .isAutoSelect(true)
                    .maxLayerCount(3)
                    .setOnFocusChangeListener { focusdPosition, lastFocusdPosition -> }
                    .build()
                8 -> RRecyclerView.LinearLayoutManagerWrap(mAttachContext)
                9 -> RRecyclerView.GridLayoutManagerWrap(mAttachContext, 4)
                10 -> RRecyclerView.StaggeredGridLayoutManagerWrap(4, RecyclerView.VERTICAL)
                11 -> TurnLayoutManager(mAttachContext, 20 * dpi, 10 * dpi)
                12 -> CarouselLayoutManager(mAttachContext, 100 * dpi)
                13 -> FlowLayoutManager()
                14 -> OverLayCardLayoutManager(mAttachContext).apply {
                    renRenItemTouchHelper.attachToRecyclerView(recyclerView)
                }
                15 -> EchelonLayoutManager(mAttachContext)
                16 -> SkidRightLayoutManager(0.8f, 0.8f)
                17 -> SlideLayoutManager(slideItemTouchHelper).apply {
                    slideItemTouchHelper.attachToRecyclerView(recyclerView)
                }
                18 -> PickerLayoutManager(mAttachContext, RecyclerView.VERTICAL, false)
                19 -> com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager(
                    mAttachContext,
                    RecyclerView.VERTICAL,
                    false
                )
                20 -> GalleryLayoutManager2(RecyclerView.VERTICAL)
                else -> MyLayoutManager3()
            }
        }
    }
}

class MyLayoutManager3 : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(-2, -2)
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    var verticallyScrollOffset = 0
    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        val realDy = fill(recycler, state, dy)
        offsetChildrenVertical(-realDy)
        verticallyScrollOffset += realDy
        return realDy
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        fill(recycler, state)
    }

    private fun fill(recycler: RecyclerView.Recycler, state: RecyclerView.State, dy: Int = 0): Int {
        L.i("$state")
        if (state.itemCount == 0) {
            removeAndRecycleAllViews(recycler)
            return 0
        }

        if (childCount == 0 && state.isPreLayout) {
            return 0
        }

        var topConsumed = paddingTop
        var bottomConsumed = paddingBottom
        var leftConsumed = paddingLeft
        var rightConsumed = paddingRight

        val viewWidth = width
        val viewHeight = height

        //开始布局的位置
        var startPosition = 0
        //开始布局的[Top]
        var startTop = topConsumed

        var layoutTop = startTop
        var layoutLeft = leftConsumed
        var layoutBottom = 0
        var layoutRight = 0

        var firstLayoutTop = 0
        var lastLayoutBottom = 0

        var firstPosition = RecyclerView.NO_POSITION
        var lastPosition = RecyclerView.NO_POSITION

        if (childCount > 0) {
            val firstView = getChildAt(0)
            val lastView = getChildAt(childCount - 1)

            val firstLayoutParams: RecyclerView.LayoutParams = firstView!!.layoutParams as RecyclerView.LayoutParams
            val lastLayoutParams: RecyclerView.LayoutParams = lastView!!.layoutParams as RecyclerView.LayoutParams

            var firstLayoutTopMargin = 0
            var lastLayoutBottomMargin = 0

            firstLayoutTopMargin = firstLayoutParams.topMargin
            lastLayoutBottomMargin = lastLayoutParams.bottomMargin

            firstPosition = firstLayoutParams.viewAdapterPosition
            lastPosition = lastLayoutParams.viewAdapterPosition

            firstLayoutTop = getDecoratedTop(firstView) - firstLayoutTopMargin
            lastLayoutBottom = getDecoratedBottom(lastView) + lastLayoutBottomMargin

            //已经在界面上的[child],按照原来的位置布局
            startPosition = firstPosition
            startTop = firstLayoutTop
        }

        //临时缓存界面上所有的child(如果有).此方法不会触发 [onCreateViewHolder]
        detachAndScrapAttachedViews(recycler)

        //兼容滚动, 保持原有的[child]布局不变, 判断首页是否需要[add]或[remove]
        //允许的最大滚动距离

        var bottomUnconsumed = 0 //底部还有多少距离未消耗, 用来追加[child]
        var realDy = dy
        if (dy > 0) {
            //手指向上滑动

            //1.判断是否有距离允许滚动
            if (lastPosition == state.itemCount - 1) {
                //最后一个已经可见, 不够距离滚动
                if (lastLayoutBottom <= viewHeight - bottomConsumed) {
                    //没空间可以滚动, 总共[child]的高度, 加起来还不够视图的高度
                    realDy = 0
                } else {
                    realDy = min(lastLayoutBottom + bottomConsumed - viewHeight, dy)
                }
            } else {
                bottomUnconsumed = dy - (lastLayoutBottom + bottomConsumed - viewHeight)
            }

            //2.底部填充多出来的滚动空间
            //如果在此处直接调用[addView]会影响[child]在[ViewGroup]中的位置, 所以先将剩余空间保存起来, 在布局之后[addView]

        } else if (dy < 0) {
            //手指向下滑动

            //1.判断是否有距离允许滚动
            var topUnconsumed = 0 //顶部还有多少距离未消耗, 用来追加[child]
            if (firstPosition == 0) {
                //第一个已经可见, 不够距离滚动
                if (firstLayoutTop >= topConsumed) {
                    realDy = 0
                } else {
                    realDy = max(firstLayoutTop - topConsumed, dy)
                }
            } else {
                topUnconsumed = abs(dy) - abs(firstLayoutTop - topConsumed)
            }

            //2.顶部填充多出来的滚动空间
            layoutBottom = firstLayoutTop
            while (topUnconsumed > 0 && firstPosition >= 0) {
                attachView(recycler, firstPosition - 1) { childView, childLayoutParams ->

                    layoutTop = layoutBottom - getDecoratedMeasuredHeight(childView) -
                            childLayoutParams.bottomMargin - childLayoutParams.topMargin

                    layoutRight = layoutLeft + getDecoratedMeasuredWidth(childView) +
                            childLayoutParams.leftMargin + childLayoutParams.rightMargin

                    //布局[child]
                    layoutDecoratedWithMargins(
                        childView,
                        layoutLeft, layoutTop,
                        layoutRight, layoutBottom
                    )

                    //消耗空间
                    topUnconsumed -= layoutBottom - layoutTop

                    layoutBottom = layoutTop

                    firstPosition--
                }
            }
            startTop = layoutBottom
            startPosition = min(firstPosition, startPosition)

            //3.由于往顶部[addView]会影响[child]在[ViewGroup]中的位置, 所以这里再一次清空所有[child], 重新按照正序布局
            detachAndScrapAttachedViews(recycler)
        }

        layoutTop = startTop
        layoutLeft = leftConsumed
        layoutBottom = 0
        layoutRight = 0

        //标准状态时, 视图应该有的[child]
        for (position in max(startPosition, 0) until state.itemCount) {
            //从各种缓存池中, 获取指定位置的 [View]
            val childView = recycler.getViewForPosition(position)
            val childLayoutParams: RecyclerView.LayoutParams = childView.layoutParams as RecyclerView.LayoutParams

            //将[child]添加到[RecyclerView]上
            addView(childView)

            //测量[child], 会考虑 [ItemDecoration] 所占得空间
            measureChildWithMargins(childView, 0, 0)

            layoutBottom = layoutTop + getDecoratedMeasuredHeight(childView) +
                    childLayoutParams.bottomMargin + childLayoutParams.topMargin

            layoutRight = layoutLeft + getDecoratedMeasuredWidth(childView) +
                    childLayoutParams.leftMargin + childLayoutParams.rightMargin

            //布局[child]
            layoutDecoratedWithMargins(
                childView,
                layoutLeft, layoutTop,
                layoutRight, layoutBottom
            )

            L.e(
                "pos:$position l:$layoutLeft t:$layoutTop r:$layoutRight b:$layoutBottom " +
                        "w:${getDecoratedMeasuredWidth(childView)} h:${getDecoratedMeasuredHeight(childView)} " +
                        "isItemRemoved:${childLayoutParams.isItemRemoved} isItemChanged:${childLayoutParams.isItemChanged}"
            )

            layoutTop = layoutBottom

            if (layoutBottom >= viewHeight - bottomConsumed) {
                //无剩余空间了
                break
            }
        }

        //滚动后, 填充底部多余出来的滚动空间
        layoutTop = lastLayoutBottom
        while (bottomUnconsumed > 0 && lastPosition < state.itemCount - 1) {
            attachView(recycler, lastPosition + 1) { childView, childLayoutParams ->

                layoutBottom = layoutTop + getDecoratedMeasuredHeight(childView) +
                        childLayoutParams.bottomMargin + childLayoutParams.topMargin

                layoutRight = layoutLeft + getDecoratedMeasuredWidth(childView) +
                        childLayoutParams.leftMargin + childLayoutParams.rightMargin

                //布局[child]
                layoutDecoratedWithMargins(
                    childView,
                    layoutLeft, layoutTop,
                    layoutRight, layoutBottom
                )

                //消耗空间
                bottomUnconsumed -= layoutBottom - layoutTop

                layoutTop = layoutBottom

                lastPosition++
            }
        }

        //回收不在可见范围之内的[child]
        for (i in childCount - 1 downTo 0) {
            val childView = getChildAt(i)!!
            val childLayoutParams: RecyclerView.LayoutParams = childView.layoutParams as RecyclerView.LayoutParams

            val top = getDecoratedTop(childView) - childLayoutParams.topMargin
            val bottom = getDecoratedBottom(childView) + childLayoutParams.bottomMargin

            if (top - realDy >= viewHeight - bottomConsumed || bottom - realDy <= 0) {
                //底部超出 视图底部, 顶部超出 视图顶部
                removeAndRecycleView(childView, recycler)
                L.i("回收越界:${childLayoutParams.viewAdapterPosition}")
            }
        }

        recycleScrapList(recycler)

        L.e(
            "childCount= [" + childCount + "]" +
                    ",[recycler.getScrapList().size():" + recycler.scrapList.size
        )

        return realDy
    }

    private fun attachView(
        recycler: RecyclerView.Recycler,
        position: Int,
        callback: (View, RecyclerView.LayoutParams) -> Unit
    ) {
        //从各种缓存池中, 获取指定位置的 [View]
        val childView = recycler.getViewForPosition(position)
        val childLayoutParams: RecyclerView.LayoutParams = childView.layoutParams as RecyclerView.LayoutParams

        //将[child]添加到[RecyclerView]上
        addView(childView)

        //测量[child], 会考虑 [ItemDecoration] 所占得空间
        measureChildWithMargins(childView, 0, 0)

        callback.invoke(childView, childLayoutParams)
    }
}

@Deprecated("未向下滚动")
class MyLayoutManager2 : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(-2, -2)
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    var verticallyScrollOffset = 0
    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        val realDy = fill(recycler, state, dy)
        offsetChildrenVertical(-realDy)
        verticallyScrollOffset += realDy
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
        var topConsumed = paddingTop
        var bottomConsumed = paddingBottom
        var leftConsumed = paddingLeft
        var rightConsumed = paddingRight

        val viewWidth = width
        val viewHeight = height

        //第一个布局的顶部位置, 和最后一个布局的底部位置
        //由于这里是 先布局, 后滚动偏移, 所以, item的top值, 就是滚动之后的.
        var firstLayoutTop = topConsumed
        var lastLayoutBottom = topConsumed

        var firstPosition = 0
        var lastPosition = RecyclerView.NO_POSITION

        var firstLayoutTopMargin = 0
        var lastLayoutBottomMargin = 0
        if (childCount > 0) {
            val firstView = getChildAt(0)
            val lastView = getChildAt(childCount - 1)

            val firstLayoutParams: RecyclerView.LayoutParams = firstView!!.layoutParams as RecyclerView.LayoutParams
            val lastLayoutParams: RecyclerView.LayoutParams = lastView!!.layoutParams as RecyclerView.LayoutParams

            firstLayoutTopMargin = firstLayoutParams.topMargin
            lastLayoutBottomMargin = lastLayoutParams.bottomMargin

            firstPosition = firstLayoutParams.viewAdapterPosition
            lastPosition = lastLayoutParams.viewAdapterPosition

            firstLayoutTop = getDecoratedTop(firstView)
            lastLayoutBottom = getDecoratedBottom(lastView)
        }

        L.w(
            "firstLayoutTop:${firstLayoutTop - firstLayoutTopMargin} lastLayoutBottom:${lastLayoutBottom + lastLayoutBottomMargin}" +
                    " firstPosition:$firstPosition lastPosition:$lastPosition"
        )

        //允许的最大滚动距离
        var realDy = dy
        if (dy > 0) {
            //手指向上滑动
            //向上, 需要判断底部边界
            if (lastPosition == state.itemCount - 1) {
                //最后一个已经可见
                realDy = min(lastLayoutBottom + lastLayoutBottomMargin + bottomConsumed - viewHeight, dy)
            } else if (lastLayoutBottom + lastLayoutBottomMargin + bottomConsumed < viewHeight) {
                //需要显示下一个item
                ++lastPosition
            }
        } else if (dy < 0) {
            //手指向下滑动
            //向下, 需要判断顶部边界
            if (firstPosition == 0) {
                //第一个已经可见
                realDy = max(firstLayoutTop - firstLayoutTopMargin - topConsumed, dy)
            } else if (firstLayoutTop - firstLayoutTopMargin - topConsumed < dy) {
                //需要显示下一个item
                --firstPosition
            }
        }
        //经过上面算法之后, 可以知道界面上需要布局item的起止位置.

        L.i("dy:$dy rdy:$realDy sy:$verticallyScrollOffset")

        //临时缓存界面上所有的child(如果有).此方法不会触发 [onCreateViewHolder]
        detachAndScrapAttachedViews(recycler)

        var layoutTop = firstLayoutTop
        var layoutBottom = 0
        var layoutLeft = 0
        var layoutRight = 0

        for (position in firstPosition until state.itemCount) {
            //从各种缓存池中, 获取指定位置的 [View]
            val childView = recycler.getViewForPosition(position)
            val childLayoutParams: RecyclerView.LayoutParams = childView.layoutParams as RecyclerView.LayoutParams

            //将[child]添加到[RecyclerView]上
            addView(childView)

            //测量[child], 会考虑 [ItemDecoration] 所占得空间
            measureChildWithMargins(childView, 0, 0)

            layoutTop += childLayoutParams.topMargin
            layoutBottom = layoutTop + getDecoratedMeasuredHeight(childView) + childLayoutParams.bottomMargin
            layoutLeft = leftConsumed + childLayoutParams.leftMargin
            layoutRight = layoutLeft + getDecoratedMeasuredWidth(childView)

            //布局[child]
            layoutDecoratedWithMargins(
                childView,
                layoutLeft, layoutTop,
                layoutRight, layoutBottom
            )

            L.e(
                "pos:$position l:$layoutLeft t:$layoutTop r:$layoutRight b:$layoutBottom " +
                        "w:${getDecoratedMeasuredWidth(childView)} h:${getDecoratedMeasuredHeight(childView)} "
            )

            //判断[child]是否布局到视图外, 用于回收屏幕之外的[item]
            if (dy < 0) {
                if ((layoutTop - realDy) >= viewHeight - bottomConsumed) {
                    //手指向下滚动 只需要判断回收底部的[child]
                    removeAndRecycleView(childView, recycler)
                }
            } else if (dy > 0) {
                if ((layoutBottom + realDy) <= topConsumed) {
                    //手指向上滚动 只需要判断回收顶部的[child]
                    removeAndRecycleView(childView, recycler)
                }
            }

            layoutTop = layoutBottom

            if (lastPosition == RecyclerView.NO_POSITION) {
                if (layoutBottom + bottomConsumed >= viewHeight) {
                    break
                }
            } else if (position == lastPosition) {
                break
            }
        }

        recycleScrapList(recycler)

        L.e(
            "childCount= [" + childCount + "]" +
                    ",[recycler.getScrapList().size():" + recycler.scrapList.size
        )

        return realDy
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