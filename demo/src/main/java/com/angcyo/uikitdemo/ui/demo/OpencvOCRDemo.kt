package com.angcyo.uikitdemo.ui.demo

import android.graphics.BitmapFactory
import android.util.LayoutDirection
import android.view.Gravity.*
import android.view.View
import android.widget.TextView
import com.angcyo.http.Rx
import com.angcyo.lib.L
import com.angcyo.opencv.CardOcrScanFragment
import com.angcyo.rcode.CodeScanFragment
import com.angcyo.rcode.scan.FrameDataDecode
import com.angcyo.tesstwo.NetOcr
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.iview.ChoiceIView
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.google.gson.JsonObject
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/05
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class OpencvOCRDemo : AppBaseItemFragment() {

    val netOcr = NetOcr()

    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        dslCreateItem {
            singleItemLayoutId = R.layout.demo_open_cv

            singleItemBind = { holder, posInData, singItem ->

                //open cv ocr 识别身份证号码
                holder.click(R.id.ocr_button) {
                    CardOcrScanFragment.show(parentFragmentManager()) {
                        holder.tv(R.id.text_view).text = "$it"

                        holder.imgV(R.id.image_view).setImageBitmap(it.base64bitmap.toBitmap())
                    }
                }

                //百度云 ocr 文字识别

                holder.click(R.id.baidu_button) {
                    //netOcr.ocrBaidu()
                    FrameDataDecode.iFrameDataDecode = FrameDataDecode.IFrameDataDecode { data ->
                        //                        netOcr.ocrBaiduBasic(data) {
//                            it.fromJson(JsonObject::class.java)?.getArray("words_result")?.forEach {
//                                it.getString("words")?.replace(" ","")?.apply {
//                                    if (this.length >= 9 && this.pattern("^[a-zA-Z0-9]+\$")) {
//                                        L.w("识别到:$this")
//                                    }
//                                }
//                            }
//                            null
//                        }

                        netOcr.ocrAliyun(data) {
                            it.fromJson(JsonObject::class.java)?.getArray("prism_wordsInfo")
                                ?.forEach {
                                    it.getString("word")?.replace(" ", "")?.apply {
                                        L.i("匹配:$this")

                                        if (this.length >= 9 && this.pattern("^[a-zA-Z0-9]+\$")) {
                                            L.w("识别到:$this")
                                        }
                                    }
                                }
                            null
                        }

                        Rx.onMain {
                            holder.imgV(R.id.image_view)
                                .setImageBitmap(
                                    BitmapFactory.decodeByteArray(
                                        data,
                                        0,
                                        data.size
                                    ).rotate()
                                )
                        }
                    }

                    show(CodeScanFragment().apply {
                        onScanResult = {
                            holder.tv(R.id.result_text_view).text = it
                            true
                        }
                    })
                }

                ChoiceIView.get(holder.group(R.id.flow_layout)).apply {
                    onChoiceSelector = object : ChoiceIView.OnChoiceSelector() {
                        override fun onChoiceSelector(itemView: View, position: Int) {
                            super.onChoiceSelector(itemView, position)
                            (itemView as TextView).apply {
                                holder.tv(R.id.log).text = buildString {
                                    append("Left:")
                                    append(LEFT)
                                    append(' ')
                                    append("Right:")
                                    append(RIGHT)
                                    append(' ')
                                    append("Top:")
                                    append(TOP)
                                    append(' ')
                                    append("Bottom:")
                                    append(BOTTOM)
                                    append(' ')
                                    append("cv:")
                                    append(CENTER_VERTICAL)
                                    append(' ')
                                    append("ch:")
                                    append(CENTER_HORIZONTAL)
                                    append(' ')
                                    append("c:")
                                    append(CENTER)
                                    append(' ')

                                    appendln()
                                    append(gravity)
                                    appendln()
                                    append(gravityToString(gravity))

                                    appendln()
                                    val absoluteGravity =
                                        getAbsoluteGravity(gravity, LayoutDirection.LTR)
                                    append(absoluteGravity)
                                    appendln()
                                    append(gravityToString(absoluteGravity))

                                    appendln()
                                    absoluteGravity.gravityFlag().forEachIndexed { index, byte ->
                                        append(
                                            when (index) {
                                                1 -> "T"
                                                2 -> "R"
                                                3 -> "B"
                                                4 -> "C"
                                                else -> "L"
                                            }
                                        )
                                        append(":")
                                        append(byte)
                                        append(" ")
                                    }
                                }
                            }
                        }
                    }
                    doIt()
                }
            }
        }
    }

    fun gravityToString(gravity: Int): String {
        val result = StringBuilder()
        if (gravity and FILL == FILL) {
            result.append("FILL").append(' ')
        } else {
            if (gravity and FILL_VERTICAL == FILL_VERTICAL) {
                result.append("FILL_VERTICAL").append(' ')
            } else {
                if (gravity and TOP == TOP) {
                    result.append("TOP").append(' ')
                }
                if (gravity and BOTTOM == BOTTOM) {
                    result.append("BOTTOM").append(' ')
                }
            }
            if (gravity and FILL_HORIZONTAL == FILL_HORIZONTAL) {
                result.append("FILL_HORIZONTAL").append(' ')
            } else {
                if (gravity and START == START) {
                    result.append("START").append(' ')
                } else if (gravity and LEFT == LEFT) {
                    result.append("LEFT").append(' ')
                }
                if (gravity and END == END) {
                    result.append("END").append(' ')
                } else if (gravity and RIGHT == RIGHT) {
                    result.append("RIGHT").append(' ')
                }
            }
        }
        if (gravity and CENTER == CENTER) {
            result.append("CENTER").append(' ')
        } else {
            if (gravity and CENTER_VERTICAL == CENTER_VERTICAL) {
                result.append("CENTER_VERTICAL").append(' ')
            }
            if (gravity and CENTER_HORIZONTAL == CENTER_HORIZONTAL) {
                result.append("CENTER_HORIZONTAL").append(' ')
            }
        }
        if (result.length == 0) {
            result.append("NO GRAVITY").append(' ')
        }
        if (gravity and DISPLAY_CLIP_VERTICAL == DISPLAY_CLIP_VERTICAL) {
            result.append("DISPLAY_CLIP_VERTICAL").append(' ')
        }
        if (gravity and DISPLAY_CLIP_HORIZONTAL == DISPLAY_CLIP_HORIZONTAL) {
            result.append("DISPLAY_CLIP_HORIZONTAL").append(' ')
        }
        result.deleteCharAt(result.length - 1)
        return result.toString()
    }

}