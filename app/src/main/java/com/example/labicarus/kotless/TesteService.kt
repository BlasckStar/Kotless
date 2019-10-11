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

    @GET("loginService")
    fun getListId(@Query("username") username: String): Call<JsonElement>

    @POST("loginService")
    @Headers("Content-Type: application/json")
    fun insert(@Body pessoa: JsonElement): Call<JsonElement>

    @DELETE("loginService/{id}")
    fun delete(@Path("id")pessoa: String): Call<JsonElement>

    @FormUrlEncoded
    @PUT("loginService/{id}")
    fun update(@Path("id")pessoa: String,
               @Field("username")username: String?,
               @Field("email")email: String?,
               @Field("password")password: String?): Call<JsonElement>
}