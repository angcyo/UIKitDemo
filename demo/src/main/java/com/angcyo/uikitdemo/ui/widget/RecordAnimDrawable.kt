package com.angcyo.uikitdemo.ui.widget

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/04/13
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
@Deprecated("--")
class RecordAnimDrawable : BaseDrawable() {

    override fun draw(canvas: Canvas) {
        super.draw(canvas)


        for (i in 0..4) {

        }

        val rect = Rect()
        paint.style = Paint.Style.STROKE
        paint.color = Color.RED

        paint.color = Color.YELLOW
        canvas.drawRect(bounds, paint)
    }
}