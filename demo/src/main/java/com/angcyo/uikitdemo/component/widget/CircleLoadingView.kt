package com.angcyo.uikitdemo.component.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.angcyo.uiview.less.kotlin.dp

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/04/17
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class CircleLoadingView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    val paint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
        }
    }

    /**
     * 宽度
     * */
    var width = 3 * dp
        set(value) {
            field = value
            paint.strokeWidth = value

            resetRect()
        }

    val drawRectF: RectF by lazy {
        RectF()
    }

    /**
     * 每帧增加的速率
     * */
    var rotateStep = 4

    /**
     * 颜色
     * */
    var loadingColor: Int = Color.parseColor("#3965D6")
        set(value) {
            field = value
            singleShader = createSingleShader(value)
            multiShader = createMultiShader(value)
        }

    /**
     * 单条时候的颜色渐变
     * */
    var singleShader = createSingleShader(loadingColor)


    /**
     * 双条的时候颜色渐变
     * */
    var multiShader = createMultiShader(loadingColor)

    /**
     * 双龙戏珠模式
     * */
    var isMultiMode = false

    /**
     * 是否绘制背景, 背景是一个圆形色块
     * */
    var drawLoadingBackground = false

    /**
     * 背景颜色
     * */
    var loadingBackgroundColor = Color.WHITE


    init {
        paint.strokeWidth = width
    }

    private fun createSingleShader(color: Int): SweepGradient {
        return SweepGradient(
            0f, 0f,
            intArrayOf(Color.TRANSPARENT, color),
            floatArrayOf(0.2f, 1f)
        )
    }

    private fun createMultiShader(color: Int): SweepGradient {
        return SweepGradient(
            0f, 0f,
            intArrayOf(Color.TRANSPARENT, color),
            floatArrayOf(0.6f, 1f)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        resetRect()
    }

    private fun resetRect() {
        drawRectF.set(
            paddingLeft.toFloat(), paddingTop.toFloat(),
            (measuredWidth - paddingRight).toFloat(), (measuredHeight - paddingBottom).toFloat()
        )
        tempRectF.set(drawRectF)
        tempRectF.inset(width / 2, width / 2)

        val size = Math.min(tempRectF.width(), tempRectF.height())
        drawTempRectF.set(
            -size / 2, -size / 2,
            size / 2, size / 2
        )
    }

    val tempRectF: RectF by lazy {
        RectF()
    }

    val drawTempRectF: RectF by lazy {
        RectF()
    }

    var rotateDegrees = 0f
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.translate(tempRectF.centerX(), tempRectF.centerY())

        //圆形背景
        if (drawLoadingBackground) {
            paint.shader = null
            paint.style = Paint.Style.FILL
            paint.color = loadingBackgroundColor
            canvas.drawCircle(0f, 0f, drawTempRectF.width() / 2 + width / 2 + 1, paint)
        }

        //旋转的角度
        canvas.rotate(rotateDegrees)
        rotateDegrees += rotateStep
        if (rotateDegrees > 360) {
            rotateDegrees = -1f
        }

        paint.style = Paint.Style.STROKE
        if (isMultiMode) {
            //双线模式
            paint.shader = multiShader
            canvas.drawArc(drawTempRectF, 0f, 360f, false, paint)
            canvas.rotate(180f)
            canvas.drawArc(drawTempRectF, 0f, 360f, false, paint)
        } else {
            //单线模式
            paint.shader = singleShader
            canvas.drawArc(drawTempRectF, 0f, 360f, false, paint)
        }

        canvas.restore()

        if (visibility == VISIBLE) {
            postInvalidate()
        }
    }

}