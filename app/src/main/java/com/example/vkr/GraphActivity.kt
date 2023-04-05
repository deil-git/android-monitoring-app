package com.example.vkr

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityGraphBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class LineChartXAxisValueFormatter : IndexAxisValueFormatter() {

    @SuppressLint("SimpleDateFormat")
    override fun getFormattedValue(value: Float): String? {
        // Convert float value to date string
        // Convert from seconds back to milliseconds to format time  to show to the user
        val emissionsMilliSince1970Time = value.toLong() * 1000

        // Show time in local version
        val date = Date(emissionsMilliSince1970Time)
        val format = SimpleDateFormat("MM.dd HH:mm:ss")
        return format.format(date)
//        val dateTimeFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
//        return dateTimeFormat.format(timeMilliseconds)
    }
}

class GraphActivity : AppCompatActivity(), OnChartValueSelectedListener {
    lateinit var executorService: ScheduledExecutorService
    lateinit var bindingClass: ActivityGraphBinding
    lateinit var chart: LineChart
    lateinit var chart2: LineChart
    lateinit var incubNum: String
    var atemp = arrayListOf<Float>()
    var ahum = arrayListOf<Float>()
    var atime = arrayListOf<Float>()
    var typeGraph = "RealTime"
    var promStart = ""
    var promEnd = ""


    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        incubNum = intent.getStringExtra("incubNum") ?: ""
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        bindingClass = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.graphLabel.text = incubNum + " Температура °C, Влажность %"

        chart = findViewById(bindingClass.chart1.id)
        chart.setBackgroundColor(Color.WHITE)
        chart.description.isEnabled = true
        chart.setTouchEnabled(true)
        chart.setOnChartValueSelectedListener(this)
        chart.setDrawGridBackground(false)
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)
        chart2 = findViewById(bindingClass.chart1.id)
        chart2.setBackgroundColor(Color.WHITE)
        chart2.description.isEnabled = true
        chart2.setTouchEnabled(true)
        chart2.setOnChartValueSelectedListener(this)
        chart2.setDrawGridBackground(false)
        chart2.isDragEnabled = true
        chart2.setScaleEnabled(true)
        chart2.setPinchZoom(true)

        val description = chart.description
        description.textSize = 24f
        description.text = ""


        val xAxis = chart.xAxis
        chart.xAxis.valueFormatter = LineChartXAxisValueFormatter()

//        xAxis.enableGridDashedLine(10f,10f,0f)
//        xAxis2.enableGridDashedLine(10f,10f,0f)

        val yAxis = chart.axisLeft
        val yAxis2 = chart2.axisRight
