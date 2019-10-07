package com.example.labicarus.kotless

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://172.16.0.141:8080/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun serverService() : TesteService = retrofit.create(TesteService::class.java)
}