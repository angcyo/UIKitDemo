package com.angcyo.uikitdemo.ui.item

import android.graphics.Color
import android.os.Bundle
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.ui.item.DslColorItem
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import kotlin.random.Random.Default.nextInt

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/11/25
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class ColorFragment : AppBaseDslRecyclerFragment() {

    var text = ""
    var color = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        text = arguments?.getString("text") ?: "默认文本"
        color = arguments?.getInt("color") ?: color
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        renderDslAdapter {
            DslColorItem()() {
                itemColor = color
                itemText = text
            }
        }
    }
}

fun getColorFragmentList(count: Int): List<ColorFragment> {
    val result = mutableListOf<ColorFragment>()
    for (i in 0 until count) {
        ColorFragment().apply {
            arguments = Bundle().apply {
                putString("text", "Fragment $i")
                putInt("color", randomColor())
            }
            result.add(this)
        }
    }
    return result
}

fun randomColor(): Int = Color.rgb(nextInt(122, 222), nextInt(122, 222), nextInt(122, 222))