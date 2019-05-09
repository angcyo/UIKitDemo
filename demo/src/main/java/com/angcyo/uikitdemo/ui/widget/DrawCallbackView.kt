package com.angcyo.uikitdemo.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/05/09
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class DrawCallbackView(
    context: Context,
    attributeSet: AttributeSet? = null
) :
    View(context, attributeSet) {

    var drawCallback: (canvas: Canvas) -> Unit = {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawCallback.invoke(canvas)
    }

}