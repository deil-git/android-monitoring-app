package com.example.vkr

import okhttp3.*
import java.io.IOException

open class Network {
    var token = ""

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

                token = response.body()?.string().toString()
            }
        })
    }
}