package com.angcyo.uikitdemo.ui.behavior

import android.animation.ValueAnimator
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.utils.UI
import com.angcyo.uiview.less.widget.behavior.BaseDependsBehavior
import kotlin.math.min

/**
 * 刷新头部 行为
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/11/15
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class RefreshHeaderBehavior : BaseDependsBehavior<View>() {

    companion object {
        //默认状态
        const val REFRESH_NORMAL = 0
        //拖拽中
        const val REFRESH_DRAG = 1
        //刷新中
        const val REFRESH_ING = 2
    }

    /**当前刷新的状态*/
    var refreshStatus = REFRESH_NORMAL
        set(value) {
            val old = field
            field = value
            if (old != value) {
                if (value == REFRESH_ING) {
                    //通知刷新中...
                    L.i("刷新回调")
                } else if (value == REFRESH_NORMAL) {
                    onFinishSpinner()
                }
            }
        }

    /**
     * 核心控制布局的回调
     * */
    var onRefreshCallback = OnRefreshCallback(this)

    /**
     * behavior附着在的view
     * */
    var child: View? = null

    /**内嵌滚动的View*/
    var scrollTargetView: View? = null

    /**
     * 内容布局, 并非一定是 内嵌滚动布局
     * */
    val content: View?
        get() = dependsLayout

    init {
        showLog = true
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return super.layoutDependsOn(
            parent,
            child,
            dependency
        ) || dependency.layoutParams.coordinatorParams()?.behavior?.run {
            if (this is RefreshContentBehavior) {
                this@RefreshHeaderBehavior.dependsLayout = dependency
                this@RefreshHeaderBehavior.child = child
                true
            } else {
                false
            }
        } ?: false
    }

    var _isFirstLayout = true

    override fun onLayoutChildAfter(parent: CoordinatorLayout, child: View, layoutDirection: Int) {
        super.onLayoutChildAfter(parent, child, layoutDirection)
        if (_isFirstLayout) {
            _currentChildOffsetTop = -child.measuredHeight
        }
        _isFirstLayout = false
        child.offsetTopTo(_currentChildOffsetTop)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        super.onDependentViewChanged(parent, child, dependency)
        child.offsetTopTo(_currentChildOffsetTop)
        return false
    }

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: View,
        ev: MotionEvent
    ): Boolean {
        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            (scrollTargetView as? RecyclerView)?.stopScroll()
            animator?.cancel()
            animator = null
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }

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

    override fun onNestedScrollAccepted(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ) {
        super.onNestedScrollAccepted(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type
        )
        scrollTargetView = target
    }

    //顶部覆盖滚动的距离
    var _overScrollTop = 0

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

        if (dy > 0) {
            //向上滚动
            if (_overScrollTop < 0) {
                consumed[1] = min(_overScrollTop.abs(), dy)
                _overScrollTop += consumed[1]
                onMoveSpinner(consumed[1])
            }
        } else if (dy < 0) {
            //向下滚动
            if (!UI.canChildScrollUp(target)) {
                (scrollTargetView as? RecyclerView)?.stopScroll()
                //顶部没有可滚动空间
                consumed[1] = dy
                L.e("$_overScrollTop $dy ${child.measuredHeight}")
                _overScrollTop += consumed[1]
                onMoveSpinner(consumed[1])
            }
        }
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        type: Int
    ) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
        onFinishSpinner()
    }

    var _currentChildOffsetTop = 0

    /**移动[Spinner]*/
    open fun onMoveSpinner(dy: Int) {
        L.e("move $dy")
        if (refreshStatus == REFRESH_NORMAL) {
            refreshStatus = REFRESH_DRAG
        }

        child?.offsetTop(-dy)
        _currentChildOffsetTop = child?.top ?: _currentChildOffsetTop
        content?.offsetTopTo(child?.bottom ?: 0)

        onRefreshCallback.onMoveSpinner(dy)
    }

    var animator: ValueAnimator? = null

    open fun onFinishSpinner() {
        animator?.cancel()

        var endOverScrollTop = 0

        when {
            refreshStatus == REFRESH_NORMAL -> {
                //刷新完成
            }
            child!!.bottom >= child!!.measuredHeight -> {
                //触发刷新
                endOverScrollTop = -child!!.measuredHeight

                refreshStatus = REFRESH_ING
            }
            else -> {
                //回到默认
            }
        }

        if (_overScrollTop == endOverScrollTop) {
            return
        }

        animator = ValueAnimator.ofInt(_overScrollTop, endOverScrollTop).apply {
            addUpdateListener {
                val value = it.animatedValue as Int
                val dy = value - _overScrollTop
                _overScrollTop = value

                onMoveSpinner(dy)
            }
            duration = 300
            interpolator = DecelerateInterpolator()
            start()
        }
    }
}

open class OnRefreshCallback(val behavior: RefreshHeaderBehavior) {

    /**用来移动[content]或者[child]*/
    open fun onMoveSpinner(dy: Int) {
        //behavior.content!!.offsetTopTo(-behavior._overScrollTop)

        val textView = behavior.child?.find<TextView>(R.id.text_view)
        val loadView = behavior.child?.find<View>(R.id.arc_loading_view)
        if (behavior.refreshStatus == RefreshHeaderBehavior.REFRESH_ING) {
            textView?.text = "正在刷新"
            loadView?.visible()
        } else {
            loadView?.gone()

            val top = behavior._overScrollTop.abs()
            val childHeight = behavior.child!!.measuredHeight
            if (top >= childHeight) {
                textView?.text = "释放刷新"
            } else {
                textView?.text = "下拉刷新"
            }
        }

        behavior.child?.find<View>(R.id.button)?.setOnClickListener {
            behavior.refreshStatus = RefreshHeaderBehavior.REFRESH_NORMAL
        }
    }
}

open class OnRefreshCallback2(behavior: RefreshHeaderBehavior) : OnRefreshCallback(behavior) {

    override fun onMoveSpinner(dy: Int) {
//        if (dy < 0) {
//            (behavior.scrollTargetView as? RecyclerView)?.stopScroll()
//        }
//
//        L.e("offset $dy")
//
//        behavior.child!!.offsetTopTo(-dy)
//        _childLastTop = behavior.child!!.top
//
//        val textView = behavior.child?.find<TextView>(R.id.text_view)
//        val loadView = behavior.child?.find<View>(R.id.arc_loading_view)
//        if (behavior.refreshStatus == RefreshHeaderBehavior.REFRESH_ING) {
//            textView?.text = "正在刷新"
//            loadView?.visible()
//        } else {
//            loadView?.gone()
//
//            val top = _getScrollY()
//            val childHeight = behavior.child!!.measuredHeight
//            if (top >= childHeight) {
//                textView?.text = "释放刷新"
//            } else {
//                textView?.text = "下拉刷新"
//            }
//        }
//
//        behavior.child?.find<View>(R.id.button)?.setOnClickListener {
//            behavior.refreshStatus = RefreshHeaderBehavior.REFRESH_NORMAL
//        }
    }
}