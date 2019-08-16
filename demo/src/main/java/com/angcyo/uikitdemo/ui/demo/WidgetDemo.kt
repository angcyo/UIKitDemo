package com.angcyo.uikitdemo.ui.demo

import android.graphics.*
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.view.View
import android.view.View.*
import android.view.ViewOutlineProvider
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.component.MarginTextSpan
import com.angcyo.uikitdemo.component.RScreenshotObserver
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.base.dialog.HttpConfigDialog
import com.angcyo.uiview.less.kotlin.anim
import com.angcyo.uiview.less.kotlin.dp
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.dslitem.dslItem
import com.angcyo.uiview.less.resources.ResUtil
import com.angcyo.uiview.less.utils.RSpan
import com.angcyo.uiview.less.utils.RUtils
import com.angcyo.uiview.less.utils.SpanUtils
import com.angcyo.uiview.less.utils.Tip
import com.google.android.material.circularreveal.CircularRevealFrameLayout
import com.google.android.material.circularreveal.CircularRevealWidget
import java.util.*
import kotlin.math.max
import kotlin.math.min


/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/02/23
 */
class WidgetDemo : AppBaseDslRecyclerFragment() {

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        val circleClipOutline = CircleClipOutline()
        renderDslAdapter {
            dslItem(R.layout.demo_widget_grayscale) {
                itemBind = { itemHolder, _, _ ->
                    //截图图片
                    if (!TextUtils.isEmpty(screenshotPath)) {
                        itemHolder.giv(R.id.image_view).url = screenshotPath
                    }

                    //变灰, 界面灰度处理
                    val matrix = ColorMatrix()
                    matrix.setSaturation(0f)//饱和度 0灰色 100过度彩色，50正常
                    val filter = ColorMatrixColorFilter(matrix)
                    val paint = Paint()
                    paint.colorFilter = filter

                    val decorView = activity?.window?.decorView

                    itemHolder.view(R.id.root_layout).apply {
                        L.e(
                            "LayoutType1: ${when (layerType) {
                                LAYER_TYPE_SOFTWARE -> "LAYER_TYPE_SOFTWARE"
                                LAYER_TYPE_HARDWARE -> "LAYER_TYPE_HARDWARE"
                                else -> "LAYER_TYPE_NONE"
                            }
                            }"
                        )

                        L.e(
                            "LayoutType2: ${when (decorView?.layerType) {
                                LAYER_TYPE_SOFTWARE -> "LAYER_TYPE_SOFTWARE"
                                LAYER_TYPE_HARDWARE -> "LAYER_TYPE_HARDWARE"
                                else -> "LAYER_TYPE_NONE"
                            }
                            }"
                        )
                        setLayerType(LAYER_TYPE_SOFTWARE, paint)
                    }

                    itemHolder.click(R.id.image_view) {
                        decorView?.setLayerType(LAYER_TYPE_SOFTWARE, paint)
                        itemHolder.view(R.id.root_layout).setLayerType(LAYER_TYPE_SOFTWARE, paint)
                    }

                    itemHolder.click(R.id.button2) {
                        itemHolder.view(R.id.root_layout).setLayerType(LAYER_TYPE_SOFTWARE, paint)
                    }

                    //恢复变灰
                    itemHolder.click(R.id.button1) {
                        decorView?.setLayerType(LAYER_TYPE_NONE, null)
                        itemHolder.view(R.id.root_layout).setLayerType(LAYER_TYPE_NONE, null)
                    }
                }
            }

            dslItem(R.layout.demo_widget_outline) {
                itemBind = { itemHolder, _, _ ->
                    itemHolder.view(R.id.wrap_layout).apply {
                        outlineProvider = circleClipOutline
                        anim(duration = 3000) {
                            L.i(it)
                            circleClipOutline.clipProgress = it
                            invalidateOutline()
                        }
                    }
                }
            }

            dslItem(R.layout.demo_widget) {
                itemBind = { itemHolder, _, _ ->
                    itemHolder.tv(R.id.text1).text =
                        RSpan.get().append("测试文本")
                            .appendImage(R.drawable.ic_logo)
                            .append("测试文本")
                            .setForegroundColor(Color.RED)
                            .appendImage(
                                R.drawable.ic_logo,
                                SpanUtils.ALIGN_CENTER
                            )
                            .create()

                    itemHolder.tv(R.id.text2).text =
                        itemHolder.tv(R.id.text1).text
                    itemHolder.tv(R.id.text3).text =
                        SpannableStringBuilder("测试文本\n测试文本").apply {
                            setSpan(RSpan.TextSpan(), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            setSpan(RSpan.TextSpan(), 6, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }

                    val spanKey = "span key"
                    val spanValue =
                        "span value .span value .span value .span value .span value.88888888@qq.com\n"
                    val spanValueNum = "888888888888888888888888888888888888888888888888@qq.com\n"

                    itemHolder.tv(R.id.text4).text = RSpan.get()
                        .appendMainText(spanKey)
                        .setMinSize(ResUtil.dpToPx(100F).toInt())
                        .appendMainText(spanValue)
                        .setForegroundColor(Color.RED)

                        .appendMainText(spanKey)
                        .setMinSize(ResUtil.dpToPx(100F).toInt())
                        .appendMainText(spanValue)
                        .setForegroundColor(Color.RED)

                        .appendMainText(spanKey)
                        .setMinSize(ResUtil.dpToPx(100F).toInt())
                        .appendMainText(spanValue)
                        .setForegroundColor(Color.RED)

                        .appendMainText(spanKey)
                        .setMinSize(ResUtil.dpToPx(100F).toInt())
                        .appendMainText(spanValueNum)
                        .setForegroundColor(Color.RED)
                        .replaceText(spanValueNum)
                        .setMaxSize(200 * dpi)

                        .appendMainText(spanKey)
                        .setMinSize(ResUtil.dpToPx(100F).toInt())
                        .appendMainText(spanValueNum)
                        .setForegroundColor(Color.RED)
                        .replaceText(spanValueNum)
                        .setMaxSize(200 * dpi)

                        .appendImage(R.drawable.ic_logo)
                        .append("测试文本")
                        .setForegroundColor(Color.RED)
                        .setMinSize(ResUtil.dpToPx(5700F).toInt())
                        .setBackgroundColor(Color.YELLOW)
                        .appendImage(
                            R.drawable.ic_logo,
                            SpanUtils.ALIGN_CENTER
                        )
                        .create()

                    itemHolder.click(R.id.button1) {
                        HttpConfigDialog.show(mAttachContext, "base_url") {

                        }
                    }

                    itemHolder.click(R.id.button2) {
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

                    val revealLayout: CircularRevealFrameLayout =
                        itemHolder.v(R.id.reveal_layout)
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

                    itemHolder.tv(R.id.time_view).text =
                        SpannableStringBuilder("22:22:22").apply {
                            setSpan(
                                RSpan.TextSpan().apply {
                                    setOffsetY(-2 * dp)
                                },
                                2,
                                3,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                        }

                    itemHolder.tv(R.id.time_view2).text =
                        SpannableStringBuilder("22:22:22").apply {
                            setSpan(MarginTextSpan(-2 * dp), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        }
                }
            }
        }
    }

    var screenshotPath: String? = null
    var screenshotObserver = RScreenshotObserver()

    override fun onFragmentShow(bundle: Bundle?) {
        super.onFragmentShow(bundle)

        screenshotObserver.observe(requireActivity().contentResolver) {
            screenshotPath = it
            baseDslAdapter.notifyItemChanged(0)
        }
    }

    override fun onFragmentHide() {
        super.onFragmentHide()
        screenshotObserver.unObserve()
    }

    override fun onDestroy() {
        super.onDestroy()
        screenshotObserver.release()
    }

    class CircleClipOutline : ViewOutlineProvider() {

        var clipProgress = 0f

        override fun getOutline(view: View, outline: Outline) {
            view.clipToOutline = true

//            if (clipProgress >= 1f) {
//                outline.setEmpty()
//                return
//            }

            val width = view.measuredWidth
            val height = view.measuredHeight

            val size = max(width, height) + min(width, height) / 2
            val offset = (clipProgress * size).toInt()

            //setOval 要保证是正方形, 才有效果
            outline.setOval(
                width / 2 - offset,
                height - offset,
                width / 2 + offset,
                height + offset
            )


//            val offsetWidth = (clipProgress * size / 2).toInt() //40 * dpi
//            val offsetHeight = (clipProgress * size / 2).toInt() //40 * dpi
//            outline.setOval(
//                width / 2 - offsetWidth,
//                height - 2 * offsetHeight,
//                width / 2 + offsetWidth,
//                height
//            )
        }
    }
}