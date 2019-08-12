package com.angcyo.uikitdemo.ui.demo

import android.graphics.Color
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.angcyo.uiview.less.resources.ResUtil
import com.angcyo.uiview.less.utils.RSpan
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019-8-12
 */
class SpanDemo : AppBaseItemFragment() {
    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        singleItems.add(object : SingleItem() {
            override fun getItemLayoutId(): Int {
                return R.layout.demo_span
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                val spanKey = "span key"
                val spanValue =
                    "span value .span value .span value .span value .span value.88888888@qq.com\n"
                val spanValueNum = "888888888888888888888888888888888888888888888888@qq.com\n"

                holder.tv(R.id.text1).text = RSpan.get()
//                    .appendMainText(spanKey)
//                    .setMinSize(ResUtil.dpToPx(100F).toInt())
//                    .appendMainText(spanValue)
//                    .setForegroundColor(Color.RED)
//
//                    .appendMainText(spanKey)
//                    .setMinSize(ResUtil.dpToPx(100F).toInt())
//                    .appendMainText(spanValue)
//                    .setForegroundColor(Color.RED)
//
//                    .appendMainText(spanKey)
//                    .setMinSize(ResUtil.dpToPx(100F).toInt())
//                    .appendMainText(spanValue)
//                    .setForegroundColor(Color.RED)

//                    .appendMainText(spanKey)
//                    .setMinSize(ResUtil.dpToPx(100F).toInt())
//                    .appendMainText(spanValueNum)
//                    .setForegroundColor(Color.RED)
                    //.replaceText(spanValueNum)

                    .appendMainText(spanKey)
                    .setMinSize(ResUtil.dpToPx(100F).toInt())
                    .appendMainText(spanValueNum)
                    .setForegroundColor(Color.RED)
                    .replaceText(spanValueNum)
                    .setMaxSize(40 * dpi)
                    .setConfigTextSpan {
                        it.setMaxSize(100 * dpi)
                    }

//                    .appendImage(R.drawable.ic_logo)
//                    .append("测试文本")
//                    .setForegroundColor(Color.RED)
//                    .setMinSize(ResUtil.dpToPx(5700F).toInt())
//                    .setBackgroundColor(Color.YELLOW)
//                    .appendImage(R.drawable.ic_logo, SpanUtils.ALIGN_CENTER)
                    .create()
            }
        })
    }
}