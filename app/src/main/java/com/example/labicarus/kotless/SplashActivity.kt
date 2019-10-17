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
        SplashClass.activity = this@SplashActivity
        SplashClass.context = this
        if(MainActivity.init){
            MainActivity().initSwitch()

        }
    }
}