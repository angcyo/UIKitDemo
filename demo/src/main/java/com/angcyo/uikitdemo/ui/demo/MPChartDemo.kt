package com.angcyo.uikitdemo.ui.demo

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.kotlin.getColor
import com.angcyo.uiview.less.kotlin.getDimen
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.angcyo.uiview.less.resources.ResUtil
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/02/24
 */
class MPChartDemo : AppBaseItemFragment() {
    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        singleItems.add(object : SingleItem() {

            override fun createItemView(parent: ViewGroup, viewType: Int): View {
                return LineChart(mAttachContext).apply {
                    description.isEnabled = false

                    layoutParams = ViewGroup.LayoutParams(-1, ResUtil.dpToPx(100f).toInt())

                    val padding = ResUtil.dpToPx(10f).toInt()
                    setPadding(padding, padding, padding, padding)

                    setNoDataText("")
                }
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                (holder.itemView as? LineChart)?.apply {
                    legend.isEnabled = false
                    setLineChart(this).apply {
                    }
                }
            }

        })
        singleItems.add(object : ChartItem() {

            override fun createItemView(parent: ViewGroup, viewType: Int): View {
                return LineChart(mAttachContext).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, ResUtil.dpToPx(200f).toInt())
                }
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                (holder.itemView as? LineChart)?.apply {
                    //setTouchEnabled(false)
                    xAxis.apply {
                        setDrawAxisLine(false)
                    }
                    axisLeft.apply {
                        setDrawTopYLabelEntry(false)
                        addLimitLine(LimitLine(15f, "LimitLine"))
                    }
                    axisRight.apply {
                        setDrawLabels(false)
                    }
                    setScaleEnabled(false)

                    isAutoScaleMinMaxEnabled = true

                    isDragDecelerationEnabled = false
                    setLineChart(this).apply {
                    }
                }
            }
        })
        singleItems.add(object : ChartItem() {

            override fun createItemView(parent: ViewGroup, viewType: Int): View {
                return LineChart(mAttachContext).apply {
                    layoutParams = ViewGroup.LayoutParams(-1, ResUtil.dpToPx(250f).toInt())
                }
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                (holder.itemView as? LineChart)?.apply {
                    extraRightOffset = ResUtil.dpToPx(6f)
                    //setTouchEnabled(false)
                    legend.apply {
                        horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                        verticalAlignment = Legend.LegendVerticalAlignment.CENTER
                        orientation = Legend.LegendOrientation.VERTICAL
                        xEntrySpace = ResUtil.dpToPx(20f)
                        //yEntrySpace = ResUtil.dpToPx(10f)
                        //stackSpace = ResUtil.dpToPx(30f)
                        //formToTextSpace = ResUtil.dpToPx(6f)
                        //yOffset = 20f
                        //xOffset = -10f
                        form = Legend.LegendForm.CIRCLE
                        //formLineWidth = ResUtil.dpToPx(120f)
                        //formSize = ResUtil.dpToPx(10f)
                        maxSizePercent = 0.5f
                    }

                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        //setDrawLabels(false)
                        //setDrawAxisLine(false)
                        setDrawGridLines(false)
                        labelRotationAngle = 45f

                        valueFormatter = object : IAxisValueFormatter {
                            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                                return "value:$value"
                            }
                        }
                    }
                    axisLeft.apply {
                        setDrawZeroLine(true)
                        zeroLineColor = Color.MAGENTA
                        zeroLineWidth = ResUtil.dpToPx(2f)

                        addLimitLine(LimitLine(15f, "LimitLine"))
                        setDrawLimitLinesBehindData(true)
                    }
                    axisRight.apply {
                        isEnabled = false
                    }

                    setLineChart(this).apply {
                    }

                    animateXY(3000, 3000)
                }
            }
        })
    }

    fun setLineChart(chart: LineChart): LineData {
        chart.apply {
            data = LineData(
                LineDataSet(
                    listOf(
                        Entry(-3f, -3f),
                        Entry(1f, 1f),
                        Entry(3f, 3f),
                        Entry(30f, 13f)
                    ),
                    "label 1"
                ).apply {
                    mode = LineDataSet.Mode.STEPPED
                    color = Color.RED
                    setDrawFilled(true)
                },
                LineDataSet(
                    listOf(
                        Entry(-3f, -3f),
                        Entry(2f, 2f),
                        Entry(3f, 3f),
                        Entry(30f, 13f)
                    ),
                    "label 22"
                ).apply {
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                    color = Color.YELLOW
                },
                LineDataSet(
                    listOf(
                        Entry(-13f, -23f),
                        Entry(1f, 1f),
                        Entry(10f, 10f),
                        Entry(15f, 15f),
                        Entry(30f, 25f),
                        Entry(20f, 13f)
                    ),
                    "label 333"
                ).apply {
                    mode = LineDataSet.Mode.HORIZONTAL_BEZIER
                    color = Color.BLUE
                    setDrawFilled(true)
                    fillColor = getColor(R.color.colorAccent)
                    setValueTextColors(listOf(Color.RED, Color.YELLOW, Color.LTGRAY))

                    setValueFormatter { value, entry, dataSetIndex, viewPortHandler ->
                        "test$value"
                    }

                    //axisDependency = YAxis.AxisDependency.RIGHT
                },
                LineDataSet(
                    listOf(
                        Entry(-3f, -3f),
                        Entry(5f, 5f),
                        Entry(13f, 13f),
                        Entry(30f, 33f)
                    ),
                    "label 4444"
                ).apply {
                    mode = LineDataSet.Mode.LINEAR
                    setDrawValues(false)
                    setDrawIcons(false)
                }
            )
        }

        return chart.lineData
    }

    inner abstract class ChartItem :
        SingleItem(0, getDimen(R.dimen.base_line), getColor(R.color.transparent_dark20)) {

        init {
            mType = Type.LINE
        }
    }

}