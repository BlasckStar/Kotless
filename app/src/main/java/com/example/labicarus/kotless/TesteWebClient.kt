package com.example.labicarus.kotless

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.dialog.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TesteWebClient {

    companion object{
        var login: Boolean = false
        var email: String? = null
        var user: String? = null
        var userId: String = ""
        var userToken:String = "primeiro"
        var usableUserId: String = ""
        var timeOut: Long = 1000
        val call = RetrofitInitializer().serverService()
        val gson = Gson()
        var description: String? = null
    }

    // region //----- RECYCLER REQUESTS -----\\
    fun callbackRecycler(list: MutableList<Employees>?, activity: Activity, recycler: RecyclerView){

        call.searchEmployees().enqueue(object: Callback<JsonElement>{
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

    fun printView(response: JsonElement, list: MutableList<Employees>?){
        val info = gson.fromJson(response, Array<Employees>::class.java)
        list?.clear()
        for (x in info.indices){
            list!!.add(Employees(info[x]._id, info[x].name, info[x].email, info[x].hierarchy, info[x].description))
        }
    }

    fun configureCardView(context: Activity, list: MutableList<Employees>?, recycler: RecyclerView){
        val pessoaAdapter = PessoaAdapter(context, list)
        recycler.adapter = pessoaAdapter
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.smoothScrollToPosition(list!!.size)
        stopActivity()
    }
    //endregion

    // region //----- REGISTER REQUESTS -----\\
    fun insert(json: JsonElement, list: MutableList<Employees>, activity: Activity, recycler: RecyclerView){
        call.registerEmployee(json).enqueue(object: Callback<JsonElement> {
            override fun onFailure(call: Call<JsonElement>, t: Throwable?) {
                stopActivity()
                Log.e("On Failure error", t?.message)
            }

            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                callbackRecycler(list, activity, recycler)
            }


        })
    }
    //endregion

    // region //----- LOGIN REQUESTS -----\\
    fun getUser(username: String, password: String, list: MutableList<Employees>?,context: Context){
        call.searchEmployee(username).enqueue(object: Callback<JsonElement>{
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
    fun getIdBack(response: JsonElement, list: MutableList<Employees>?, context: Context, password: String){
        val info = gson.fromJson(response, Array<Employees>::class.java)
        for (x in info.indices){
            list!!.add(Employees(info[x]._id, info[x].name, info[x].email, info[x].hierarchy, info[x].password, info[x].description))
        }
        if(info.size > 1){
            stopActivity()
            Toast.makeText(context, "Usuario/Senha inválido", Toast.LENGTH_SHORT).show()
        }
        if (info.size == 1){
            if(info[0].password == password){
                login = true
                user = info[0].name.toString()
                userId = info[0]._id.toString()
                email = info[0].email.toString()
                description = info[0].description.toString()

                callbackLoginVerification(context)
            }else{
                Toast.makeText(context, "Usuario/Senha invalido", Toast.LENGTH_SHORT).show()
                stopActivity()
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
            SplashActivity.SplashClass.activity.finish()
        }, timeOut)
    }
    fun logut(){
        user = ""
        userToken = ""
        TesteWebClient.login = false
    }
    //endregion

    // region //----- DELETE REQUESTS -----\\
    fun callbackIdToDelete(response: String,list: MutableList<Employees>, context: Context, activity: Activity, recycler: RecyclerView){
        call.searchEmployee(response).enqueue(object : Callback<JsonElement>{
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
    fun getIdToDelete(response: JsonElement, list: MutableList<Employees>?, context: Context, activity: Activity, recycler: RecyclerView){
        val info = gson.fromJson(response, Array<Employees>::class.java)
        for (x in info.indices){
            list!!.add(Employees(info[x]._id, info[x].name, info[x].email, info[x].hierarchy, info[x].description))
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
    fun delete(context: Context, list: MutableList<Employees>?, activity: Activity, recycler: RecyclerView){
            call.deleteEmployee(usableUserId).enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                Toast.makeText(context, "Usuario deletado", Toast.LENGTH_SHORT).show()
                callbackRecycler(list, activity, recycler)
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable?) {
                Toast.makeText(context,"Erro", Toast.LENGTH_SHORT).show()
            }

        })
    }
    //endregion

    // region //----- UPDATE REQUESTS -----\\
    fun getIdToUpdate(activity: Activity, context: Context, list: MutableList<Employees>?, recycler: RecyclerView, username:String){
        call.searchEmployee(username).enqueue(object: Callback<JsonElement>{
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
    fun checkUpdate(activity: Activity, context: Context, list: MutableList<Employees>?, recycler: RecyclerView, username:String, response: JsonElement){
        val info = gson.fromJson(response, Array<Employees>::class.java)
        list!!.clear()
        for (x in info.indices){
            list.add(Employees(info[x]._id, info[x].name, info[x].email, info[x].hierarchy, info[x].password, info[x].description))
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
                        val mDescription = createdView.input_dialog_description.text.toString()
                        startActivity(context, Intent(context, SplashActivity::class.java), Bundle())
                        callbackUpdate(activity, context, list, recycler, mUsername, mEmail, mPassword, info[0]._id.toString(), info[0].hierarchy.toString(), mDescription)
                    }
                })
                .show()
        }
        if(info.isEmpty()){
            Toast.makeText(context, "Usuario não existe", Toast.LENGTH_SHORT).show()
        }
    }
    fun callbackUpdate(activity: Activity, context: Context, list: MutableList<Employees>?, recycler: RecyclerView, username: String, email: String, password: String, id: String, hierarchy: String, description: String){

        var _username: String? = ""
        var _email: String? = ""
        var _password: String? = ""
        var _description: String? = ""
        var _hierarchy: String? = ""

        if (username == _username) _username = list!![0].name else _username = username

        if (email == _email) _email = list!![0].email else _email = email

        if (password == _password) _password = CryptoClient().encode(list!![0].password.toString()+ _username) else _password = CryptoClient().encode(password + _username)

        if (hierarchy == _hierarchy) _hierarchy = list!![0].hierarchy else _hierarchy = hierarchy

        if (description == _description) _description = list!![0].description else _description = description

        call.updateEmployees(id, _username, _email, _password, _hierarchy, _description).enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body()?.let{
                    val info = it
                    if(info.toString() != ""){
                        stopActivity()
                        Toast.makeText(context, "Update complete", Toast.LENGTH_SHORT).show()
                        callbackRecycler(list, activity, recycler)
                    }
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                stopActivity()
                Toast.makeText(context, "deu errado o Update", Toast.LENGTH_SHORT).show()
            }

        })
    }
    //endregion

    // region //----- Shared Preferences -----\\
    fun saveInfo(context: Context){
        val sharedPreference = SharedPreference(context)
        sharedPreference.clear()
        sharedPreference.save("username", user)
        Toast.makeText(context, "Perfil Salvo", Toast.LENGTH_SHORT).show()
    }
    fun retrieveInfo(context: Context, list: MutableList<Pessoa>?){
        val sharedPreference = SharedPreference(context)
        if (!login){
            if(sharedPreference.getValueString("username")!=null){
                val info = sharedPreference.getValueString("username").toString()
                callbackInfo(info, context, list)
                login = true
            }else{
                stopActivityLogin(context)
            }
        }
    }
    fun callbackInfo(string: String?, context: Context, list: MutableList<Pessoa>?){
        val call = RetrofitInitializer().serverService().getListId(string)
        call.enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body()?.let{
                    val info = it
                    val gson = Gson()
                    val jsinho = gson.fromJson(info, Array<Teste>::class.java)
                    list?.clear()
                    for(x in jsinho.indices){
                        list!!.add(Pessoa(jsinho[x].username, jsinho[x].email, jsinho[x].password, jsinho[x]._id))
                    }
                    user = list!![0].username.toString()
                    email = list[0].email.toString()
                    Toast.makeText(context, "Login automatico ativado", Toast.LENGTH_SHORT).show()
                    stopActivity()
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                stopActivityLogin(context)
                Toast.makeText(context, "User não existe", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun stopActivityLogin(context: Context){
        val handler = Handler()
        handler.postDelayed({
            SplashActivity.SplashClass.activity.finish()
            startActivity(context, Intent(context, LoginActivity::class.java), Bundle())
        }, timeOut)
    }
    //endregion

    // region //----- TOKENS -----\\
    fun testeToken(context: Context, _token: String, list: MutableList<TokenData>){
        call.searchEmployeeToken(_token).enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body().let{
                    val info = it
                    val ulist: MutableList<UserData> = mutableListOf()
                    val jsinho = gson.fromJson(info, Array<IdToken>::class.java)
                    for (x in jsinho.indices){
                        ulist.clear()
                        for(y in jsinho[x].users.indices) {
                            ulist.add(UserData(jsinho[x].users[y].toString()))
                        }
                        list.add(TokenData(jsinho[x]._id.toString(),jsinho[x].token.toString(), ulist))
                    }
                    Toast.makeText(context, list.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

            }
        })
    }
    fun callbackToken(context: Context, list:MutableList<IdToken>){
        call.searchTokens().enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body()?.let{
                    val info = it
                    val listin: MutableList<UserToken> = mutableListOf()
                    val jsonTokens = gson.fromJson(info, Array<IdToken>::class.java)
                    for (x in jsonTokens.indices) {
                        for(y in jsonTokens[x].users.indices){
                            listin.add(UserToken(jsonTokens[x].users[y]._id, jsonTokens[x].users[y].user))
                        }
                        list.add(IdToken(jsonTokens[x]._id.toString(), jsonTokens[x].token, listin))
                    }
                }
            }


            override fun onFailure(call: Call<JsonElement>, t: Throwable?) {
                Toast.makeText(context, t?.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
    //endregion

    // region //----- VERIFICATION REQUESTS -----\\
    fun callbackRecyclerVerification(list: MutableList<Employees>?, activity: Activity, recycler: RecyclerView){
        call.searchEmployeeToken(userToken).enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body()?.let{
                    val info = it
                    val jsonToken = gson.fromJson(info, Array<IdToken>::class.java)
                    if (jsonToken.size == 1) {
                        for(x in jsonToken[0].users.indices){
                            if (jsonToken[0].users[x].user == user){
                                callbackRecycler(list, activity, recycler)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
            }

        })
    }
    fun callbackInsertVerification(user: JsonElement, list: MutableList<Employees>, activity: Activity, recycler: RecyclerView){
        call.searchEmployeeToken(userToken).enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body()?.let {
                    val info = it
                    val jsonToken = gson.fromJson(info, Array<IdToken>::class.java)
                    if (jsonToken.size == 1) {
                        for (x in jsonToken[0].users.indices) {
                            if (jsonToken[0].users[x].user == Companion.user) {
                                insert(user, list, activity, recycler)
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable?) {
                print(t?.message)
            }

        })

    }
    fun callbackDeleteVerification(_response: String, list: MutableList<Employees>, context: Context, activity: Activity, recycler: RecyclerView){
        call.searchEmployeeToken(userToken).enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body()?.let {
                    val info = it
                    val jsonToken = gson.fromJson(info, Array<IdToken>::class.java)
                    if (jsonToken.size == 1) {
                        for (x in jsonToken[0].users.indices) {
                            if (jsonToken[0].users[x].user == Companion.user) {
                                callbackIdToDelete(_response, list, context, activity, recycler)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable?) {
                stopActivity()
                print(t?.message)
            }

        })
    }
    fun callbackUpdateVerification(activity: Activity, context: Context, list: MutableList<Employees>?, recycler: RecyclerView, username: String){
        call.searchEmployeeToken(userToken).enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body()?.let {
                    val info = it
                    val jsonToken = gson.fromJson(info, Array<IdToken>::class.java)
                    if (jsonToken.size == 1) {
                        for (x in jsonToken[0].users.indices) {
                            if (jsonToken[0].users[x].user == Companion.user) {
                                getIdToUpdate(activity, context, list, recycler, username)
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<JsonElement>, t: Throwable?) {
                print(t?.message)
            }
        })
    }
    fun callbackLoginVerification(context: Context){
        userToken = ""
        call.searchTokens().enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>?) {
                response?.body()?.let {
                    val info = it
                    val jsonResponse = gson.fromJson(info, Array<IdToken>::class.java)
                    for (x in jsonResponse.indices) {
                        for(y in jsonResponse[x].users.indices){
                            if(jsonResponse[x].users[y].user == user){
                                userToken = jsonResponse[x].token.toString()
                                Toast.makeText(context, "Ola,$user", Toast.LENGTH_SHORT).show()
                                stopActivity()
                                LoginActivity.LoginClass.activity?.finish()
                            }
                        }
                        if(userToken != "" && x.toString() == jsonResponse.indices.toString()){
                            Toast.makeText(context, "Ola, $user você não possui permissões", Toast.LENGTH_SHORT).show()
                            stopActivity()
                            LoginActivity.LoginClass.activity?.finish()

                            Toast.makeText(context, userToken, Toast.LENGTH_SHORT).show()
                        }else{
                            stopActivity()
                            LoginActivity.LoginClass.activity?.finish()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

                Toast.makeText(context, "Usuario sem licensa", Toast.LENGTH_SHORT).show()
                stopActivity()
                LoginActivity.LoginClass.activity?.finish()
            }

        })
    }
    //endregion

}