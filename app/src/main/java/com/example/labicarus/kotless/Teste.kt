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

data class UsersData (

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

data class IdToken(

    @field:SerializedName("_id")
    @Expose
    val _id: String? = null,

    @field:SerializedName("token")
    @Expose
    val token: String? = null,

    @field:SerializedName("users")
    @Expose
    val users: MutableList<UserToken> = mutableListOf()
)

data class UserToken(

    @field:SerializedName("_id")
    @Expose
    val _id: String? = null,

    @field:SerializedName("user")
    @Expose
    val user: String? = null
)//