package com.example.vkr

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

//TODO{
// Запарсить json
// Как делать get, post and maybe put
// Как прально открывать новое окно
// Как хранить токен https://metanit.com/java/android/2.2.php
// Как нормально нарисовать карту
// Через какую библиотеку строить графики
// Поменять иконку
// }

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding

    fun tokenGet(login: String, password: String): String {
        var token: String = ""
        val client = OkHttpClient()
        val credential = Credentials.basic(login, password)
        val request = Request.Builder()
            .url("http://web.foodrus.ru/api/tokens")
            .addHeader("Authorization", credential)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                token = response.body()?.string().toString()
            }
        })

        return token

    }
    fun getInfo(){ //можно сюда передавать аргумент того что хочешеь отобразить а подефолту какой-нибудь ondefault поставить за 24
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://web.foodrus.ru/api/indications/CM6?start=2023-02-27T00:00:00&end=2023-03-01T00:00:00")
            .addHeader("Authorization", "Bearer " + "qEhfAwF3GUklK+tVfF6Cd5wbTePySz+n")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("CREATION", "FAIL")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("CREATION", response.body()?.string().toString())
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.loginButton.setOnClickListener {
            val login = bindingClass.loginText.text.toString()
            val password = bindingClass.passwordText.text.toString()

            Log.d("CREATION", tokenGet(login, password))
            getInfo()
//            Toast.makeText(applicationContext, "Неверный логин или пароль", Toast.LENGTH_LONG).show()

        }


    }
}