package com.example.labicarus.kotless

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_test.*


class TestActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.pessoa_item)
    }

    fun teste(){
        val test: String = "{'id':1001, 'firstName':'Lokesh', 'lastName':'Gupta', 'email':'howtodoinjava@gmail.com'}"
        val gson: Gson = Gson()
        val teste: Teste = gson.fromJson(test, Teste::class.java)
        txt_test.text = teste.email
    }
}