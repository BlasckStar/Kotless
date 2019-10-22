package com.example.labicarus.kotless

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

interface TesteService {

    //todos os tipos de callback que o servido faz no momento estão aqui

    // region //----- LOGIN SERVICE REQUESTS -----\\
    @GET("loginService")
    fun getList(): Call<JsonElement>

    @GET("loginService")
    fun getListId(@Query("username") username: String?): Call<JsonElement>

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

    //endregion

    // region //----- TOKEN SERVICE REQUEST -----\\

    @GET("tokenService")
    fun searchEmployeeToken(@Query("token")token: String): Call<JsonElement>

    @GET("tokenService")
    fun searchTokens(): Call<JsonElement>


    //endregion

    //region //----- EMPLOYEE SERVICE REQUEST -----\\

    @GET("employeeService")
    fun searchEmployees():Call<JsonElement>

    @GET("employeeService")
    fun  searchEmployee(@Query("name") name: String?): Call<JsonElement>

    @GET("employeeService")
    fun searchEmployeeByEmail(@Query("email") email: String): Call<JsonElement>

    @POST("employeeService")
    @Headers("Content-Type: application/json")
    fun registerEmployee(@Body employee: JsonElement): Call<JsonElement>

    @DELETE("employeeService/{id}")
    fun deleteEmployee(@Path("id")id: String): Call<JsonElement>

    @FormUrlEncoded
    @PUT("employeeService/{id}")
    fun updateEmployees(@Path("id")id: String,
                        @Field("name")username: String?,
                        @Field("email")email: String?,
                        @Field("hierarchy")hierarchy: String?,
                        @Field("password")password: String?,
                        @Field("description")description: String?): Call<JsonElement>

    //endregion
}