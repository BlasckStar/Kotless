package com.example.labicarus.kotless

import android.app.Activity
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
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_callback_server.*
import kotlinx.android.synthetic.main.activity_recycler.*
import kotlinx.android.synthetic.main.dialog.view.*
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
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

        dialog(this)
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

    fun dialog(conext: Activity){

        Fab.setOnClickListener {
            val createdView =LayoutInflater.from(this@RecyclerActivity).inflate(R.layout.dialog,
                window.decorView as ViewGroup,
                false)
            AlertDialog.Builder(this@RecyclerActivity)
                .setTitle("Add Note")
                .setView(createdView)
                .setPositiveButton("Save", object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        //--------------------------- Data ------------------------//
                        val username = createdView.input_username.text.toString()
                        val email = createdView.input_email.text.toString()
                        val password = createdView.input_password.text.toString()
                        val test = Pessoa(username, email, password)

                        //--------------------------- Convert ---------------------//

                        val gson = Gson()
                        val jsinho = gson.toJsonTree(test)
                        TesteWebClient().insert(jsinho, pessoaList, conext, recyclerViewPessoas)

                    }
                })
                .show()
        }

    }
}