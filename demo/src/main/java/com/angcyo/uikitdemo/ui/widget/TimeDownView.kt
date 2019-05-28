package com.angcyo.uikitdemo.ui.widget

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/03/02
 */
class TimeDownView(context: Context, attributeSet: AttributeSet? = null) :
    AppCompatTextView(context, attributeSet) {

    companion object {
        const val MAX_COUNT = 30
    }

    var count = 0

    private val runnable = Runnable {
        if (count == 0) {
            onEnd?.run()
        } else {
            count--
            text = "$count"
            next()
        }
    }

    var onEnd: Runnable? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        stopDown()
    }

    fun startDown() {
        count = MAX_COUNT + 1

        runnable.run()
    }

    fun stopDown() {
        removeCallbacks(runnable)
    }

    protected fun next() {
        postDelayed(runnable, 1000L)
    }

}