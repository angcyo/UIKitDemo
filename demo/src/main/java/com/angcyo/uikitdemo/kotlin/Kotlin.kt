package com.angcyo.uikitdemo.kotlin

import android.graphics.RectF
import com.angcyo.http.Json
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.kotlin.getDrawable
import com.angcyo.uiview.less.kotlin.scale
import com.angcyo.uiview.less.kotlin.scaleFromCenter

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/03/22
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
object Kotlin {
    fun main() {
        L.e("Kotlin ......... ")
        val rectF1 = RectF(0f, 0f, 100f, 100f)
        val rectF2 = RectF(rectF1)

        L.i(rectF1.scale(0.5f, 0.5f))
        L.i(rectF2.scaleFromCenter(0.5f, 0.5f))

        val drawable = getDrawable(R.drawable.ic_pause)

        L.i("重复key测试:${Json.json().add("k", "abc").add("k", "123").build()}")

        L.e("Kotlin ......... end")
    }
}