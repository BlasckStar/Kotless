package com.example.labicarus.kotless

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_callback_server.*

class CallbackActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_callback_server)

        //region //----- LISTENERS -----\\
        back()
        //endregion

        //region //----- ATUALIZATION -----\\
        attInfo()
        //endregion

    }

    //region //----- LISTENERS FUNCTIONS -----\\
    fun back(){
        btn_callback_back.setOnClickListener {
            finish()
        }
    }
    //endregion

    //region //----- ATUALIZATION FUNCTIONS -----\\
    fun attInfo(){
        recycler_txt_name.text = TesteWebClient.user
        recycler_text_email.text = TesteWebClient.email
        recycler_text_description.text = TesteWebClient.description
    }
    //endregion

}