package com.example.vkr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.example.vkr.databinding.ActivityLogBinding

class LogActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityLogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        bindingClass = ActivityLogBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.createButton.setOnClickListener {
            val intent = Intent(this, LogCreateActivity::class.java)
            startActivity(intent)
        }
    }
}