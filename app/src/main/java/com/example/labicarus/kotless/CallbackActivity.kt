package com.example.labicarus.kotless

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_callback_server.*
import okhttp3.ResponseBody

class CallbackActivity: AppCompatActivity() {

    lateinit var info: ResponseBody

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_callback_server)

        //callback()

        back()
    }

    fun back(){
        btn_callback_back.setOnClickListener {
            finish()
        }
    }

   /* fun callback(){
        val call = RetrofitInitializer().serverService().getList()
        call.enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>?,
                                    response: Response<JsonElement>?) {
                response?.body()?.let{
                    val info: JsonElement= it
                    printView(info)
                }

            }

            override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                txt_calback.text = t.toString()
            }
        })
    }*/

    @SuppressLint("SetTextI18n")
    fun printView(response: JsonElement){

        val gson = Gson()
        val info = gson.fromJson(response, Array<Teste>::class.java)
        txt_calback.text = info[0].username
    }

    fun CardView(){
        info.contentType()
    }

    /*fun printViewRue(response: ResponseBody){
        val gson = Gson()
        Teste = son.fromJson(response, Teste::class.java)
    }*/



}