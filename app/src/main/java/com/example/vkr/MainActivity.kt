package com.example.vkr

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.loginButton.setOnClickListener {
            val toast = Toast.makeText(this, "Test", Toast.LENGTH_LONG)
            toast.show()
        }


    }

}