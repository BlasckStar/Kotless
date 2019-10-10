package com.example.labicarus.kotless

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Teste (

    @field:SerializedName("_id")
    @Expose
    val _id: String? = null,

    @field:SerializedName("username")
    @Expose
    val username: String? = null,

    @field:SerializedName("password")
    @Expose
    val password: String? = null,

    @field:SerializedName("email")
    @Expose
    val email: String? = null

)