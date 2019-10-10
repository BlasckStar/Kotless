package com.example.labicarus.kotless

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SplashActivity: AppCompatActivity(){

    class SplashClass{
        companion object{
            @SuppressLint("StaticFieldLeak")
            var activity: Activity? = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        SplashClass.activity = this@SplashActivity
        MainActivity().initSwitch()
    }
}