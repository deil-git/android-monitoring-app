package com.example.vkr

import retrofit2.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @POST("/api/tokens")
    suspend fun getToken(login: String, password: String) : Response <ResponseBody>
}