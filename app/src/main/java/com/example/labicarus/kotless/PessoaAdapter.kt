package com.example.labicarus.kotless

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_recycler.*
import kotlinx.android.synthetic.main.delete_dialog.view.*
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.android.synthetic.main.pessoa_item.view.*

class PessoaAdapter(private val context: Context, private var pessoaList: MutableList<Employees>, _recycler: RecyclerView?): RecyclerView.Adapter<PessoaAdapter.PessoaViewHolder>() {

    //region //----- ADAPTER FUNCTIONS -----\\
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PessoaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pessoa_item, parent, false)
        return PessoaViewHolder(view)
    }

    override fun getItemCount() = pessoaList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PessoaViewHolder, @SuppressLint("RecyclerView") position: Int) {
        //region //----- HOLDER CONFIG -----\\
        holder.bindView(pessoaList[position])
        //endregion

        //region //----- LISTENERS FUNCTION -----\\
        holder.btnDelete.setOnClickListener{
            val createdView =LayoutInflater.from(RecyclerActivity.activity).inflate(R.layout.delete_dialog,
                RecyclerActivity.activity.window.decorView as ViewGroup,
                false)
            val name = pessoaList[position].name
            createdView.txt_dialog_delete.text = "Você realmente deseja deletar o usuario $name"
            AlertDialog.Builder(RecyclerActivity.activity)
                .setView(createdView)
                .setPositiveButton("Deletar", object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        TesteWebClient().callbackDeleteVerification(pessoaList[position].name.toString(), pessoaList, context, RecyclerActivity.activity, RecyclerActivity.recyclerView)
                    }
                })
                .show()
        }
        holder.btnUpdate.setOnClickListener{
            TesteWebClient().callbackUpdateVerification(RecyclerActivity.activity, RecyclerActivity.activity, pessoaList, RecyclerActivity.recyclerView, pessoaList[position].name.toString())
        }
        //endregion

    }
    //endregion

    //region //----- HOLDER CONFIG -----\\
    class PessoaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewNome = itemView.textViewNome
        val textViewTelefone = itemView.textViewTelefone
        val btnDelete = itemView.flt_delete
        val btnUpdate = itemView.btn_flt_update

        fun bindView(pessoa: Employees) {
            textViewNome.text = pessoa.name
            textViewTelefone.text = pessoa.email
        }
    }
    //endregion

}