package com.example.labicarus.kotless

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_recycler.*
import kotlinx.android.synthetic.main.dialog.view.*

class RecyclerActivity : AppCompatActivity(){

    val pessoaList: MutableList<Pessoa> = mutableListOf()
    val list: MutableList<Pessoa> = mutableListOf()

    lateinit var pessoaAdapter: PessoaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        TesteWebClient().callbackRecycerVerification(pessoaList, this, recyclerViewPessoas)
        configureCardView()

        dialog(this, this)
        deleteDialog(this, this)
        updateDialog(this, this)
        back()
    }

    fun back(){
        btn_recycler_back.setOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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

    fun dialog(activity: Activity, context: Context){

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
                        val username = createdView.update_input_username.text.toString()
                        val email = createdView.input_email.text.toString()
                        val password = CryptoClient().decoded(createdView.input_password.text.toString() + username)
                        val test = PessoaPost(username, email, password)
                        //--------------------------- Convert ---------------------//
                        val gson = Gson()
                        val jsinho = gson.toJsonTree(test)
                        startActivity(Intent(this@RecyclerActivity, SplashActivity::class.java))
                        TesteWebClient().insert(jsinho, pessoaList, activity, recyclerViewPessoas)
                    }
                })
                .show()
        }
    }

    fun deleteDialog(activity: Activity, context: Context){
        btn_recycler_delete.setOnClickListener{
            Toast.makeText(context, "Não funcionou", Toast.LENGTH_SHORT).show()

            val createdView =LayoutInflater.from(this@RecyclerActivity).inflate(R.layout.update_dialog,
                window.decorView as ViewGroup,
                false)
            AlertDialog.Builder(this@RecyclerActivity)
                .setTitle("Delete User")
                .setView(createdView)
                .setPositiveButton("Delete", object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        //--------------------------- Data ------------------------//
                        val username = createdView.update_input_username.text.toString()
                        startActivity(Intent(this@RecyclerActivity, SplashActivity::class.java))
                        TesteWebClient().callbackIdToDelete(username, list, context, this@RecyclerActivity, recyclerViewPessoas)
                    }
                })
                .show()
        }
    }

    fun updateDialog(activity: Activity, context: Context){
        btn_recycler_update.setOnClickListener{
            val createdView =LayoutInflater.from(this@RecyclerActivity).inflate(R.layout.update_dialog,
                window.decorView as ViewGroup,
                false)
            AlertDialog.Builder(this@RecyclerActivity)
                .setTitle("Entre com o seu usuario")
                .setView(createdView)
                .setPositiveButton("Update", object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        //--------------------------- Data ------------------------//
                        val username = createdView.update_input_username.text.toString()
                        startActivity(Intent(this@RecyclerActivity, SplashActivity::class.java))
                        TesteWebClient().getIdToUpdate(this@RecyclerActivity, this@RecyclerActivity, list, recyclerViewPessoas,username)
                    }
                })
                .show()
        }
    }

}
