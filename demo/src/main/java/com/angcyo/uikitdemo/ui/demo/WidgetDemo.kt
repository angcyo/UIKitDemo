package com.angcyo.uikitdemo.ui.demo

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.angcyo.uiview.less.resources.ResUtil
import com.angcyo.uiview.less.utils.RSpan
import com.angcyo.uiview.less.utils.SpanUtils
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/02/23
 */
class WidgetDemo : AppBaseItemFragment() {
    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        singleItems.add(object : SingleItem() {
            override fun getItemLayoutId(): Int {
                return R.layout.demo_widget
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                holder.tv(R.id.text1).text =
                    RSpan.get().append("测试文本")
                        .appendImage(R.drawable.ic_logo)
                        .append("测试文本")
                        .setForegroundColor(Color.RED)
                        .appendImage(R.drawable.ic_logo, SpanUtils.ALIGN_CENTER)
                        .create()

                holder.tv(R.id.text2).text = holder.tv(R.id.text1).text
                holder.tv(R.id.text3).text = SpannableStringBuilder("测试文本\n测试文本").apply {
                    setSpan(RSpan.TextSpan(), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    setSpan(RSpan.TextSpan(), 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }

                holder.tv(R.id.text4).text = RSpan.get()
                    .append("测试文本")
                    .setMinSize(ResUtil.dpToPx(100F).toInt())
                    .appendImage(R.drawable.ic_logo)
                    .append("测试文本")
                    .setForegroundColor(Color.RED)
                    .setMinSize(ResUtil.dpToPx(5700F).toInt())
                    .setBackgroundColor(Color.YELLOW)
                    .appendImage(R.drawable.ic_logo, SpanUtils.ALIGN_CENTER)
                    .create()
            }
        })
    }

}