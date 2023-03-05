package com.example.vkr

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.loginButton.setOnClickListener {
            val login = bindingClass.loginText.text
            val password = bindingClass.passwordText.text

            val client = OkHttpClient()
            val credential = Credentials.basic(login.toString(), password.toString())
            val request = Request.Builder()
                .url("http://web.foodrus.ru/api/tokens")
                .addHeader("Authorization", credential)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("CREATION", "FAIL")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d("CREATION", response.body()?.string().toString())
                }
            })
//
//            val logpass = login.toString().plus(" ").plus(password.toString())
//            if (login.isNotEmpty() and password.isNotEmpty()) {
//                val toast = Toast.makeText(this, logpass, Toast.LENGTH_LONG)
//                toast.show()
//            }

        }


    }
}