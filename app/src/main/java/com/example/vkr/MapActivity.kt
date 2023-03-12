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
            intent.putExtra("inc", 1)
            startActivity(intent)
        }
        bindingClass.incubatorButton2.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("inc", 2)
            startActivity(intent)
        }
        bindingClass.incubatorButton3.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("inc", 3)
            startActivity(intent)
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