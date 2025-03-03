package com.example.labicarus.kotless

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SplashActivity: AppCompatActivity(){

    class SplashClass{
        companion object{
            @SuppressLint("StaticFieldLeak")
            var activity: Activity = Activity()

            @SuppressLint("StaticFieldLeak")
            var context: Context? = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        /*Está Activity apenas serve para indicar que está fazendo um loading de dados, normalmente eu
        chamo ela quando é nescessario fazer um callback no servidor ou algo do tipo, e elá é fechada
        por uma função que o TesteWebClient tem chamada stopActivity
        */

        SplashClass.activity = this@SplashActivity
        SplashClass.context = this
        if(MainActivity.init){
            MainActivity().initSwitch()
        }
    }
}