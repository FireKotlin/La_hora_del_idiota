package com.example.lahoradelidiota.database

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lahoradelidiota.databinding.DbItemBinding
import com.example.lahoradelidiota.others.Idiota

class DbAdapter(private val context: Context) : ListAdapter<Idiota, DbAdapter.ViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DbItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val idiota = getItem(position)
        holder.bind(idiota)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Idiota>() {
        override fun areItemsTheSame(oldItem: Idiota, newItem: Idiota): Boolean {
            return oldItem.numeroDeIdiota == newItem.numeroDeIdiota
        }

        override fun areContentsTheSame(oldItem: Idiota, newItem: Idiota): Boolean {
            return oldItem == newItem
        }
    }

    private lateinit var onItemClickListener: ((idiota: Idiota) -> Unit)

    fun setOnItemClickListener(listener: (Idiota) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(private val binding: DbItemBinding) : RecyclerView.ViewHolder(binding.root) {



        fun bind(idiota: Idiota) {
            binding.idiotNumber.text = idiota.numeroDeIdiota
            binding.nameTextview.text = idiota.nombre

            binding.deleteBttn.setOnClickListener {
                showDeleteConfirmationDialog(idiota)
            }
            binding.editBttn.setOnClickListener {
                val intent = Intent(context, AddIdiot::class.java)
                intent.putExtra("idiota", idiota) // Pasa los datos del idiota a la actividad de edición
                context.startActivity(intent)
            }
        }
    }

    private fun showDeleteConfirmationDialog(idiota: Idiota) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Confirmación")
        alertDialogBuilder.setMessage("¿Estás seguro de que deseas borrar este elemento?")
        alertDialogBuilder.setPositiveButton("Borrar") { _, _ ->
            // Invoca el método para borrar el elemento en la actividad
            onItemClickListener.invoke(idiota)
        }
        alertDialogBuilder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}
