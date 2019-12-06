package com.angcyo.uikitdemo.ui.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.OverScroller
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.forEachIndexed
import androidx.recyclerview.widget.RecyclerView
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.utils.UI
import com.angcyo.uiview.less.widget.behavior.BaseDependsBehavior
import com.angcyo.uiview.less.widget.group.SwipeBackLayout.clamp
import kotlin.math.abs

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/12/02
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

open class StickHeaderBehavior(
    val context: Context,
    attrs: AttributeSet? = null
) : BaseDependsBehavior<View>(context, attrs), Runnable {

    //fling 速率阈值
    var _minFlingVelocity = 50
    var _maxFlingVelocity = 8000
    var _touchSlop = 0

    init {
        showLog = false

        val vc = ViewConfiguration.get(context)
        _minFlingVelocity = vc.scaledMinimumFlingVelocity
        _maxFlingVelocity = vc.scaledMaximumFlingVelocity
    }

    /**布局开始的Top值*/
    var lastLayoutTop = 0

    var _childView: View? = null
    var _parentView: ViewGroup? = null
    var _pagerWrapView: View? = null

    /**头部[RecyclerView]*/
    var topRecyclerView: RecyclerView? = null

    /**[ViewPager]中的[RecyclerView]*/
    val bottomRecyclerView: RecyclerView?
        get() = _pagerWrapView?.findRecyclerView()

    //<editor-fold desc="可操作方法">

    /**To完全展开状态*/
    fun stickToOpen() {
        _overScroller.startScroll(0, 0, 0, -childHeight)
        _lastScrollY = _overScroller.currY
        _childView?.post(this)
    }

    /**To完全关闭状态*/
    fun stickToClose() {
        _overScroller.startScroll(0, 0, 0, childHeight)
        _lastScrollY = _overScroller.currY
        _childView?.post(this)
    }

    /**是否处于完全打开状态*/
    fun isStickOpen(): Boolean {
        return lastLayoutTop == 0
    }

    /**是否处于完全关闭状态*/
    fun isStickClose(): Boolean {
        return childHeight == -lastLayoutTop
    }

    //</editor-fold desc="可操作方法">

    //<editor-fold desc="基础方法">

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        _childView = child
        _parentView = parent
        topRecyclerView = child.findRecyclerView()
        dependency.layoutParams.coordinatorParams {
            if (behavior is StickHeaderPagerBehavior) {
                _pagerWrapView = dependency
            }
        }
        return super.layoutDependsOn(parent, child, dependency)
    }

    override fun onMeasureChild(
        parent: CoordinatorLayout,
        child: View,
        parentWidthMeasureSpec: Int,
        widthUsed: Int,
        parentHeightMeasureSpec: Int,
        heightUsed: Int
    ): Boolean {
        super.onMeasureChild(
            parent,
            child,
            parentWidthMeasureSpec,
            widthUsed,
            parentHeightMeasureSpec,
            heightUsed
        )

        val lp = child.layoutParams
        if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT &&
            child !is RecyclerView &&
            topRecyclerView == null
        ) {
            parent.onMeasureChild(
                child,
                parentWidthMeasureSpec,
                widthUsed,
                ViewGroup.getChildMeasureSpec(
                    View.MeasureSpec.makeMeasureSpec(
                        0,
                        View.MeasureSpec.UNSPECIFIED
                    ), parent.paddingTop + parent.paddingBottom, lp.height
                ),
                heightUsed
            )
            return true
        }
        return false
    }

    override fun onLayoutAfter(parent: CoordinatorLayout, child: View, layoutDirection: Int) {
        super.onLayoutAfter(parent, child, layoutDirection)
        _layoutTop(parent, lastLayoutTop)
    }

    //</editor-fold desc="基础方法">

    //<editor-fold desc="布局滚动">

    /**重新调整布局top*/
    fun _layoutTop(parent: ViewGroup, top: Int) {
        var layoutTop = top

        _childView?.let {
            it.offsetTopTo(layoutTop, -it.measuredHeight, 0)

            layoutTop = it.bottom

            lastLayoutTop = it.top
        }

        parent.forEachIndexed { _, view ->
            if (view.visibility != View.GONE) {
                view.layoutParams.coordinatorParams {
                    if (behavior is IStickHeaderFollowView) {
                        view.offsetTopTo(layoutTop)
                        layoutTop = view.bottom
                    }
                }
            }
        }
    }

    /**滚动dy距离, 返回值表示是否滚动到头了*/
    fun _scrollBy(parent: ViewGroup?, dy: Int): Boolean {
        if (_childView == null || parent == null) {
            return true
        }

        //L.w("_scrollBy:$dy")

        _layoutTop(parent, lastLayoutTop - dy)

        val result = (dy < 0 && _childView!!.top == 0) || (dy > 0 && _childView!!.bottom == 0)

        return result
    }

    //</editor-fold desc="布局滚动">

    //<editor-fold desc="手势滚动监听">

    var _lastScrollY = 0
    val childHeight: Int
        get() = _childView?.measuredHeight ?: 0

    override fun run() {
        if (_overScroller.computeScrollOffset()) {
            val velocity = _overScroller.currVelocity
            val dy = _overScroller.currY - _lastScrollY
            //L.e("v:$velocity currY:${_overScroller.currY} lastY:$_lastScrollY lastLayoutTop:$lastLayoutTop dy:$dy")

            _lastScrollY = _overScroller.currY

            if (_scrollBy(_parentView, dy)) {
                if (dy > 0) {
                    bottomRecyclerView?.fling(0, velocity.toInt())
                } else {
                    topRecyclerView?.fling(0, -velocity.toInt())
                }
                _overScroller.abortAnimation()
            } else {
                _childView?.post(this@StickHeaderBehavior)
            }
        }
    }

    //滚动支持
    val _overScroller: OverScroller by lazy {
        OverScroller(context)
    }

    //手势检测
    val _gestureDetector: GestureDetector by lazy {
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                val absY = abs(velocityY)

                if (absY > _minFlingVelocity && _nestedScrollView == null && _handleTouch) {

                    val vY = _velocity(-velocityY.toInt())

                    if (velocityY > 0) {
                        //Y轴向下fling
                        val bottomRecyclerView = bottomRecyclerView
                        if (UI.canChildScrollUp(bottomRecyclerView)) {
                            bottomRecyclerView?.fling(0, vY)
                            return false
                        }
                    }

                    val minY = 0
                    val maxY = childHeight

                    _overScroller.fling(
                        0,
                        lastLayoutTop.abs(),
                        0,
                        vY,
                        0,
                        0,
                        minY,
                        maxY,
                        0,
                        0
                    )

                    _lastScrollY = _overScroller.currY

                    //L.w("velocityY:$velocityY vY:$vY lastY:$_lastScrollY")

                    _childView?.post(this@StickHeaderBehavior)
                    return true
                }
                return false
            }

            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                val absX = abs(distanceX)
                val absY = abs(distanceY)

                if (absX > _touchSlop || absY > _touchSlop) {
                    if (_isFirstScroll) {
                        if (absX > absY) {
                            _handleTouch = false
                            return false
                        }
                        _isFirstScroll = false
                    }

                    if (absY > absX) {
                        //L.e("onScroll:$distanceX $distanceY")

                        if (_nestedScrollView == null) {
                            val bottomRecyclerView = bottomRecyclerView

                            if (distanceY < 0 && UI.canChildScrollUp(bottomRecyclerView)) {
                                bottomRecyclerView?.scrollBy(0, distanceY.toInt())
                            } else {
                                _scrollBy(_parentView, distanceY.toInt())
                            }
                        }
                        return true
                    }
                }

                return false
            }
        })
    }

    fun _velocity(velocity: Int): Int {
        return if (velocity > 0) {
            clamp(velocity, _minFlingVelocity, _maxFlingVelocity)
        } else {
            clamp(velocity, -_maxFlingVelocity, -_minFlingVelocity)
        }
    }

    fun _onTouchDown() {
        _handleTouch = true
        _isFirstScroll = true
        _overScroller.abortAnimation()
        _nestedScrollView?.apply {

            //L.e("停止滚动...${this.simpleName()}")

            this.stopNestedScroll()
            if (this is RecyclerView) {
                this.stopScroll()
            }
            _nestedScrollView = null
        }

        _bottomFlingRecyclerView?.stopNestedScroll()
        _bottomFlingRecyclerView?.stopScroll()
        _bottomFlingRecyclerView = null

        _topFlingRecyclerView?.stopNestedScroll()
        _topFlingRecyclerView?.stopScroll()
        _topFlingRecyclerView = null
    }

    var _handleTouch = true
    var _isFirstScroll = true

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: View,
        ev: MotionEvent
    ): Boolean {
        if (ev.isDown()) {
            _onTouchDown()
        } else if (ev.isUp()) {
            parent.requestDisallowInterceptTouchEvent(false)
        }
        if (_handleTouch) {
            _gestureDetector.onTouchEvent(ev)
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        var result = false
        if (_handleTouch) {
            result = _gestureDetector.onTouchEvent(ev)
        }
        if (ev.isUp()) {
            parent.requestDisallowInterceptTouchEvent(false)
        } else if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            _onTouchDown()
        }
        return super.onTouchEvent(parent, child, ev) || result
    }

    //</editor-fold desc="手势滚动监听">

    //<editor-fold desc="内嵌滚动相关">

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        super.onStartNestedScroll(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type
        )
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        _onNestedViewScroll(coordinatorLayout, target, dy, consumed)
    }

    /**内嵌滚动的View, 开始滚动了*/
    fun _onNestedViewScroll(
        parent: ViewGroup?,
        scrollView: View?,
        dy: Int,
        consumed: IntArray
    ) {
        if (dy > 0) {
            //手指向上滚动
            if (scrollView == topRecyclerView) {
                if (!UI.canChildScrollDown(scrollView)) {
                    if (!_scrollBy(parent, dy)) {
                        consumed[1] = dy
                    }
                }
            } else if (scrollView == bottomRecyclerView) {
                if (!_scrollBy(parent, dy)) {
                    consumed[1] = dy
                }
            }
        } else if (dy < 0) {
            //手指向下滚动
            if (scrollView == topRecyclerView) {
                if (!_scrollBy(parent, dy)) {
                    consumed[1] = dy
                }
            } else if (scrollView == bottomRecyclerView) {
                if (!UI.canChildScrollUp(scrollView)) {
                    if (!_scrollBy(parent, dy)) {
                        consumed[1] = dy
                    }
                }
            }
        }
    }

    var _topFlingRecyclerView: RecyclerView? = null
    var _bottomFlingRecyclerView: RecyclerView? = null

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type
        )

        //child.offsetTop(-dyUnconsumed, -child.measuredHeight, 0)

        //fling 传递
        if (dyUnconsumed > 0 && _bottomFlingRecyclerView == null && isStickClose()) {
            val velocityY = topRecyclerView?.getLastVelocity()?.toInt() ?: 0
            //L.e("lastVelocity1:.....${topRecyclerView?.simpleHash()} $velocityY")

            if (velocityY != 0) {
                bottomRecyclerView?.apply {
                    _bottomFlingRecyclerView = this
                    fling(0, (velocityY * 0.9).toInt())
                }
            }
        } else if (dyUnconsumed < 0 && _topFlingRecyclerView == null) {
            if (target == bottomRecyclerView) {
                val velocityY = (target as? RecyclerView)?.getLastVelocity()?.toInt() ?: 0
                //L.e("lastVelocity2:.....${target.simpleHash()} $velocityY")

                if (velocityY != 0) {
                    topRecyclerView?.apply {
                        _topFlingRecyclerView = this
                        fling(0, (-velocityY * 0.9).toInt())
                    }
                }
            }
        }
    }

    //</editor-fold desc="内嵌滚动相关">
}