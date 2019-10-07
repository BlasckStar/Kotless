package com.example.labicarus.kotless

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler()
        server()
        test()

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

    fun test(){
        btn_test.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }
    }
}
