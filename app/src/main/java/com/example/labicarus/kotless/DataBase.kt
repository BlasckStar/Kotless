package com.example.labicarus.kotless

import com.google.gson.annotations.SerializedName

data class Employees(

    @field:SerializedName("_id")
    var _id: String? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("email")
    var email: String? = null,

    @field:SerializedName("hierarchy")
    var hierarchy: String? = null,

    @field:SerializedName("password")
    var password: String? = null,

    @field:SerializedName("description")
    var description: String? = null

)

/*  Para dar um POST no servidor não é nescessario haver uma field _id pq o servidor que automaticamente
    cria ela então este employeePost foi nescessario ser criado apenas para fazer o POST no servidor
*/
data class EmployeePost
    (

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("email")
    var email: String? = null,

    @field:SerializedName("hierarchy")
    var hierarchy: String? = null,

    @field:SerializedName("password")
    var password: String? = null,

    @field:SerializedName("description")
    var description: String? = null

)

data class Tokens(

    @field:SerializedName("_id")
    var _id: String? = null,

    @field:SerializedName("token")
    var token: String? = null,

    @field:SerializedName("users")
    var users: MutableList<TokenUser> = mutableListOf()

)

data class TokenUser(
    @field:SerializedName("user")
    var user: String? = null
)