package com.example.vkr

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException
import android.app.Activity
import android.content.Intent
import android.os.Handler
import okhttp3.internal.wait

//TODO{
// Запарсить json https://developer.alexanderklimov.ru/android/library/retrofit.php
// Как делать get, post and maybe put https://youtu.be/3KTXD_ckAX0?t=745
// Как прально открывать новое окно
// Как хранить токен https://metanit.com/java/android/2.2.php
// Как нормально нарисовать карту
// Через какую библиотеку строить графики
// Поменять иконку
// }

typealias MyListener = (String) -> Unit
class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding

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
                Log.d("CREATION", response.body?.string().toString())
            }
        })
    }



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.loginButton.setOnClickListener {
            val login = bindingClass.loginText.text.toString()
            val password = bindingClass.passwordText.text.toString()

            Network.tokenGet(login, password) {
                if (it.isNotEmpty()){
                    runOnUiThread(Runnable {
                        var token = it
                        if ("error" in token){bindingClass.status.text = token}

                    })

                }
            }

            Handler().postDelayed({
                val intent = Intent(this, GraphActivity::class.java)
                startActivity(intent)
            }, 5000)



//            Log.d("CREATION", tokenGet(login, password))
//
//            var tokenPars = tokenGet(login, password)
////            Log.d("CREATION", tokenPars[0].toString())
//            if(tokenPars == "\"error\": \"Unauthorized\""){
//                Toast.makeText(applicationContext, "Неверный логин или пароль", Toast.LENGTH_LONG).show()
//            }
//            getInfo()
        }


    }
}