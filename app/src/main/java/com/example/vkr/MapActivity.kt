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

        bindingClass.IncubatorButton1.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            startActivity(intent)
        }
        bindingClass.incubatorButton2.setOnClickListener {
            Toast.makeText(applicationContext, "Нормал 2", Toast.LENGTH_SHORT).show()
        }
        bindingClass.incubatorButton3.setOnClickListener {
            Toast.makeText(applicationContext, "Нормал 3", Toast.LENGTH_SHORT).show()
        }

        Network.getData(){
            runOnUiThread(Runnable {
                bindingClass.respons.text = it[0].toString()
                var r: String = ""
                for(d in it){
                    r += "${d.temp} ${d.hum} ${d.id_box} \n"

                }
                bindingClass.respons.text = r
            })
        }
    }
}