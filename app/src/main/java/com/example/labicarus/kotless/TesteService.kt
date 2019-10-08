package com.example.labicarus.kotless

import com.google.gson.JsonElement
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface TesteService {
    @GET("loginService")
    @Headers("Content-Type: application/json")
    fun getList(): Call<JsonElement>

    @POST("loginService")
    @Headers("Content-Type: application/json")
    fun insert(@Body pessoa: JsonElement): Call<JsonElement>
}