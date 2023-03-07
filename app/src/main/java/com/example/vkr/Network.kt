package com.example.vkr

import android.util.Log
import okhttp3.*
import java.io.IOException

open class Network {


    companion object {
        var token = ""
        fun tokenGet(login: String, password: String, onResult: (String) -> Unit) {
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

                    token = response.body?.string().toString()
                    Log.d("TOKEN", token)
                    onResult(token)
                }
            })
        }
    }
}



/*
fun tokenGet(login: String, password: String) {
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

            Network.token = response.body()?.string().toString()
        }
    })
}*/
