package com.example.vkr

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Window
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityPopUpWindowBinding
import java.util.*

class PopUpWindow : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    lateinit var bindingClass: ActivityPopUpWindowBinding
    lateinit var textView: TextView
    lateinit var button: Button
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

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        bindingClass = ActivityPopUpWindowBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        textView = findViewById(R.id.textView)
        button = findViewById(R.id.btnPick)
        button.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this, this, year, month, day)
            datePickerDialog.show()
        }

        bindingClass.popupWindowButton.setOnClickListener {
            finish()
        }
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month
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
        textView.text = "Year: " + myYear + "" + "Month: " + myMonth + "" + "Day: " + myDay + "" + "Hour: " + myHour + "" + "Minute: " + myMinute
    }
}