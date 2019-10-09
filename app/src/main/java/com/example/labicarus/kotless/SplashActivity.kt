package com.example.labicarus.kotless

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class SplashActivity: AppCompatActivity(){

    val timeOut:Long = 3000 //Loading duration
    var json: String = ""

    val list: MutableList<Pessoa>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        invokeActivity()
    }

    fun invokeActivity(){
        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, timeOut)
    }
}