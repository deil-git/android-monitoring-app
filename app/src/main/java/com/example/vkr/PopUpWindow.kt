package com.example.vkr

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.format.DateFormat
import android.util.Log
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityPopUpWindowBinding
import java.util.*
import kotlin.properties.Delegates

class PopUpWindow : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    lateinit var bindingClass: ActivityPopUpWindowBinding
    lateinit var s1: String
    lateinit var s2: String
    var promStart = ""
    var promEnd = ""
    var ButtonNum = 0
    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0
    var myDay1 = 0
    var myMonth1: Int = 0
    var myYear1: Int = 0
    var myHour1: Int = 0
    var myMinute1: Int = 0
    var myDay2 = 0
    var myMonth2: Int = 0
    var myYear2: Int = 0
    var myHour2: Int = 0
    var myMinute2: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        bindingClass = ActivityPopUpWindowBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        s1 = "<b>С:</b> "
        bindingClass.TextTime1.text = Html.fromHtml(s1)
        s2 = "<b>До:</b> "
        bindingClass.TextTime2.text = Html.fromHtml(s2)

        bindingClass.btnPick1.setOnClickListener {
            ButtonNum = 1
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this, this, year, month, day)
            datePickerDialog.show()
        }

        bindingClass.btnPick2.setOnClickListener {
            ButtonNum = 2
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this, this, year, month, day)
            datePickerDialog.show()
        }

        bindingClass.btnRealTime.setOnClickListener {
            val intent = Intent()
            intent.putExtra("typeGraph", "RealTime")
            setResult(RESULT_OK, intent)
            finish()
        }

        bindingClass.btnPromTime.setOnClickListener {
            var Error: Boolean = false
            if(bindingClass.TextTime1.text == "С: " ||  bindingClass.TextTime2.text == "До: "){
                Toast.makeText(applicationContext,
                    "Выберите временной промежуток",
                    Toast.LENGTH_LONG).show()
            } else{
                if(myYear1 <= myYear2){ if(myYear1 == myYear2){
                if(myMonth1 <= myMonth2){ if(myMonth1 == myMonth2){
                if(myDay1 <= myDay2){ if(myDay1 == myDay2){
                if(myHour1 <= myHour2){ if(myHour1 == myHour2){
                if(myMinute1 < myMinute2){
                    val intent = Intent()
                    intent.putExtra("typeGraph", "PromTime")
                    intent.putExtra("promStart", promStart)
                    intent.putExtra("promEnd", promEnd)
                    setResult(RESULT_OK, intent)
                    finish()
                } else{Error = true}
                    } else{val intent = Intent()
                        intent.putExtra("typeGraph", "PromTime")
                        intent.putExtra("promStart", promStart)
                        intent.putExtra("promEnd", promEnd)
                        setResult(RESULT_OK, intent)
                        finish()}
                } else{Error = true}
                    } else{val intent = Intent()
                        intent.putExtra("typeGraph", "PromTime")
                        intent.putExtra("promStart", promStart)
                        intent.putExtra("promEnd", promEnd)
                        setResult(RESULT_OK, intent)
                        finish()}
                } else{Error = true}
                    } else{val intent = Intent()
                        intent.putExtra("typeGraph", "PromTime")
                        intent.putExtra("promStart", promStart)
                        intent.putExtra("promEnd", promEnd)
                        setResult(RESULT_OK, intent)
                        finish()}
                } else{Error = true}
                } else{val intent = Intent()
                    intent.putExtra("typeGraph", "PromTime")
                    intent.putExtra("promStart", promStart)
                    intent.putExtra("promEnd", promEnd)
                    setResult(RESULT_OK, intent)
                    finish()}
                } else{Error = true}

                if(Error){
                    Toast.makeText(applicationContext,
                        "Некорректный временной промежуток",
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month + 1
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this, this, hour, minute,
            DateFormat.is24HourFormat(this))
        timePickerDialog.show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        if(ButtonNum == 1) {
            myDay1 = myDay
            myMonth1 = myMonth
            myYear1 = myYear
            myHour1 = myHour
            myMinute1 = myMinute
            } else{
            myDay2 = myDay
            myMonth2 = myMonth
            myYear2 = myYear
            myHour2 = myHour
            myMinute2 = myMinute
        }

        var myMonth1s = myMonth1.toString()
        var myMonth2s = myMonth2.toString()
        var myDay1s = myDay1.toString()
        var myDay2s = myDay2.toString()
        var myHour1s = myHour1.toString()
        var myHour2s = myHour2.toString()
        var myMinute1s = myMinute1.toString()
        var myMinute2s = myMinute2.toString()

        if(myMonth1 <= 9){
            myMonth1s = "0" + myMonth1.toString()
        }
        if(myMonth2 <= 9){
            myMonth2s = "0" + myMonth2.toString()
        }
        if(myDay1 <= 9){
            myDay1s = "0" + myDay1.toString()
        }
        if(myDay2 <= 9){
            myDay2s = "0" + myDay2.toString()
        }
        if(myHour1 <= 9){
            myHour1s = "0" + myHour1.toString()
        }
        if(myHour2 <= 9){
            myHour2s = "0" + myHour2.toString()
        }
        if(myMinute1 <= 9){
            myMinute1s = "0" + myMinute1.toString()
        }
        if(myMinute2 <= 9){
            myMinute2s = "0" + myMinute2.toString()
        }

        if(ButtonNum == 1) {
            s1 = "<b>С:</b> $myDay1s.$myMonth1s.$myYear $myHour1s:$myMinute1s"
            bindingClass.TextTime1.text = Html.fromHtml(s1)
        } else{
            s2 = "<b>До:</b> $myDay2s.$myMonth2s.$myYear $myHour2s:$myMinute2s"
            bindingClass.TextTime2.text = Html.fromHtml(s2)
        }

        promStart = myYear1.toString() + "-" + myMonth1s + "-" + myDay1s + "T" + myHour1s + ":" + myMinute1s + ":00"
        promEnd = myYear2.toString() + "-" + myMonth2s + "-" + myDay2s + "T" + myHour2s + ":" + myMinute2s + ":00"
        Log.d("DateTime", promStart + " " + promEnd)
    }
}