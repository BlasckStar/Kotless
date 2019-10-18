package com.example.labicarus.kotless

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://172.16.0.141:8080/api/") // URL base
        .addConverterFactory(GsonConverterFactory.create()) //conversor utilizado foi o Gson mas e possivel mudar para outros
        .build()

    fun serverService() : TesteService = retrofit.create(TesteService::class.java)// Aqui ele utiliza todas as funções passadas antes
}