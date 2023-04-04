package com.example.vkr

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.vkr.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMapBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMapBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.BackButton.setOnClickListener {
            finish()
        }

        bindingClass.incubatorButtonJW1.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("inc", 1)
            startActivity(intent)
        }
        bindingClass.incubatorButtonJW2.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("inc", 2)
            startActivity(intent)
        }
        bindingClass.incubatorButtonJW3.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("inc", 3)
            startActivity(intent)
        }
        bindingClass.incubatorButtonJW4.setOnClickListener {

        }
        bindingClass.incubatorButtonJW5.setOnClickListener {

        }
        bindingClass.incubatorButtonJW6.setOnClickListener {

        }

        bindingClass.incubatorButtonHT1.setOnClickListener {

        }
        bindingClass.incubatorButtonHT2.setOnClickListener {

        }
        bindingClass.incubatorButtonHT3.setOnClickListener {

        }
        bindingClass.incubatorButtonHT4.setOnClickListener {

        }
        bindingClass.incubatorButtonHT5.setOnClickListener {

        }
        bindingClass.incubatorButtonHT6.setOnClickListener {

        }
        bindingClass.incubatorButtonHT7.setOnClickListener {

        }
        bindingClass.incubatorButtonHT8.setOnClickListener {

        }

        bindingClass.incubatorButtonCM1.setOnClickListener {

        }
        bindingClass.incubatorButtonCM2.setOnClickListener {

        }
        bindingClass.incubatorButtonCM3.setOnClickListener {

        }
        bindingClass.incubatorButtonCM4.setOnClickListener {

        }
        bindingClass.incubatorButtonCM5.setOnClickListener {

        }
        bindingClass.incubatorButtonCM6.setOnClickListener {

        }
        bindingClass.incubatorButtonCM7.setOnClickListener {

        }
        bindingClass.incubatorButtonCM8.setOnClickListener {

        }
        bindingClass.incubatorButtonCM9.setOnClickListener {

        }
        bindingClass.incubatorButtonCM10.setOnClickListener {

        }
        bindingClass.incubatorButtonCM11.setOnClickListener {

        }
        bindingClass.incubatorButtonCM12.setOnClickListener {

        }
        bindingClass.incubatorButtonCM13.setOnClickListener {

        }
        bindingClass.incubatorButtonCM14.setOnClickListener {

        }
    }
}