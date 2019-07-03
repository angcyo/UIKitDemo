package com.angcyo.uikitdemo.ui.demo

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uikitex.chart.ExtraEntry
import com.angcyo.uikitex.chart.ExtraLineChartRenderer
import com.angcyo.uikitex.chart.RMPChart
import com.angcyo.uikitex.chart.SingleBarChart
import com.angcyo.uiview.less.kotlin.getColor
import com.angcyo.uiview.less.kotlin.getDimen
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.angcyo.uiview.less.resources.ResUtil
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/02/24
 */
class MPChartDemo2 : AppBaseItemFragment() {
    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        /**
         * 线性图表
         * */
        singleItems.add(object : SingleItem() {

            override fun createItemView(parent: ViewGroup, viewType: Int): View {
                return LineChart(mAttachContext).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, ResUtil.dpToPx(300f).toInt())

                    setBackgroundColor(Color.WHITE)

                    renderer = ExtraLineChartRenderer(this, animator, viewPortHandler)
                }
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                (holder.itemView as? LineChart)?.apply {
                    RMPChart.get(this)
                        .addLineEntry(1f, 1f)
                        .addLineEntry(2f, 2f)
                        .addLineEntry(4f, 4f)
                        .addLineEntry(ExtraEntry(5f, 5f, Color.YELLOW))
                        .addLineEntry(8f, 8f)
                        .addLineEntry(16f, 10f)
                        .setColor(Color.RED)
                        .setLineCircleHoleColor(Color.WHITE)
                        .setLineCircleColor(Color.BLUE)
                        .setLineCircleRadius(6f)
                        .setLineCircleHoleRadius(4f)
                        .setLineMode(LineDataSet.Mode.CUBIC_BEZIER)
                        .setLineDrawCircleHole(true)
                        .setLineDrawCircles(false)
                        .setDrawLineValues(false)
                        .setLineDrawFilled(true)
                        .setDrawXAxisLine(true)
                        .setDrawXAxisTextColor(Color.BLUE)
                        .setDrawXAxisGridLines(true)
                        .setDrawXAxisLineColor(Color.BLUE)
                        .setDrawXAxisGridColor(Color.BLUE)
                        .setXAxisGridDashedLine(20f, 40f, 0f)
                        .setXAxisLineDashedLine(20f, 40f, 0f)
                        .addNew()
                        .addLineEntry(5f, 7f)
                        .addLineEntry(9f, 10f)
                        .setDrawYAxisLeftTextColor(Color.BLUE)
                        .setDrawYAxisLeftLineColor(Color.BLUE)
                        .setYAxisLeftGridDashedLine(20f, 20f, 0f)
                        .setYAxisLeftLineDashedLine(20f, 20f, 0f)
                        .setXAxisValueFormatter(object : ValueFormatter() {
                            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                                return "Value:$value"
                            }
                        })
                        .setXAxisLabelCount(3, false)
                        .addXAxisLimitLine(LimitLine(6f, "6Value"))
                        .addYAxisLeftLimitLine(LimitLine(7f, "7Value"))
                        .setYAxisLeftEnable(true)
                        .setDescriptionEnable(true)
                        .setDescriptionText("(秒)")
                        .setDescriptionToTopLeft()
                        .setDescriptionTextColor(Color.BLUE)
                        .setDescriptionOffset(0f, 5f)
                        .doIt()
                }
            }

        })

        /**
         * 饼状图表
         * */
        singleItems.add(object : SingleItem() {

            override fun createItemView(parent: ViewGroup, viewType: Int): View {
                return PieChart(mAttachContext).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, ResUtil.dpToPx(300f).toInt())
                    setBackgroundColor(Color.WHITE)
                }
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                (holder.itemView as? PieChart)?.apply {
                    RMPChart.get(this)
                        .addPieEntry(0.3f, "L1")
                        .addPieEntry(0.7f, "L2")
                        .addPieEntry(1f, "L3")
                        .setDrawPieHoleRadius(50f)
                        .setDrawPieHoleColor(Color.RED)
                        .setDrawPieTransparentCircleColor(Color.YELLOW)
                        .setDrawPieEntryLabelColor(Color.WHITE)
                        .setDrawPieEntryLabelTextSize(30f)
                        .setDrawPieValues(false)
                        .setDrawPieEntryLabels(false)
                        .setDrawPieValueTextColor(Color.RED)
                        .setDrawPieValueTextSize(20f)
                        .setColors(mutableListOf(Color.GREEN, Color.CYAN, Color.MAGENTA))
                        .setDrawPieDrawCenterText(true)
                        .setDrawPieCenterText("总数\n240")
                        .setLegendEnable(true)
                        .doIt()
                }
            }
        })

        /**
         * 柱状图
         * */
        singleItems.add(object : SingleItem() {

            override fun createItemView(parent: ViewGroup, viewType: Int): View {
                return BarChart(mAttachContext).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, ResUtil.dpToPx(300f).toInt())
                    setBackgroundColor(Color.WHITE)
                }
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                (holder.itemView as? BarChart)?.apply {
                    setFitBars(true)
                    xAxis.spaceMin = 1f
                    xAxis.spaceMax = 1f

                    RMPChart.get(this)
                        .addBarEntry(2f, 5f)
                        .addBarEntry(3f, 6f)
                        .doIt()

                    RMPChart.get(this)
                        .setDrawXAxisGridLines(false)
                        .addBarEntry(1f, 1f)
                        .addBarEntry(2f, 5f)
                        .addBarEntry(6f, 3f)
                        .addBarEntry(10f, 8f)
                        .setBarWidth(0.4f)
                        .setDrawBarShadow(false)
                        .setDrawBarValueTextColor(Color.BLUE)
                        .setDrawBarShadowColor(Color.BLUE)
                        .setDrawBarValueTextSize(10f)
                        .setColors(mutableListOf(Color.GREEN, Color.CYAN, Color.MAGENTA))
                        .setDrawYAxisLeftTextColor(Color.BLUE)
                        .setYAxisLeftGridDashedLine(10f, 10f, 0f)
                        .setDescriptionEnable(true)
                        .setDescriptionToTopLeft()
                        .setDescriptionText("(次)")
                        .addNew()
                        .addBarEntry(1f, 7f)
                        .addBarEntry(3f, 18f)
                        .addBarEntry(7f, 3f)
                        .addBarEntry(11f, 8f)
                        .groupBars(0f, 1f, 0.5f)
                        .doIt()
                }
            }
        })

        singleItems.add(object : SingleItem() {

            override fun createItemView(parent: ViewGroup, viewType: Int): View {
                return BarChart(mAttachContext).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, ResUtil.dpToPx(300f).toInt())
                    setBackgroundColor(Color.WHITE)
                }
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                (holder.itemView as? BarChart)?.apply {

                    axisLeft.axisMinimum = 0f
                    axisLeft.axisMaximum = 10f
                    axisLeft.setLabelCount(10, true)

                    RMPChart.get(this)
                        .addBarEntry(0f, 1f)
                        .addBarEntry(2f, 5f)
                        .addBarEntry(3f, 6f)
                        .addBarEntry(4f, 2f)
                        .addBarEntry(40f, 2f)
                        .doIt()

                    xAxis.axisMinimum = 0f
                    //xAxis.axisMaximum = 6f
                    xAxis.setLabelCount(6, true)

//                    data.dataSets.forEach {
//                        it.min
//                    }
                }
            }
        })

        singleItems.add(object : SingleItem() {

            override fun createItemView(parent: ViewGroup, viewType: Int): View {
                return SingleBarChart(mAttachContext).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, ResUtil.dpToPx(300f).toInt())
                    setBackgroundColor(Color.WHITE)
                }
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                (holder.itemView as? BarChart)?.apply {

                    axisLeft.axisMinimum = 0f
                    axisLeft.axisMaximum = 10f
                    axisLeft.setLabelCount(10, true)

                    RMPChart.get(this)
                        .setHighlightPerTapEnabled(false)
                        .setHighlightPerDragEnabled(false)
                        //.setDrawBarShadow(false)
                        .setDrawXAxisGridLines(false)
                        .setScaleEnabled(false)
                        .setBarWidth(ResUtil.dpToPx(40f))
                        .addBarEntry(0f, 1f)
                        .addBarEntry(2f, 5f)
                        .addBarEntry(3f, 6f)
                        .addBarEntry(4f, 2f)
                        .addBarEntry(40f, 2f)
                        .addBarEntry(80f, 2f)
                        .addBarEntry(240f, 2f)
                        .setColors(listOf(Color.RED, Color.YELLOW, Color.BLUE))
                        .doIt()

//                    xAxis.axisMinimum = 0f
                    //xAxis.axisMaximum = 6f
//                    xAxis.setLabelCount(6, true)

//                    data.dataSets.forEach {
//                        it.min
//                    }
                }
            }
        })
    }


    inner abstract class ChartItem :
        SingleItem(0, getDimen(R.dimen.base_line), getColor(R.color.transparent_dark20)) {

        init {
            mType = Type.LINE
        }
    }

}