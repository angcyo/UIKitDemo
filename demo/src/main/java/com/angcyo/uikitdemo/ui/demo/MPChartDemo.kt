package com.angcyo.uikitdemo.ui.demo

import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.kotlin.getColor
import com.angcyo.uiview.less.kotlin.getDimen
import com.angcyo.uiview.less.kotlin.getDrawable
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.angcyo.uiview.less.resources.ResUtil
import com.angcyo.uiview.less.utils.RSpan
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/02/24
 */
class MPChartDemo : AppBaseItemFragment() {
    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        /**
         * 线性图表
         * */
        singleItems.add(object : SingleItem() {

            override fun createItemView(parent: ViewGroup, viewType: Int): View {
                return LineChart(mAttachContext).apply {
                    description.isEnabled = false

                    layoutParams = ViewGroup.LayoutParams(-1, ResUtil.dpToPx(100f).toInt())

                    val padding = ResUtil.dpToPx(10f).toInt()
                    setPadding(padding, padding, padding, padding)

                    setNoDataText("")

                    setBackgroundColor(Color.DKGRAY)
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
                    post {
                        description.apply {
                            text = "(小时)"
                            textAlign = Paint.Align.RIGHT
                            setPosition(
                                viewPortHandler.contentLeft(),
                                viewPortHandler.contentTop()
                            )
                        }
                        invalidate()
                    }
                    extraRightOffset = ResUtil.dpToPx(10f)
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

        /**
         * 饼状图表
         * */
        singleItems.add(object : SingleItem() {

            override fun createItemView(parent: ViewGroup, viewType: Int): View {
                return PieChart(mAttachContext).apply {
                    //description.isEnabled = false

                    layoutParams = ViewGroup.LayoutParams(-1, ResUtil.dpToPx(200f).toInt())

                    val padding = ResUtil.dpToPx(10f).toInt()
                    setPadding(padding, padding, padding, padding)

                    setNoDataText("--")

                    setBackgroundColor(Color.DKGRAY)
                }
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                (holder.itemView as? PieChart)?.apply {
                    centerText = RSpan.get()
                        .append("测试\n").setForegroundColor(Color.RED)
                        .append("100%\n").setForegroundColor(Color.YELLOW).setFontSize(40, true)
                        .append("100%").setForegroundColor(Color.MAGENTA).setFontSize(40, false)
                        .create()
                    setPieChart(this)
                }
            }
        })

        /**
         * 柱状图
         * */
        singleItems.add(object : SingleItem() {

            override fun createItemView(parent: ViewGroup, viewType: Int): View {
                return BarChart(mAttachContext).apply {
                    //description.isEnabled = false

                    layoutParams = ViewGroup.LayoutParams(-1, ResUtil.dpToPx(200f).toInt())

                    val padding = ResUtil.dpToPx(10f).toInt()
                    setPadding(padding, padding, padding, padding)

                    setNoDataText("--")

                    setBackgroundColor(Color.DKGRAY)
                }
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                (holder.itemView as? BarChart)?.apply {
                    setBarChart(this)
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

    fun setPieChart(chart: PieChart): PieData {
        chart.apply {
            extraLeftOffset = -600f

            isDrawHoleEnabled = true
            setDrawSlicesUnderHole(true)

            //百分比值, 但是没有百分号
            setUsePercentValues(false)

            holeRadius = ResUtil.dpToPx(40f)
            transparentCircleRadius = ResUtil.dpToPx(44f)
            setHoleColor(Color.CYAN)
            setTransparentCircleColor(Color.WHITE)

            //占用一个圆的多少角度
            maxAngle = 360f

            minOffset = ResUtil.dpToPx(10f)

            //旋转角度
            rotationAngle = 0f

            legend.apply {
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                verticalAlignment = Legend.LegendVerticalAlignment.CENTER
                orientation = Legend.LegendOrientation.VERTICAL
                //xEntrySpace = ResUtil.dpToPx(20f)
                yEntrySpace = ResUtil.dpToPx(6f)
                //stackSpace = ResUtil.dpToPx(30f)
                //formToTextSpace = ResUtil.dpToPx(6f)
                yOffset = -20f
                xOffset = 100f
                form = Legend.LegendForm.SQUARE
                //formLineWidth = ResUtil.dpToPx(120f)
                //formSize = ResUtil.dpToPx(10f)
                maxSizePercent = 0.1f

            }

            //绘制 entry label
            setDrawEntryLabels(false)

            setTouchEnabled(false)
        }

        chart.data = PieData().apply {
            dataSet = PieDataSet(mutableListOf<PieEntry>().apply {
                add(PieEntry(45f, "已处理 28 (90%)", getDrawable(R.drawable.ic_logo)))
                add(PieEntry(45f, "未处理 5 (10%)"))
                add(PieEntry(30f, "L3"))
                add(PieEntry(30f, "L4"))
            }, null).apply {
                //拨片间隙
                sliceSpace = getDimen(R.dimen.base_ldpi).toFloat()

                //选中之后放大的距离
                selectionShift = 4f

                //是否绘制薄片上的值
                setDrawValues(false)

                //是否绘制薄片上的icon
                setDrawIcons(false)

                //entry label 绘制的位置
                xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                //entry value 绘制的位置
                yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

                setColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
            }
        }
        return chart.data
    }

    fun setBarChart(chart: BarChart): BarData {
        chart.apply {
            //值不够的地方, 用灰色绘制
            setDrawBarShadow(true)

            //在bar的上方是否绘制value, false 表示在bar的下方绘制
            setDrawValueAboveBar(false)

            setFitBars(true)

            isHighlightFullBarEnabled = true

            //setMaxVisibleValueCount(2)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
            }

            setDrawBorders(false)

            legend.apply {
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                verticalAlignment = Legend.LegendVerticalAlignment.CENTER
                orientation = Legend.LegendOrientation.VERTICAL
                //xEntrySpace = ResUtil.dpToPx(20f)
                yEntrySpace = ResUtil.dpToPx(6f)
                //stackSpace = ResUtil.dpToPx(30f)
                //formToTextSpace = ResUtil.dpToPx(6f)
                yOffset = -20f
                form = Legend.LegendForm.SQUARE
                //formLineWidth = ResUtil.dpToPx(120f)
                //formSize = ResUtil.dpToPx(10f)
                maxSizePercent = 0.1f

            }
        }

        chart.data = BarData(
            mutableListOf<IBarDataSet>().apply {
                add(BarDataSet(mutableListOf<BarEntry>().apply {
                    add(BarEntry(1f, floatArrayOf(10f, 15f, 20f)))
                    add(BarEntry(3f, 12f))
                    add(BarEntry(6f, 20f))

                }, "L1").apply {
                    setColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
                })

                add(BarDataSet(mutableListOf<BarEntry>().apply {
                    add(BarEntry(2f, 10f))
                    add(BarEntry(4f, 12f))
                    add(BarEntry(7f, 20f))
                    add(BarEntry(10f, 40f))

                }, "L2").apply {
                    setColors(Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE)
                    setDrawValues(false)
                })
            }
        ).apply {
            barWidth = 0.2f
            groupBars(0f, 0.8f, 0.1f)
        }
        return chart.data
    }


    inner abstract class ChartItem :
        SingleItem(0, getDimen(R.dimen.base_line), getColor(R.color.transparent_dark20)) {

        init {
            mType = Type.LINE
        }
    }

}