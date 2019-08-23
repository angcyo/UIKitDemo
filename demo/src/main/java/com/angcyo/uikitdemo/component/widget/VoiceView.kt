package com.angcyo.uikitdemo.component.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.angcyo.uiview.less.kotlin.dp

/**
 * 语音播放提示控件
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/04/17
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class VoiceView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {
    val paint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
        }
    }

    var color: Int = Color.parseColor("#020202")
        set(value) {
            field = value
            paint.color = value
        }

    /**
     * 线的厚度
     * */
    var width = 2 * dp

    /**线之间的间隙*/
    var space = 1 * dp

    /**每增加一根线, 起始角度偏移多少度*/
    var stepAngle = 3

    /**每增加一根线, 高度增加多少*/
    var stepHeight = 4

    /**线开始的角度偏移*/
    var startAngle = 40f

    /**线的数量*/
    var count = 2

    var drawCount = -1

    init {
        paint.color = color
    }

    val tempRectF: RectF by lazy {
        RectF()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (widthMode != MeasureSpec.EXACTLY) {
            //wrap_content unspecified
            //widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(measureDrawWidth(widthSize, widthMode), View.MeasureSpec.EXACTLY);
            widthSize = (paddingLeft + paddingRight + width * 2 * (count + 2) + space * (count - 1)).toInt()
        }

        if (heightMode != MeasureSpec.EXACTLY) {
            //wrap_content unspecified
            //heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(measureDrawHeight(heightSize, heightMode), View.MeasureSpec.EXACTLY);
            heightSize = (paddingTop + paddingBottom + width * count * 4).toInt()
        }

        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val drawHeight = measuredHeight - paddingTop - paddingBottom
        val cr = width
        val cx = paddingLeft + cr
        val cy = paddingTop + drawHeight / 2

        //最小的圆点
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 0f
        canvas.drawCircle(cx, cy.toFloat(), cr, paint)

        //扇形
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = width

        for (i in 1..(if (drawCount >= 0) drawCount else count)) {
            tempRectF.set(
                cx - cr - space * i - width / 2 * i - width * (i - 1),
                cy - cr - stepHeight * dp * i,
                cx + cr + space * i + width / 2 * i + width * (i - 1),
                cy + cr + stepHeight * dp * i
            )
            canvas.drawArc(tempRectF, -startAngle - stepAngle * i, startAngle * 2 + stepAngle * 2 * i, false, paint)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //play()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility != VISIBLE) {
            stop()
        }
    }

    val playRunnable: Runnable by lazy {
        Runnable {

            if (drawCount < 0 || drawCount >= count) {
                drawCount = -1
            }

            drawCount++

            postInvalidateOnAnimation()
            postDelayed(playRunnable, 300)
        }
    }

    /**
     * 开始播放动画
     * */
    fun play() {
        stop()
        post(playRunnable)
    }

    /**
     * 停止动画
     * */
    fun stop() {
        removeCallbacks(playRunnable)
        drawCount = -1
        postInvalidateOnAnimation()
    }
}