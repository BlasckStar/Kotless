package com.example.labicarus.kotless

data class Pessoa(var username: String?,
                  var email: String?,
                  var password: String?,
                  var _id: String?)

data class PessoaPost(var username: String?,
                      var email: String?,
                      var password: String?)

data class login(var username: String?)