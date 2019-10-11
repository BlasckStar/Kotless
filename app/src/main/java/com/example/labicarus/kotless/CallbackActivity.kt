package com.example.labicarus.kotless

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_callback_server.*

class CallbackActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_callback_server)
        back()
        attInfo()
        del()
    }

    fun back(){
        btn_callback_back.setOnClickListener {
            finish()
        }
    }
    fun del(){
        btn_callback_convert.setOnClickListener{
            SharedPreference(this).clear()
        }
    }

    fun attInfo(){
        recycler_txt_name.text = TesteWebClient.user
        recycler_text_email.text = TesteWebClient.email
    }
}