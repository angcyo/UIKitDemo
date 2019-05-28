package com.angcyo.uikitdemo.ui.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/02/04
 */

class WriteView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {
    val path: Path by lazy {
        Path()
    }

    val paint: Paint by lazy {
        Paint().apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 2 * resources.displayMetrics.density
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> path.moveTo(event.x, event.y)
            MotionEvent.ACTION_MOVE -> path.lineTo(event.x, event.y)
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //canvas.drawColor(Color.GRAY)

        drawPath(canvas)
    }

    private fun drawPath(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    fun toBitmap(): Bitmap {
        val bitmap: Bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }
}