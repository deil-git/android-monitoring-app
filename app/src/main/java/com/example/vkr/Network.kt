package com.example.vkr

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type
import java.util.*


open class Network {


    companion object {
        var token = ""
        fun tokenGet(login: String, password: String, onResult: (ServerResponse) -> Unit) {
            val client = OkHttpClient()
            val credential = Credentials.basic(login, password)
            val request = Request.Builder()
                .url("http://web.foodrus.ru/api/tokens")
                .addHeader("Authorization", credential)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("TOKEN", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    val gson = Gson()
                    val resp = gson.fromJson(
                        response.body?.string().toString(),
                        ServerResponse::class.java)
                    token = resp.token
                    onResult(resp)
                }
            })
        }

        fun getData(onResult: (Vector<ServerResponse.Data>) -> Unit) {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("http://web.foodrus.ru/api/indications/last")
                .addHeader("Authorization", "Bearer $token")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val gson = Gson()
                    val userType: Type = object : TypeToken<Vector<ServerResponse.Data?>?>() {}.type
                    val userList: Vector<ServerResponse.Data> = gson.fromJson(response.body?.string(), userType)
                    //val resp: Vector<ServerResponse.Data> = gson.fromJson(response.body?.string(), Vector<ServerResponse.Data::class.java>)

                    //Log.d("TOKEN", resp.token)
                    onResult(userList)
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
