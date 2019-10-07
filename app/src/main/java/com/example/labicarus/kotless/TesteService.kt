package com.example.labicarus.kotless

import com.google.gson.JsonElement
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TesteService {
    @GET("loginService")
    fun getList(): Call<JsonElement>

    @POST("loginService")
    fun insert(@Body pessoa: RequestBody): Call<RequestBody>
}