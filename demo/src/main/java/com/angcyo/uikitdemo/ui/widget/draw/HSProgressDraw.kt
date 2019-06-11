package com.angcyo.uikitdemo.ui.widget.draw

import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.angcyo.uiview.less.draw.RSectionDraw
import com.angcyo.uiview.less.kotlin.alpha
import com.angcyo.uiview.less.kotlin.set

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/11
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class HSProgressDraw(view: View) : RSectionDraw(view) {
    init {
        setSections(floatArrayOf(0.5f, 0.5f))

        setInterpolatorList(DecelerateInterpolator(), DecelerateInterpolator())
    }

    var progressColor = Color.WHITE

    override fun initAttribute(attr: AttributeSet?) {
        super.initAttribute(attr)
    }

    override fun onDrawProgressSection(
        canvas: Canvas,
        index: Int,
        startProgress: Float,
        endProgress: Float,
        totalProgress: Float,
        sectionProgress: Float
    ) {
        mBasePaint.color = progressColor.alpha(255 * (1 - sectionProgress + 0.2f))

        val right = viewWidth - paddingRight
        if (index == 0) {
            mDrawRectF.set(
                paddingLeft,
                paddingTop,
                (right * sectionProgress).toInt(),
                viewHeight - paddingBottom
            )
        } else if (index == 1) {
            mDrawRectF.set(
                (right - viewDrawWidth * sectionProgress).toInt(),
                paddingTop,
                right,
                viewHeight - paddingBottom
            )
        }
        canvas.drawRect(mDrawRectF, mBasePaint)
    }

}