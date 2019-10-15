package com.example.labicarus.kotless

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
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


    var userinfo: MutableList<Pessoa>? = mutableListOf()
    var tokenInfo: MutableList<TokenData> = mutableListOf()
    var list:MutableList<String>? = null
    var plist:MutableList<String>? = null


    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, SplashActivity::class.java))
        if (init){
            TesteWebClient().retrieveInfo(this, userinfo)
        }
        hand()
        TesteWebClient().callbackSplash(list, plist)
        TesteWebClient().testeToken(this, "primeiro", tokenInfo)
        logout()
        recycler()
        server()
        options()
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }

                // Process the messages array.

                Toast.makeText(this, messages.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun recycler(){
        btn_recycler.setOnClickListener {
            if(TesteWebClient.login){
                val intent = Intent(this, RecyclerActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Efetue o login!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun server(){
        btn_request.setOnClickListener {
            val intent = Intent(this, CallbackActivity::class.java)
            startActivity(intent)
        }
    }
    fun logout(){
        btn_logout.setOnClickListener {
            if (!TesteWebClient.login){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{
                var mainActivity = this@MainActivity; TesteWebClient().logut()
                hand()
            }
        }
    }
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
    }
    fun initSwitch(){
        if (MainActivity.init){
            MainActivity.init = false
        }
    }
    fun options(){
        btn_options.setOnClickListener{
            startActivity(Intent(this, OptionsActivity::class.java))
        }
    }

    fun nfc(context: Context){
        var NfcAdapter = NfcAdapter.getDefaultAdapter(this)
    }

    fun teste(){
        txt_results.text = tokenInfo.toString()
    }
}
