package com.example.vkr.network

import android.util.Log
import com.example.vkr.MainActivity
import com.example.vkr.data_structs.ConfigStruct
import com.example.vkr.data_structs.CorrectStruct
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.lang.reflect.Type
import java.util.*
import kotlin.properties.Delegates

open class Network {

    companion object {
        var token = ""
        var login_g = ""
        var password_g = ""
        var err by Delegates.notNull<Boolean>()

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
                    Log.d("tokenGetError", e.toString())
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

        fun getData(incubNum :String, typeGraph :String, logBool :String, logId :String, promStart :String, promEnd :String, onResult: (Vector<ServerResponse.Data>) -> Unit) {
            val client = OkHttpClient()
            lateinit var request: Request

            if(typeGraph == "RealTime" && logBool == "0"){
                request = Request.Builder()
                    .url(HttpRoutes.Indications + incubNum)
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            }
            if(typeGraph == "PromTime" && logBool == "0"){
                request = Request.Builder()
                    .url(HttpRoutes.Indications + incubNum + HttpRoutes.PromTimeStart + promStart + HttpRoutes.PromTimeEnd + promEnd)
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            }
            if(logBool == "1"){
                request = Request.Builder()
                    .url(HttpRoutes.Logs + "/" + logId)
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

        fun getAddress(onResult: (AddressResponse) -> Unit) {
            token = MainActivity.AppPreferences.token.toString()
            err = false
            val client = OkHttpClient()

            var request: Request = Request.Builder()
                .url(HttpRoutes.Config)
                .addHeader("Authorization", "Bearer $token")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {
                    val gson = Gson()
                    val userType: Type = object : TypeToken<AddressResponse?>() {}.type
                    var userList: AddressResponse = AddressResponse()
                    var s = response.body?.string()
                    userList = gson.fromJson(s, userType)

                    if(s.toString().contains("Unauthorized")) {
                        Log.d("getAddressError", "Error")
                        err = true
                        userList.err = err
                    }
                    userList.err = err
                    onResult(userList)
                }
            })
        }

        fun getDevices(onResult: (DeviceResponce) -> Unit) {
            token = MainActivity.AppPreferences.token.toString()
            val client = OkHttpClient()

            var request: Request = Request.Builder()
                .url(HttpRoutes.Devices)
                .addHeader("Authorization", "Bearer $token")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {
                    val gson = Gson()
                    val userType: Type = object : TypeToken<DeviceResponce?>() {}.type
                    var userList: DeviceResponce = DeviceResponce()

                    try {
                        var s = response.body?.string()
                        userList = gson.fromJson(s, userType)
                    }
                    catch (e:Exception) {
                        Log.d("getDevicesError", e.toString())
                    }
                    onResult(userList)
                }
            })
        }

        fun getLogs(onResult: (LogResponce) -> Unit) {
            token = MainActivity.AppPreferences.token.toString()
            val client = OkHttpClient()

            var request: Request = Request.Builder()
                .url(HttpRoutes.Logs)
                .addHeader("Authorization", "Bearer $token")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {
                    val gson = Gson()
                    val userType: Type = object : TypeToken<LogResponce?>() {}.type
                    var userList: LogResponce = LogResponce()

                    try {
                        var s = response.body?.string()
                        userList = gson.fromJson(s, userType)
                    }
                    catch (e:Exception) {
                        Log.d("getLogsError", e.toString())
                        tokenGet(login_g, password_g) {
                            token = it.token
                        }
                    }
                    onResult(userList)
                }
            })
        }

        fun deleteLogs(logId :String) {
            val client = OkHttpClient()
            lateinit var request: Request

            request = Request.Builder()
                .url(HttpRoutes.Logs + "/delete/" + logId)
                .addHeader("Authorization", "Bearer $token")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {}
            })
        }

        fun getBoxes(onResult: (BoxResponce) -> Unit) {
            token = MainActivity.AppPreferences.token.toString()
            val client = OkHttpClient()

            var request: Request = Request.Builder()
                .url(HttpRoutes.Boxes)
                .addHeader("Authorization", "Bearer $token")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {
                    val gson = Gson()
                    val userType: Type = object : TypeToken<BoxResponce?>() {}.type
                    var userList: BoxResponce = BoxResponce()

                    try {
                        var s = response.body?.string()
                        userList = gson.fromJson(s, userType)
                    }
                    catch (e:Exception) {
                        Log.d("getBoxesError", e.toString())
                        tokenGet(login_g, password_g) {
                            token = it.token
                        }
                    }
                    onResult(userList)
                }
            })
        }

        fun sendFCM(FCMtoken: String, onResult: (String) -> Unit) {
            token = MainActivity.AppPreferences.token.toString()
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
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {
                    val res = response.body?.string().toString()
                    Log.d("FTOKEN", res)
                    onResult(res)
                }
            })
        }

        fun sendConfig(data: MutableList<ConfigStruct>, onResult: (String) -> Unit){
            token = MainActivity.AppPreferences.token.toString()
            val gson = Gson()
            var dataList = gson.toJson(mapOf("box" to data))
            val client = OkHttpClient()

            val body: RequestBody = RequestBody.create(
                "application/json".toMediaTypeOrNull(), dataList
            )

            val request = Request.Builder()
                .url(HttpRoutes.Replace)
                .addHeader("Authorization", "Bearer $token")
                .post(body)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {
                    val res = response.body?.string().toString()
                    onResult(res)
                }
            })
        }

        fun sendCorect(data: MutableList<CorrectStruct>, onResult: (String) -> Unit){
            token = MainActivity.AppPreferences.token.toString()
            val gson = Gson()
            var dataList = gson.toJson(mapOf("device" to data))
            val client = OkHttpClient()

            val body: RequestBody = RequestBody.create(
                "application/json".toMediaTypeOrNull(), dataList
            )

            val request = Request.Builder()
                .url(HttpRoutes.Replace)
                .addHeader("Authorization", "Bearer $token")
                .post(body)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}

                override fun onResponse(call: Call, response: Response) {
                    val res = response.body?.string().toString()
                    onResult(res)
                }
            })
        }
    }
}
