package com.example.labicarus.kotless

data class Pessoa(var username: String?,
                  var email: String?,
                  var password: String?,
                  var _id: String?)

data class PessoaPost(var username: String?,
                      var email: String?,
                      var password: String?)

data class login(var username: String?)

data class TokenData(
    var _id: String,
    var token: String,
    var users: MutableList<UserData>
)

data class UserData(
    var user:String? = null
)

data class UserInfo(
    var _id: String = "",
    var name: String? = null,
    var email: String? = null,
    var hierarchy: String? = null,
    var description: String? = null
)