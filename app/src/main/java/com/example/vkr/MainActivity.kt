package com.example.vkr

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException
import android.content.Intent
import android.os.Handler
import android.view.Window
import android.widget.Toast
import okhttp3.internal.wait

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
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.loginButton.setOnClickListener {
            val login = bindingClass.loginText.text.toString()
            val password = bindingClass.passwordText.text.toString()

            Network.tokenGet(login, password) {

                runOnUiThread(Runnable {
                    var token = it.token
                    var error = it.error

                    if(token.isNotEmpty()){
                        bindingClass.status.text = token
                        val intent = Intent(this, MapActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        bindingClass.status.text = error
                        Toast.makeText(applicationContext,
                            "Неверный логин или пароль",
                            Toast.LENGTH_LONG).show()
                    }
                })
            }
        }




        bindingClass.DELETEME.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }
}