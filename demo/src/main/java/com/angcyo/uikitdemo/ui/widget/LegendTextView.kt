package com.angcyo.uikitdemo.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet
import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.kotlin.dpi

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/05/07
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class LegendTextView(context: Context, attributeSet: AttributeSet? = null) :
    AppCompatTextView(context, attributeSet) {

    companion object {
        //正方形
        const val LEGEND_STYLE_SQUARE = 1
        //圆形
        const val LEGEND_STYLE_CIRCLE = 2
    }

    /**样式*/
    var legendStyle = LEGEND_STYLE_SQUARE

    /**宽高, 正方形/圆形, 会取最大值*/
    var legendWidth = 0
    var legendHeight = 0

    /**和文本的偏移距离*/
    var legendOffset = 3 * dpi
    /*圆角大小*/
    var legendRound = 2 * dpi
    /*填充颜色*/
    var legendColor = 0

    var drawBorderLine = false
    var borderLineColor = Color.parseColor("#D5D5D5")
    var borderLineWidth = 1 * dpi

    init {
        val array = context.obtainStyledAttributes(attributeSet, R.styleable.LegendTextView)
        legendStyle = array.getInt(R.styleable.LegendTextView_r_legend_style, legendStyle)
        legendWidth = array.getDimensionPixelOffset(R.styleable.LegendTextView_r_legend_width, legendWidth)
        legendHeight = array.getDimensionPixelOffset(R.styleable.LegendTextView_r_legend_height, legendHeight)
        legendOffset = array.getDimensionPixelOffset(R.styleable.LegendTextView_r_legend_offset, legendOffset)
        legendRound = array.getDimensionPixelOffset(R.styleable.LegendTextView_r_legend_round, legendRound)
        borderLineWidth =
            array.getDimensionPixelOffset(R.styleable.LegendTextView_r_legend_border_width, borderLineWidth)
        legendColor = array.getInt(R.styleable.LegendTextView_r_legend_color, legendColor)
        borderLineColor = array.getInt(R.styleable.LegendTextView_r_legend_border_color, borderLineColor)
        legendColor = array.getInt(R.styleable.LegendTextView_r_legend_border_color, legendColor)
        drawBorderLine = array.getBoolean(R.styleable.LegendTextView_r_legend_draw_border, drawBorderLine)
        array.recycle()
    }

    var legendRect = RectF()
        get() {
            var w = legendWidth
            var h = legendHeight
            if (legendStyle == LEGEND_STYLE_SQUARE || legendStyle == LEGEND_STYLE_CIRCLE) {
                w = Math.max(legendWidth, legendHeight)
                h = w
            }

            val top = paddingTop + (measuredHeight - paddingTop - paddingBottom) / 2 - h / 2
            val bottom = top + h

            //垂直居中展示
            field.set(
                paddingLeft.toFloat(), top.toFloat(), (paddingLeft + w).toFloat(),
                bottom.toFloat()
            )
            return field
        }

    val paint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension((measuredWidth + legendRect.width() + legendOffset).toInt(), measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {

        canvas.save()
        canvas.translate(legendRect.width() + legendOffset, 0f)
        super.onDraw(canvas)
        canvas.restore()

        paint.color = legendColor
        paint.style = Paint.Style.FILL
        canvas.drawRoundRect(legendRect, legendRound.toFloat(), legendRound.toFloat(), paint)

        if (drawBorderLine) {
            paint.color = borderLineColor
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = borderLineWidth.toFloat()
            canvas.translate(borderLineWidth.toFloat() / 2, 0f)
            canvas.drawRoundRect(legendRect, legendRound.toFloat(), legendRound.toFloat(), paint)
        }
    }
}