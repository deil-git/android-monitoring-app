package com.example.vkr.network

import android.util.Log
import com.example.vkr.data_structs.ConfigStruct
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.Headers.Companion.toHeaders
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.Collection


open class Network {

    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()

    companion object {
        var token = ""
        var login_g = ""
        var password_g = ""
        fun tokenGet(login: String, password: String, onResult: (ServerResponse) -> Unit) {
            login_g = login
            password_g = password
            val client = OkHttpClient()
            val credential = Credentials.basic(login, password)
            val request = Request.Builder()
                .url(HttpRoutes.Token)
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

        fun getData(incubNum :String, typeGraph :String, promStart :String, promEnd :String, onResult: (Vector<ServerResponse.Data>) -> Unit) {
            val client = OkHttpClient()
            lateinit var request: Request

            if(typeGraph == "RealTime"){
                request = Request.Builder()
                    .url(HttpRoutes.Indications + incubNum)
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            }
            else{
                request = Request.Builder()
                    //.url("https://web.foodrus.ru/api/indications/JW2?start=2023-04-04T22:09:16&end=2023-04-04T22:17:16")
                    .url(HttpRoutes.Indications + incubNum + HttpRoutes.PromTimeStart + promStart + HttpRoutes.PromTimeEnd + promEnd)
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            }

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {
                    val gson = Gson()
                    val userType: Type = object : TypeToken<Vector<ServerResponse.Data?>?>() {}.type
                    var userList: Vector<ServerResponse.Data> = Vector<ServerResponse.Data>()

                    try {
                        userList = gson.fromJson(response.body?.string(), userType)
                    }
                    catch (e:Exception) {
                        Log.d("getDataError", e.toString())
                        tokenGet(login_g, password_g) {
                            token = it.token
                        }
                    }
                    onResult(userList)
                }
            })
        }

        fun getDevices(onResult: (ServerResponse.AddressResponse) -> Unit) {
            val client = OkHttpClient()

            var request: Request = Request.Builder()
                .url(HttpRoutes.Config)
                .addHeader("Authorization", "Bearer $token")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {
                    val gson = Gson()
                    val userType: Type = object : TypeToken<ServerResponse.AddressResponse?>() {}.type
                    var userList: ServerResponse.AddressResponse = ServerResponse.AddressResponse()

                    try {
                        userList = gson.fromJson(response.body?.string(), userType)
                    }
                    catch (e:Exception) {
                        Log.d("debug0", e.toString())
                        tokenGet(login_g, password_g) {
                            token = it.token
                        }
                    }
                    onResult(userList)
                }
            })
        }


        fun sendFCM(FCMtoken: String, onResult: (String) -> Unit) {
            val client = OkHttpClient()

            val data = "{ \"fcm\" : \"$FCMtoken\" }"
            Log.d("FTOKEN", data)
            val body: RequestBody = RequestBody.create(
                "application/json".toMediaTypeOrNull(), data
            )

            val request = Request.Builder()
                .url(HttpRoutes.FCMtoken)
                .addHeader("Authorization", "Bearer $token")
                .post(body)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                    //val resp: Vector<ServerResponse.Data> = gson.fromJson(response.body?.string(), Vector<ServerResponse.Data::class.java>)
                    val res = response.body?.string().toString()
                    Log.d("FTOKEN", res)
                    onResult(res)
                }
            })
        }

        fun sendConfig(data: MutableList<ConfigStruct>, onResult: (String) -> Unit){
            val gson = Gson()
            var dataList = gson.toJson(mapOf("data" to data))
            val client = OkHttpClient()



            val body: RequestBody = RequestBody.create(
                "application/json".toMediaTypeOrNull(), dataList
            )

            val request = Request.Builder()
                .url("https://web.foodrus.ru/api/config/replace")
                .addHeader("Authorization", "Bearer $token")
                .post(body)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {

                    //val resp: Vector<ServerResponse.Data> = gson.fromJson(response.body?.string(), Vector<ServerResponse.Data::class.java>)
                    val res = response.body?.string().toString()
                    Log.d("debug0", res)
                    onResult(res)
                }
            })

            Log.d("debug0", dataList.toString());

        }
    }
}
