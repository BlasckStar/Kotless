package com.example.labicarus.kotless

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import kotlinx.android.synthetic.main.activity_callback_server.*
import kotlinx.android.synthetic.main.activity_recycler.*
import kotlinx.android.synthetic.main.dialog.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerActivity : AppCompatActivity(){

    val pessoaList: MutableList<Pessoa> = mutableListOf()

    lateinit var pessoaAdapter: PessoaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        TesteWebClient().teste(pessoaList, this, recyclerViewPessoas)

        configureCardView()

        dialog()
        back()
    }

    fun back(){
        btn_recycler_back.setOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun configureCardView(){
        pessoaAdapter = PessoaAdapter(this, pessoaList)
        recyclerViewPessoas.adapter = pessoaAdapter
        recyclerViewPessoas.layoutManager = LinearLayoutManager(this)
        recyclerViewPessoas.smoothScrollToPosition(pessoaList.size)
    }

    fun callback(){
        val call = RetrofitInitializer().serverService().getList()
        call.enqueue(object: Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>?,
                                    response: Response<JsonElement>?) {
                response?.body()?.let{
                    val info: JsonElement = it
                    printView(info)
                    configureCardView()
                }
            }

            override fun onFailure(call: Call<JsonElement>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
                txt_calback.text = t.toString()
            }
        })
    }

    fun printView(response: JsonElement){
        val gson = Gson()
        val info = gson.fromJson(response, Array<Teste>::class.java)
        for (x in 0 until info.size){
            pessoaList.add(Pessoa(info[x].username, info[x].email, info[x].password))
        }
    }

    fun dialog(){

        Fab.setOnClickListener {
            val createdView =LayoutInflater.from(this@RecyclerActivity).inflate(R.layout.dialog,
                window.decorView as ViewGroup,
                false)
            AlertDialog.Builder(this@RecyclerActivity)
                .setTitle("Add Note")
                .setView(createdView)
                .setPositiveButton("Save", object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val username = createdView.input_username.text.toString()
                        val email = createdView.input_email.text.toString()
                        val password = createdView.input_password.text.toString()
                        val test = Pessoa(username, email, password)

                        val gson: Gson = GsonBuilder().setPrettyPrinting().create()
                        val person = gson.toJson(test)
                        TesteWebClient().insert(person)
                    }
                })
                .show()
        }

    }
}