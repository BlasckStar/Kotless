package com.example.labicarus.kotless

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.setup_activity.*

class OptionsActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setup_activity)

        //region //----- LISTENERS -----\\
        buttons()
        //endregion
    }

    //region //----- LISTENERS FUNCTION -----\\
    fun buttons(){
        btn_options_save.setOnClickListener{
            TesteWebClient().saveInfo(this)
        }
        btn_option_clear.setOnClickListener{
            SharedPreference(this).clear()
            Toast.makeText(this, "Limpeza de perfil completa", Toast.LENGTH_SHORT).show()
        }
        btn_options_back.setOnClickListener{
            finish()
        }
    }
    //endregion

}