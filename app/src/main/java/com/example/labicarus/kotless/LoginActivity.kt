package com.example.labicarus.kotless

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity: AppCompatActivity() {

    companion object{
        var user:String = ""
    }
    var list: MutableList<Pessoa>? = null
    var login: Boolean = false
    var loginIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        val intent = Intent()
        //----------------Function-------------------//
        TesteWebClient().callback(list, this)
        //----------------buttons--------------------//
        back()
        login()
        userUpdate()
        teste()
    }

    var handler = Handler()

    fun back(){
        btn_login_back.setOnClickListener {
            finish()
        }
    }

    fun login(){
        btn_login_enter.setOnClickListener {

        }
    }

    fun userUpdate(){
        btn_login_enter.setOnClickListener {
        }
    }

    fun teste(){
        btn_login_enter.setOnClickListener {
            user = input_login_username.text.toString()
            finish()

        }
    }
}