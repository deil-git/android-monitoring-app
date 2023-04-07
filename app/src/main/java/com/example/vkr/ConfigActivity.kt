package com.example.vkr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.example.vkr.databinding.ActivityConfigBinding

class ConfigActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        bindingClass = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

    }
}