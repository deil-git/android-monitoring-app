package com.example.vkr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vkr.databinding.ActivityGraphBinding

class GraphActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityGraphBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.BackButton.setOnClickListener {
            finish()
        }
    }
}