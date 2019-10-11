package com.example.labicarus.kotless

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.airbnb.lottie.L.debug
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_recycler.*
import kotlinx.android.synthetic.main.activity_recycler.view.*
import kotlinx.android.synthetic.main.dialog.view.*
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
        var email: String = ""
        var userId: String = ""
        var usableUserId: String = ""
        var timeOut: Long = 500
    }

    var json_getById = ""

    //----------------------------------RECYCLER CALL------------------------------//
    fun callbackRecycler(list: MutableList<Pessoa>?, activity: Activity, recycler: RecyclerView,context: Context ){
        val call = RetrofitInitializer().serverService().getList()
        call.enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>?,
                                    response: Response<JsonElement>?) {
                response?.body()?.let{
                    val info: JsonElement = it
                    printView(info, list)
                    configureCardView(activity, list, recycler)
                }
            }
            override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                stopActivity()
                Log.e("onFailure error: ", t?.message)
            }
        })
    }

    fun printView(response: JsonElement, list: MutableList<Pessoa>?){
        val gson = Gson()
        val info = gson.fromJson(response, Array<Teste>::class.java)
        list?.clear()
        for (x in info.indices){
            list!!.add(Pessoa(info[x].username, info[x].email, info[x].password, info[x]._id))
        }
    }

    fun configureCardView(context: Activity, list: MutableList<Pessoa>?, recycler: RecyclerView){
        val pessoaAdapter = PessoaAdapter(context, list)
        recycler.adapter = pessoaAdapter
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.smoothScrollToPosition(list!!.size)
        stopActivity()
    }

    //---------------------------------------- RECYCLER INSERT -----------------------------------------//

    fun insert(pessoa: JsonElement, list: MutableList<Pessoa>, activity: Activity, recycler: RecyclerView, context: Context){
        val call = RetrofitInitializer().serverService().insert(pessoa)
        call.enqueue(object: Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable?) {
                stopActivity()
                Log.e("On Failure error", t?.message)
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                callbackRecycler(list, activity, recycler, context)
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
                stopActivity()
                Toast.makeText(context,"WTF",Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getIdBack(response: JsonElement, list: MutableList<Pessoa>?, context: Context, password: String){
        val gson = Gson()
        val info = gson.fromJson(response, Array<Teste>::class.java)
        for (x in info.indices){
            list!!.add(Pessoa(info[x].username, info[x].email, info[x].password, info[x]._id))
        }
        if(info.size > 1){
            stopActivity()
            Toast.makeText(context, "Usuario/Senha inválido", Toast.LENGTH_SHORT).show()
        }
        if (info.size == 1){
            if(info[0].password == password){
                login = true
                user = info[0].username.toString()
                userId = info[0]._id.toString()
                email = info[0].email.toString()
                Toast.makeText(context, "Ola,$user", Toast.LENGTH_SHORT).show()
                stopActivity()
                LoginActivity.LoginClass.activity?.finish()
            }else{
                stopActivity()
                Toast.makeText(context, "Usuario/Senha inválido", Toast.LENGTH_SHORT).show()
            }
        }
        if(info.isEmpty()){
            stopActivity()
            Toast.makeText(context, "Usuario/Senha inválido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopActivity(){
        val handler = Handler()
        handler.postDelayed({
            SplashActivity.SplashClass.activity!!.finish()
        }, timeOut)
    }

    fun logut(){
        TesteWebClient.user = ""
        TesteWebClient.login = false
    }

    //-------------------------------------- Delete query ---------------------------------------------------------//
    fun callbackIdToDelete(response: String,list: MutableList<Pessoa>, context: Context, activity: Activity, recycler: RecyclerView){
        val call = RetrofitInitializer().serverService().getListId(response)
        call.enqueue(object : Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body()?.let{
                    val info = it
                    getIdToDelete(info, list, context, activity, recycler)
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

            }
        })
    }

    fun getIdToDelete(response: JsonElement, list: MutableList<Pessoa>?, context: Context, activity: Activity, recycler: RecyclerView){
        val gson = Gson()
        val info = gson.fromJson(response, Array<Teste>::class.java)
        for (x in info.indices){
            list!!.add(Pessoa(info[x].username, info[x].email, info[x].password, info[x]._id))
        }
        if(info.size > 1){
            Toast.makeText(context, "Mais de um usuario", Toast.LENGTH_SHORT).show()
        }
        if (info.size == 1){
            usableUserId = info[0]._id.toString()
            stopActivity()
            delete(context, list, activity, recycler)
        }
        if(info.isEmpty()){
            Toast.makeText(context, "Usuario não existe", Toast.LENGTH_SHORT).show()
        }
    }

    fun delete(context: Context, list: MutableList<Pessoa>?, activity: Activity, recycler: RecyclerView){
        val call = RetrofitInitializer().serverService().delete(usableUserId)
        call.enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                Toast.makeText(context, "Usuario deletado", Toast.LENGTH_SHORT).show()
                callbackRecycler(list, activity, recycler, context)
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable?) {
                Toast.makeText(context,"Erro", Toast.LENGTH_SHORT).show()
            }

        })
    }


    //--------------------------- Teste PUT --------------------------------------------------------//

    fun getIdToUpdate(activity: Activity, context: Context, list: MutableList<Pessoa>?, recycler: RecyclerView, username:String){
        val call = RetrofitInitializer().serverService().getListId(username)
        call.enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body()?.let{
                    val info = it
                    stopActivity()
                    checkUpdate(activity, context, list, recycler, username,info)
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

            }
        })
    }

    fun checkUpdate(activity: Activity, context: Context, list: MutableList<Pessoa>?, recycler: RecyclerView, username:String, response: JsonElement){

        val gson = Gson()
        val info = gson.fromJson(response, Array<Teste>::class.java)
        for (x in info.indices){
            list!!.add(Pessoa(info[x].username, info[x].email, info[x].password, info[x]._id))
        }
        if(info.size > 1){
            Toast.makeText(context, "Mais de um usuario", Toast.LENGTH_SHORT).show()
        }
        if (info.size == 1){
            usableUserId = info[0]._id.toString()
            stopActivity()
            val createdView = LayoutInflater.from(context).inflate(R.layout.dialog,
                activity.window.decorView as ViewGroup,
                false)
            AlertDialog.Builder(context)
                .setTitle("Update user")
                .setView(createdView)
                .setPositiveButton("Update", object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        //--------------------------- Data ------------------------//
                        val mUsername = createdView.update_input_username.text.toString()
                        val mEmail = createdView.input_email.text.toString()
                        val mPassword = createdView.input_password.text.toString()
                        startActivity(context, Intent(context, SplashActivity::class.java), Bundle())
                        callbackUpdate(activity, context, list, recycler, mUsername, mEmail, mPassword, info[0]._id.toString())
                    }
                })
                .show()
        }
        if(info.isEmpty()){
            Toast.makeText(context, "Usuario não existe", Toast.LENGTH_SHORT).show()
        }
    }

    fun callbackUpdate(activity: Activity, context: Context, list: MutableList<Pessoa>?, recycler: RecyclerView, username: String, email: String, password: String, id: String){

        var _username: String? = ""
        var _email: String? = ""
        var _password:String? = ""

        if (username == _username) _username = list!![0].username else _username = username

        if (email == _email) _email = list!![0].email else _email = email

        if (password == _password) _password = list!![0].password else _password = password


        val call = RetrofitInitializer().serverService().update(id, _username, _email, _password)
        call.enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body()?.let{
                    val info = it
                    if(info.toString() != ""){
                        stopActivity()
                        Toast.makeText(context, "Update complete", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                stopActivity()
                Toast.makeText(context, "deu errado o Update", Toast.LENGTH_SHORT).show()
            }

        })
    }
    //--------------------------- Teste PUT --------------------------------------------------------//

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