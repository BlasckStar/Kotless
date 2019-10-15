package com.example.labicarus.kotless

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebClient{

    companion object{
        var login: Boolean = false
        var email: String? = null
        var user: String? = null
        var userId: String = ""
        var userToken:String = ""
        var usableUserId: String = ""
        var timeOut: Long = 1000
        val gson: Gson = Gson()
    }

    ////////////////////////////////////////////////////////
    //---------------------- Requests --------------------//
    ////////////////////////////////////////////////////////

    fun callbackUsers(context: Context, usersList: MutableList<UsersData>){
        val call = RetrofitInitializer().serverService().getList()
        call.enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body()?.let{
                    val info = it
                    val users = gson.fromJson(info, Array<UsersData>::class.java)
                    for(x in users.indices) {
                        usersList.add(UsersData(users[x]._id.toString(), users[x].username.toString(), users[x].email.toString()))
                    }
                }
                Toast.makeText(context, "Requisição completa", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable?) {
                Toast.makeText(context, t?.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun callbackUser(context: Context, user: String, list: MutableList<UserData>){
        val call = RetrofitInitializer().serverService().getListId(user)
        call.enqueue(object: Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body().let {
                    val info = it
                    val user = gson.fromJson(info, Array<UserInfo>::class.java)
                    if (user.size > 1) {
                        Toast.makeText(context, "Mais de um $user", Toast.LENGTH_SHORT).show()
                    }
                    if (user.size == 1) {
                        Toast.makeText(context, "$user encontrado", Toast.LENGTH_SHORT).show()
                    }
                    if (user.isEmpty()) {
                        Toast.makeText(context, "$user não encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable?) {
                Toast.makeText(context, t?.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun callbackTokens(){

    }
    fun callbackToken(){

    }
    fun callbackDeleteUser(){

    }
    fun callbackUpdateUser(){

    }
}