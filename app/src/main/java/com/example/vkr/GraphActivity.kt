package com.example.vkr

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityGraphBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener


class GraphActivity : AppCompatActivity(), OnChartValueSelectedListener {
    lateinit var bindingClass: ActivityGraphBinding
    lateinit var chart: LineChart
    lateinit var chart2: LineChart
    var atemp = arrayListOf<Float>()
    var ahum = arrayListOf<Float>()
    var atime = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        bindingClass = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        chart = findViewById(bindingClass.chart1.id)
        chart.setBackgroundColor(Color.WHITE)
        chart.description.isEnabled = false
        chart.setTouchEnabled(true)
        chart.setOnChartValueSelectedListener(this)
        chart.setDrawGridBackground(false)
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)
        chart2 = findViewById(bindingClass.chart1.id)
        chart2.setBackgroundColor(Color.WHITE)
        chart2.description.isEnabled = false
        chart2.setTouchEnabled(true)
        chart2.setOnChartValueSelectedListener(this)
        chart2.setDrawGridBackground(false)
        chart2.isDragEnabled = true
        chart2.setScaleEnabled(true)
        chart2.setPinchZoom(true)

        val xAxis = chart.xAxis
        val xAxis2 = chart2.xAxis
        xAxis.enableGridDashedLine(10f,10f,0f)
        xAxis2.enableGridDashedLine(10f,10f,0f)

        val yAxis = chart.axisLeft
        val yAxis2 = chart2.axisRight
//        chart.axisRight.isEnabled = false
        yAxis.enableGridDashedLine(10f, 10f, 0f)
        yAxis.setAxisMaximum(40f)
        yAxis.setAxisMinimum(-50f)
        yAxis2.enableGridDashedLine(10f, 10f, 0f)
        yAxis2.setAxisMaximum(40f)
        yAxis2.setAxisMinimum(10f)

        val llXAxis = LimitLine(9f, "Index 10")
        llXAxis.lineWidth = 4f
        llXAxis.enableDashedLine(10f, 10f, 0f)
        llXAxis.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
        llXAxis.textSize = 10f
        //llXAxis.typeface = tfRegular

        val ll1 = LimitLine(150f, "Upper Limit")
        ll1.lineWidth = 4f
        ll1.enableDashedLine(10f, 10f, 0f)
        ll1.labelPosition = LimitLabelPosition.RIGHT_TOP
        ll1.textSize = 10f
        //ll1.typeface = tfRegular

        val ll2 = LimitLine(-30f, "Lower Limit")
        ll2.lineWidth = 4f
        ll2.enableDashedLine(10f, 10f, 0f)
        ll2.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
        ll2.textSize = 10f
        //ll2.typeface = tfRegular

        // draw limit lines behind data instead of on top
        yAxis.setDrawLimitLinesBehindData(true)
        xAxis.setDrawLimitLinesBehindData(true)

        // add limit lines
        yAxis.addLimitLine(ll1)
        yAxis.addLimitLine(ll2)


        chart.animateX(1500)
        val legend = chart.legend
        legend.form = Legend.LegendForm.LINE
        chart2.animateX(1500)
        val legend2 = chart2.legend
        legend2.form = Legend.LegendForm.LINE

        Network.getData {
            runOnUiThread(Runnable {
                Log.d("getData", it[0].toString())

                var r: String = ""
                for(d in it){
                    r += "${d.temp} ${d.hum} ${d.time} \n"
                    atemp.add(d.temp)
                    ahum.add(d.hum)
                    atime.add(d.time)

                }
                Log.d("getData", r)
                setData(45, 180f)
            })
        }

    }


    private fun setData(count: Int, range: Float) {

        val values: ArrayList<Entry> = ArrayList()
        for (i in 0 until atemp.size) {
            // turn your data into Entry objects
            values.add(Entry(i.toFloat(), atemp[i]))
        }

        val values2: ArrayList<Entry> = ArrayList()
        for (i in 0 until ahum.size) {
            // turn your data into Entry objects
            values2.add(Entry(i.toFloat(), ahum[i]))
        }

        val set1: LineDataSet
        val set2: LineDataSet

        if (chart.data != null && chart.data.dataSetCount > 0) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
            set2 = chart2.data.getDataSetByIndex(0) as LineDataSet
            set2.values = values2
            set2.notifyDataSetChanged()
            chart2.data.notifyDataChanged()
            chart2.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")

            set1.setDrawIcons(false)

            set2 = LineDataSet(values2, "DataSet 2")

            set2.setDrawIcons(false)

            // draw dashed line
            //set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.GREEN
            set1.setCircleColor(Color.GREEN)

            set2.color = Color.BLUE
            set2.setCircleColor(Color.BLUE)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f

            set2.lineWidth = 1f
            set2.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)
            set2.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set2.formLineWidth = 1f
            //set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f
            set2.formSize = 15f

            // text size of values
            set1.valueTextSize = 0f
            set2.valueTextSize = 0f

            // draw selection line as dashed
            //set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
//            set1.setDrawFilled(true)
//            set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart.axisLeft.axisMinimum }

            // set color of filled area
//                val drawable = ContextCompat.getDrawable(this, R.color.teal_200)
//                set1.fillDrawable = drawable
//                set1.fillColor = Color.BLACK


            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1)
            dataSets.add(set2)

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            chart.data = data

        }
    }


    override fun onNothingSelected() {
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        bindingClass.chart1.highlightValue(h);
        if (h != null) {
            Log.d("Highlight", "onValueSelected: " + h.getY())
        };
    }

}

