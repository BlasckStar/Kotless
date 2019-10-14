package com.example.labicarus.kotless

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.setup_activity.*

class OptionsActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setup_activity)
        buttons()
    }

    fun buttons(){
        btn_options_save.setOnClickListener{
            TesteWebClient().saveInfo(this)
        }
        btn_option_clear.setOnClickListener{
            SharedPreference(this).clear()
        }
        btn_options_back.setOnClickListener{
            finish()
        }
    }
}