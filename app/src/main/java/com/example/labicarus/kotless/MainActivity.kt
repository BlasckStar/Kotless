package com.example.labicarus.kotless

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
       var user: String = "Login please"
    }


    var list:MutableList<String>? = null
    var plist:MutableList<String>? = null

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hand()
        TesteWebClient().callbackSplash(list, plist)
        print(list)
        startActivity(Intent(this, LoginActivity::class.java))
        recycler()
        server()
        loginButton()
    }

    fun recycler(){
        btn_recycler.setOnClickListener {
            val intent = Intent(this, RecyclerActivity::class.java)
            startActivity(intent)
        }
    }

    fun server(){
        btn_request.setOnClickListener {
            val intent = Intent(this, CallbackActivity::class.java)
            startActivity(intent)
        }
    }

    fun loginButton(){
        btn_login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun hand(){
        val handler = Handler(Looper.getMainLooper())
        handler.post(object: Runnable{
            override fun run() {
                if(TesteWebClient.user != "" && TesteWebClient.user != text_user.text){
                    text_user.text = TesteWebClient.user
                }
                handler.postDelayed(this, 1000)
            }
        })
    }
}
