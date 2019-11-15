package com.angcyo.uikitdemo.ui.behavior

import android.animation.ValueAnimator
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
import kotlin.math.max
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
                } else {
                    onRefreshCallback.onContentStopScroll()
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

    //最后一次布局时, child的top坐标
    var _childLastTop = 0

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        _childLastTop = child.top
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onLayoutChildAfter(parent: CoordinatorLayout, child: View, layoutDirection: Int) {
        super.onLayoutChildAfter(parent, child, layoutDirection)
        _followLayout()
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        super.onDependentViewChanged(parent, child, dependency)
        return _followLayout()
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
        onRefreshCallback.onContentStartScroll()
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

        dependsLayout?.let {
            onRefreshCallback.onContentPreScroll(target, dy, consumed)
        }
    }

    override fun onNestedFling(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        type: Int
    ) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
        onRefreshCallback.onContentStopScroll()
    }

    //跟随刷新内容控制的位置
    fun _followLayout(): Boolean {
        return dependsLayout?.run {
            onRefreshCallback.onContentViewChanged()
        } ?: false
    }
}

open class OnRefreshCallback(val behavior: RefreshHeaderBehavior) {

    var animator: ValueAnimator? = null

    /**
     * 当[content]布局位置发生改变时回调
     *
     * @return 如果改变了 [child]的大小, 则返回true
     * */
    open fun onContentViewChanged(): Boolean {
        behavior.apply {
            child!!.offsetTopTo(content!!.top - child!!.measuredHeight)
        }
        return false
    }

    /**当[content]即将开始滚动时回调*/
    open fun onContentStartScroll() {
        if (behavior.refreshStatus != RefreshHeaderBehavior.REFRESH_ING) {
            behavior.refreshStatus = RefreshHeaderBehavior.REFRESH_DRAG
        }
        animator?.cancel()
    }

    //Y轴滚动的距离
    open fun _getScrollY(): Int {
        return behavior.content!!.top
    }

    /**当[content]想要滚动时回调*/
    open fun onContentPreScroll(target: View, dy: Int, consumed: IntArray) {
        animator?.cancel()

        val scrollY = _getScrollY()

        if (dy > 0) {
            //向上滚动
            if (scrollY > 0) {
                consumed[1] = min(scrollY, dy)
                onMoveView(consumed[1])
            }
        } else if (dy < 0) {
            //向下滚动
            if (!UI.canChildScrollUp(target)) {
                //顶部没有可滚动空间
                val factor = scrollY * 1f / behavior.content!!.measuredHeight
                val f = max(0.0, 1.0 - 0.6 - factor)
                consumed[1] = dy
                onMoveView((dy * f).toInt())
            }
        }
    }

    /**当[content]停止滚动时回调*/
    open fun onContentStopScroll() {
        val scrollY = _getScrollY()

        if (scrollY == 0) {
            return
        }
        val childHeight = behavior.child!!.measuredHeight

        val endY =
            if (scrollY >= childHeight && behavior.refreshStatus != RefreshHeaderBehavior.REFRESH_NORMAL) {
                //达到了刷新阈值
                behavior.refreshStatus = RefreshHeaderBehavior.REFRESH_ING
                childHeight
            } else {
                //未达到阈值, 回退到默认状态
                0
            }

        animator = ValueAnimator.ofInt(scrollY, endY).apply {
            addUpdateListener {
                onMoveView(_getScrollY() - it.animatedValue as Int)
            }
            duration = 300
            interpolator = DecelerateInterpolator()
            start()
        }
    }

    /**用来移动[content]或者[child]*/
    open fun onMoveView(dy: Int) {
        if (dy < 0) {
            (behavior.scrollTargetView as? RecyclerView)?.stopScroll()
        }

        behavior.content!!.offsetTop(-dy)

        val textView = behavior.child?.find<TextView>(R.id.text_view)
        val loadView = behavior.child?.find<View>(R.id.arc_loading_view)
        if (behavior.refreshStatus == RefreshHeaderBehavior.REFRESH_ING) {
            textView?.text = "正在刷新"
            loadView?.visible()
        } else {
            loadView?.gone()

            val top = _getScrollY()
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

    override fun _getScrollY(): Int {
        return behavior.child!!.bottom
    }

    var _childLastTop = 0
    var isFirst = true

    override fun onContentViewChanged(): Boolean {

        behavior.apply {
            if (isFirst) {
                _childLastTop = -child!!.measuredHeight
            }
            child!!.offsetTopTo(_childLastTop)
        }

        isFirst = false
        return false
    }

    override fun onMoveView(dy: Int) {
        if (dy < 0) {
            (behavior.scrollTargetView as? RecyclerView)?.stopScroll()
        }

        L.e("offset $dy")

        behavior.child!!.offsetTopTo(-dy)
        _childLastTop = behavior.child!!.top

        val textView = behavior.child?.find<TextView>(R.id.text_view)
        val loadView = behavior.child?.find<View>(R.id.arc_loading_view)
        if (behavior.refreshStatus == RefreshHeaderBehavior.REFRESH_ING) {
            textView?.text = "正在刷新"
            loadView?.visible()
        } else {
            loadView?.gone()

            val top = _getScrollY()
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