package com.example.labicarus.kotless

import com.google.gson.annotations.SerializedName

data class Teste (

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("email")
    val email: String? = null

)