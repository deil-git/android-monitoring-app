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
import java.util.stream.IntStream.range


class GraphActivity : AppCompatActivity(), OnChartValueSelectedListener {
    lateinit var bindingClass: ActivityGraphBinding
    lateinit var chart: LineChart
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

        val xAxis = chart.xAxis
        xAxis.enableGridDashedLine(10f,10f,0f)

        val yAxis = chart.axisLeft
        chart.axisRight.isEnabled = false
        yAxis.enableGridDashedLine(10f, 10f, 0f)
        yAxis.setAxisMaximum(200f)
        yAxis.setAxisMinimum(-50f)

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
            Log.d("aaa", atemp.toString())
        }

//        val values = ArrayList<Entry>()
//
//        for (i in 0 until count) {
//
//            val value = (Math.random() * range).toFloat() - 30
//            values.add(Entry(i.toFloat(), value/*, resources.getDrawable(R.drawable.star)*/))
//        }

        val set1: LineDataSet

        if (chart.data != null && chart.data.dataSetCount > 0) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")

            set1.setDrawIcons(false)

            // draw dashed line
            //set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            //set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 9f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
//            set1.setDrawFilled(true)
//            set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart.axisLeft.axisMinimum }

            // set color of filled area
//                val drawable = ContextCompat.getDrawable(this, R.color.teal_200)
//                set1.fillDrawable = drawable
//                set1.fillColor = Color.BLACK


            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            chart.data = data
        }
    }


    override fun onNothingSelected() {
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
    }

}
