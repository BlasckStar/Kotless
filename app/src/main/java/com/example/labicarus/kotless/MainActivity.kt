package com.example.labicarus.kotless

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        var user: String = "Login please"
        var init: Boolean = true
        val timeOut:Long = 1000 //Loading duration
    }

    //region //----- Unique variables -----\\
    var userinfo: MutableList<Employees>? = mutableListOf()
    var tokenInfo: MutableList<Tokens> = mutableListOf()
    //endregion

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //region //----- Init -----\\
        startActivity(Intent(this, SplashActivity::class.java))
        if (init){
            TesteWebClient().retrieveInfo(this, userinfo)
            TesteWebClient().testeToken(this, "primeiro", tokenInfo)
        }
        //endregion

        // region //----- Listeners -----\\
        hand()
        buttons()
        //endregion

    }

    // region //----- Listeners function -----\\
    fun buttons(){
        btn_request.setOnClickListener {
            startActivity(Intent(this, CallbackActivity::class.java))
        }

        btn_logout.setOnClickListener {
            if (!TesteWebClient.login){
                startActivity(Intent(this, LoginActivity::class.java))
            }else{
                var mainActivity = this@MainActivity; TesteWebClient().logut()
                hand()
            }
        }

        btn_recycler.setOnClickListener {
            if(TesteWebClient.userToken != ""){
                startActivity(Intent(this, RecyclerActivity::class.java))
            }else{
                Toast.makeText(this, "Você não tem permissão", Toast.LENGTH_SHORT).show()
            }
        }

        btn_options.setOnClickListener{
            if(TesteWebClient.userToken != ""){
                startActivity(Intent(this, OptionsActivity::class.java))
            }else{
                Toast.makeText(this, "Você não tem permissão", Toast.LENGTH_SHORT).show()
            }
        }
    } // Buttons clickListeners
    fun hand(){
        val handler = Handler(Looper.getMainLooper())
        handler.post(object: Runnable{
            @SuppressLint("SetTextI18n")
            override fun run() {
                if(TesteWebClient.user != "" && TesteWebClient.user != text_user.text){
                    text_user.text = TesteWebClient.user
                }
                if(TesteWebClient.user == ""){
                    text_user.text = "Logue por favor"
                }
                if(!TesteWebClient.login){
                    btn_logout.text = "Entrar"
                }else{
                   btn_logout.text = "Sair"
                }

                txt_results.text = tokenInfo.toString()
                handler.postDelayed(this, 1000)
            }
        })
    } // Hand its a updater data for components
    //endregion

    //region //----- Companion editors -----\\
    fun initSwitch(){
        if (MainActivity.init){
            MainActivity.init = false
        }
    } // Make Init False for nex calls
    //endregion

}
