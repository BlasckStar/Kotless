package com.example.labicarus.kotless

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TesteWebClient {

    fun teste(list: MutableList<Pessoa>, context: Activity, recycler: RecyclerView){
        val call = RetrofitInitializer().serverService().getList()
        call.enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>?,
                                    response: Response<JsonElement>?) {
                response?.body()?.let{
                    val info: JsonElement = it
                    printView(info, list)
                    configureCardView(context, list, recycler)
                }
            }
            override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                Log.e("onFailure erro: ", t?.message)
            }
        })
    }

    fun printView(response: JsonElement, list: MutableList<Pessoa>){
        val gson = Gson()
        val info = gson.fromJson(response, Array<Teste>::class.java)
        for (x in 0 until info.size){
            list.add(Pessoa(info[x].username, info[x].email, info[x].password))
        }
    }

    fun configureCardView(context: Activity, list: MutableList<Pessoa>, recycler: RecyclerView){
        val pessoaAdapter = PessoaAdapter(context, list)
        recycler.adapter = pessoaAdapter
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.smoothScrollToPosition(list.size)
    }

    fun insert(pessoa: JsonElement, list: MutableList<Pessoa>, context: Activity, recycler: RecyclerView){
        val call = RetrofitInitializer().serverService().insert(pessoa)
        call.enqueue(object: Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable?) {
                Log.e("On Failure error", t?.message)
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                teste(list, context, recycler)
            }


        })
    }

    fun callback(list: MutableList<Pessoa>?, context: Activity){
        val call = RetrofitInitializer().serverService().getList()
        call.enqueue(object : Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body()?.let{
                    val info = it
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
            }
        })
    }


}