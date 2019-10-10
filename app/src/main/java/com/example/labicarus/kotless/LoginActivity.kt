package com.example.labicarus.kotless

import android.annotation.SuppressLint
import android.app.Activity
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

    class LoginClass{
        companion object{
            @SuppressLint("StaticFieldLeak")
            var activity: Activity? = null

            @SuppressLint("StaticFieldLeak")
            var loading: Activity? = null
        }
    }

    var fa: Activity = this

    var list: MutableList<Pessoa>? = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        //----------------Function-------------------//
        LoginClass.activity = this@LoginActivity
        //----------------buttons--------------------//
        back()
        login()
        teste()
    }

    fun back(){
        btn_login_back.setOnClickListener {
            finish()
        }
    }

    fun login(){
        btn_login_enter.setOnClickListener {

        }
    }


    fun teste(){
        btn_login_enter.setOnClickListener {
            if (input_login_username.text.toString() != "" && input_login_password.text.toString() != ""){
                val username =  input_login_username.text.toString()
                val password = input_login_password.text.toString()
                startActivity(Intent(this, SplashActivity::class.java))
                TesteWebClient().getUser(username, password, list,this)
                //finish()
            }else{
                Toast.makeText(this, "Entre com login e senha!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}