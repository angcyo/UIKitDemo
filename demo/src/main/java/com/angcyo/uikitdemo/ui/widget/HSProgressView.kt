package com.angcyo.uikitdemo.ui.widget

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.angcyo.uikitdemo.ui.widget.draw.HSProgressDraw
import com.angcyo.uiview.less.draw.view.BaseDrawView

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/11
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class HSProgressView(context: Context, attributeSet: AttributeSet? = null) :
    BaseDrawView<HSProgressDraw>(context, attributeSet) {

    private var valueAnimator: ValueAnimator? = null

    /**开始进度动画*/
    fun startAnimator() {
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt(1, 100).apply {
                interpolator = LinearInterpolator()
                duration = 1300
                repeatMode = ValueAnimator.RESTART
                repeatCount = ValueAnimator.INFINITE
                addUpdateListener { animation ->
                    getBaseDraw().setProgress(animation.animatedValue as Int)
                    postInvalidate()
                }
                start()
            }
        }
    }

    /**停止进度动画*/
    fun stopAnimator() {
        valueAnimator?.cancel()
        valueAnimator = null
        getBaseDraw().setProgress(0)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility != View.VISIBLE) {
            stopAnimator()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //startAnimator()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimator()
    }
}