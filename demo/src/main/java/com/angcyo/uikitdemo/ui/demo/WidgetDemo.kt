package com.angcyo.uikitdemo.ui.demo

import android.graphics.Color
import android.support.design.circularreveal.CircularRevealFrameLayout
import android.support.design.circularreveal.CircularRevealWidget
import android.text.SpannableStringBuilder
import android.text.Spanned
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.component.MarginTextSpan
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.base.dialog.HttpConfigDialog
import com.angcyo.uiview.less.kotlin.dp
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.angcyo.uiview.less.resources.ResUtil
import com.angcyo.uiview.less.utils.RSpan
import com.angcyo.uiview.less.utils.RUtils
import com.angcyo.uiview.less.utils.SpanUtils
import com.angcyo.uiview.less.utils.Tip
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

                holder.click(R.id.button1) {
                    HttpConfigDialog.show(mAttachContext, "base_url") {

                    }
                }

                holder.click(R.id.button2) {
                    fun getMonthEndTime(): String {
                        val cal = Calendar.getInstance()

                        val year = cal.get(Calendar.YEAR)//2018
                        val month = cal.get(Calendar.MONTH)//0-11月

                        for (i in 0..11) {
                            cal.set(year, i, 0)
                            val day = cal.get(Calendar.DAY_OF_MONTH)//1-31天
                            L.i("1: ${i + 1}月,共$day 天")
                        }

                        for (i in 0..11) {
                            cal.set(year, i, 1)
                            cal.roll(Calendar.DAY_OF_MONTH, -1)
                            val day = cal.get(Calendar.DAY_OF_MONTH)//1-31天
                            L.i("2: ${i + 1}月,共$day 天")
                        }

                        val day = cal.get(Calendar.DAY_OF_MONTH)//1-31天
                        return "$year-$month-$day 23:59:59"
                    }

                    Tip.tip(getMonthEndTime())
                }

                val revealLayout: CircularRevealFrameLayout = holder.v(R.id.reveal_layout)
                revealLayout.post {
                    revealLayout.buildCircularRevealCache()
                    //revealLayout.circularRevealScrimColor = Color.RED

                    revealLayout.revealInfo =
                        CircularRevealWidget.RevealInfo(
                            (RUtils.getScreenWidth() / 2).toFloat(),
                            (RUtils.getScreenHeight() / 2).toFloat(),
                            10f
                        )

                    revealLayout.destroyCircularRevealCache()
                }

                holder.tv(R.id.time_view).text = SpannableStringBuilder("22:22:22").apply {
                    setSpan(
                        RSpan.TextSpan().apply {
                            setOffsetY((-2 * dp).toInt())
                        },
                        2,
                        3,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                holder.tv(R.id.time_view2).text = SpannableStringBuilder("22:22:22").apply {
                    setSpan(MarginTextSpan(-2 * dp), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        })
    }

}