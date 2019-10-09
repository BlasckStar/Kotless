package com.example.labicarus.kotless

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.airbnb.lottie.L.debug
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

    companion object{
        val usernames: MutableList<String>? = null
        val passwords: MutableList<String>? = null
        var login: Boolean = false
        var user: String = ""

    }

    var json_getById = ""

    //----------------------------------RECYCLER CALL------------------------------//

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

    fun printView(response: JsonElement, list: MutableList<Pessoa>?){
        val gson = Gson()
        val info = gson.fromJson(response, Array<Teste>::class.java)
        for (x in 0 until info.size){
            list!!.add(Pessoa(info[x].username, info[x].email, info[x].password))
        }
    }

    fun configureCardView(context: Activity, list: MutableList<Pessoa>, recycler: RecyclerView){
        val pessoaAdapter = PessoaAdapter(context, list)
        recycler.adapter = pessoaAdapter
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.smoothScrollToPosition(list.size)
    }

    //---------------------------------------- RECYCLER INSERT -----------------------------------------//

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

    //----------------------------------------- TESTE GET COM ID --------------------------------------//
    fun getUser(username: String, password: String, list: MutableList<Pessoa>?,context: Context ){
        val call = RetrofitInitializer().serverService().getListId(username)
        call.enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body()?.let{
                    val info = it
                    getIdBack(info, list, context, password)
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Toast.makeText(context,"WTF",Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getIdBack(response: JsonElement, list: MutableList<Pessoa>?, context: Context, password: String){
        val gson = Gson()
        val info = gson.fromJson(response, Array<Teste>::class.java)
        for (x in info.indices){
            list!!.add(Pessoa(info[x].username, info[x].email, info[x].password))
        }
        if(info.size > 1){
            Toast.makeText(context, "Não existe", Toast.LENGTH_SHORT).show()
        }
        if (info.size == 1){
            Toast.makeText(context, info[0].username, Toast.LENGTH_SHORT).show()
            if(info[0].password == password){
                login = true
                user = info[0].username.toString()
                Toast.makeText(context, "Ola,$user", Toast.LENGTH_SHORT).show()
                LoginActivity.LoginClass.activity?.finish()
            }
        }
    }

    //-------------------------------------- ---------------------------------------------------------//


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

    fun callbackSplash(list: MutableList<String>?, password: MutableList<String>?){
        val call = RetrofitInitializer().serverService().getList()
        call.enqueue(object : Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body()?.let{
                    val info = it
                    val gson = Gson()
                    val jsinho = gson.fromJson(info, Array<Teste>::class.java)
                    for (x in jsinho.indices){
                        list?.add(jsinho[x].username.toString())
                        password?.add(jsinho[x].password.toString())
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

            }
        })
    }


}