//        chart.axisRight.isEnabled = false
        yAxis.enableGridDashedLine(10f, 10f, 0f)
        yAxis.setAxisMaximum(33f)
        yAxis.setAxisMinimum(26f)
        yAxis2.enableGridDashedLine(10f, 10f, 0f)
        yAxis2.setAxisMaximum(33f)
        yAxis2.setAxisMinimum(26f)

        val llXAxis = LimitLine(9f, "Index 10")
        llXAxis.lineWidth = 4f
        llXAxis.enableDashedLine(10f, 10f, 0f)
        llXAxis.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
        llXAxis.textSize = 10f
        //llXAxis.typeface = tfRegular

        val ll1 = LimitLine(28.5f, "макс. темп.")
        ll1.lineWidth = 3f
        ll1.enableDashedLine(10f, 10f, 0f)
        ll1.labelPosition = LimitLabelPosition.RIGHT_TOP
        ll1.textSize = 10f
        //ll1.typeface = tfRegular

        val ll2 = LimitLine(27f, "мин. темп.")
        ll2.lineWidth = 3f
        ll2.enableDashedLine(10f, 10f, 0f)
        ll2.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
        ll2.textSize = 10f
        //ll2.typeface = tfRegular

        val ll3 = LimitLine(31.5f, "макс. влаж.")
        ll3.lineWidth = 3f
        ll3.enableDashedLine(10f, 10f, 0f)
        ll3.labelPosition = LimitLabelPosition.RIGHT_TOP
        ll3.textSize = 10f

        val ll4 = LimitLine(29.5f, "мин. влаж.")
        ll4.lineWidth = 3f
        ll4.enableDashedLine(10f, 10f, 0f)
        ll4.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
        ll4.textSize = 10f

        // draw limit lines behind data instead of on top
        yAxis.setDrawLimitLinesBehindData(true)
        xAxis.setDrawLimitLinesBehindData(true)

        // add limit lines
        yAxis.addLimitLine(ll1)
        yAxis.addLimitLine(ll2)
        yAxis.addLimitLine(ll3)
        yAxis.addLimitLine(ll4)

        chart.animateX(1500)
        val legend = chart.legend
        legend.form = Legend.LegendForm.LINE
        chart2.animateX(1500)
        val legend2 = chart2.legend
        legend2.form = Legend.LegendForm.LINE

        bindingClass.ParamButton.setOnClickListener {
            val intent = Intent(this, PopUpWindow::class.java)
            startActivityForResult(intent, 1)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                typeGraph = data?.getStringExtra("typeGraph").toString()
                promStart = data?.getStringExtra("promStart").toString()
                promEnd = data?.getStringExtra("promEnd").toString()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        executorService.shutdown()
    }
    override fun onStop() {
        super.onStop()
        executorService.shutdown()
    }
    @SuppressLint("SimpleDateFormat")
    override fun onResume() {
        super.onResume()
        executorService = Executors.newSingleThreadScheduledExecutor()
        executorService.scheduleAtFixedRate({
            Network.getData(incubNum, typeGraph, promStart, promEnd) {
                runOnUiThread(Runnable {
                    //Log.d("getData", it[0].toString())
                    var r: String = ""
                    atime.clear()
                    atemp.clear()
                    ahum.clear()
                    for(d in it){

                        atemp.add(d.temp)
                        ahum.add(d.hum)

                        val format = SimpleDateFormat("MM-dd'T'HH:mm:ss")
                        val tm = d.time
                        val s:String = (tm[5].toString()+tm[6]+tm[7]+tm[8]+tm[9]+tm[10]+tm[11]+tm[12]+tm[13]+tm[14]+tm[15]+tm[16]+tm[17]+tm[18])
                        val date = format.parse(s)
                        val floatTime = date.time.toFloat() / 1000
                        atime.add(floatTime)
                        r += "${d.temp} ${d.hum} ${floatTime} \n"
                    }
                    Log.d("getData", "итерация")
                    setData()
                })
            }
        }, 0, 5, TimeUnit.SECONDS)
    }

    private fun setData() {

        chart.data = null

        val values: ArrayList<Entry> = ArrayList()
        for (i in 0 until atemp.size) {
            // turn your data into Entry objects
            values.add(Entry(atime[i], atemp[i]))
        }

        val values2: ArrayList<Entry> = ArrayList()
        for (i in 0 until ahum.size) {
            // turn your data into Entry objects
            values2.add(Entry(atime[i], ahum[i]))
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
            set1 = LineDataSet(values, "Температура")

            set1.setDrawIcons(false)

            set2 = LineDataSet(values2, "Влажность")

            set2.setDrawIcons(false)

            // draw dashed line
            //set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.GREEN
            set1.setCircleColor(Color.TRANSPARENT)

            set2.color = Color.BLUE
            set2.setCircleColor(Color.TRANSPARENT)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 1f

            set2.lineWidth = 1f
            set2.circleRadius = 1f

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

            chart.invalidate()

        }
    }


    override fun onNothingSelected() {
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        bindingClass.chart1.highlightValue(h);
        if (h != null) {
            val description = chart.description
            if (h.dataSetIndex == 0){
                description.text = h.getY().toString() + "°С"
            }
            else{
                description.text = h.getY().toString() + "%"
            }

            //Log.d("Highlight", "onValueSelected: " + h.getY() + " " + e)
        };
    }

}

