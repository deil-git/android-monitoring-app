package com.example.vkr

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityPopUpWindowBinding
import java.util.*
import kotlin.properties.Delegates

class PopUpWindow : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    lateinit var bindingClass: ActivityPopUpWindowBinding
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
        overridePendingTransition(0, 0)
        bindingClass = ActivityPopUpWindowBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

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
            finish()
        }

        bindingClass.btnPromTime.setOnClickListener {
            var Error: Boolean = false
            if(bindingClass.TextTime1.text == "С: " ||  bindingClass.TextTime2.text == "До: "){
                Toast.makeText(applicationContext,
                    "Выберите временной промежуток",
                    Toast.LENGTH_LONG).show()
            } else{
                if(myYear1 <= myYear2){
                    if(myYear1 == myYear2){
                        if(myMonth1 <= myMonth2){
                            if(myMonth1 == myMonth2){
                                if(myDay1 <= myDay2){
                                    if(myDay1 == myDay2){
                                        if(myHour1 <= myHour2){
                                            if(myHour1 == myHour2){
                                                if(myMinute1 < myMinute2){
                                                    finish()
                                                } else{Error = true}
                                            } else{finish()}
                                        } else{Error = true}
                                    } else{finish()}
                                } else{Error = true}
                            } else{finish()}
                        } else{Error = true}
                    } else{finish()}
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
            bindingClass.TextTime1.text = "С: " + myDay + "." + myMonth+ "." + myYear + " " + myHour + ":" + myMinute
            myDay1 = myDay
            myMonth1 = myMonth
            myYear1 = myYear
            myHour1 = myHour
            myMinute1 = myMinute
            } else{
            bindingClass.TextTime2.text = "До: " + myDay + "." + myMonth+ "." + myYear + " " + myHour + ":" + myMinute
            myDay2 = myDay
            myMonth2 = myMonth
            myYear2 = myYear
            myHour2 = myHour
            myMinute2 = myMinute
        }

    }
}