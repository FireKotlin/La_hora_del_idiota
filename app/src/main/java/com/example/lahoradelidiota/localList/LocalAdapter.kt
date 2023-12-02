package com.example.lahoradelidiota.localList
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lahoradelidiota.databinding.ListItemBinding

class LocalAdapter : ListAdapter<IdiotaLocal, LocalAdapter.ViewHolder>(DiffCallback()) {

    private var onItemClickListener: ((idiota: IdiotaLocal) -> Unit) = {}

    fun setOnItemClickListener(onItemClickListener: (idiota: IdiotaLocal) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val idiotaLocal = getItem(position)
        holder.bind(idiotaLocal)
    }

    fun getItemAtPosition(position: Int): IdiotaLocal {
        return getItem(position)
    }

    inner class ViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(idiotaLocal: IdiotaLocal) {
            binding.idiotNumber.text = idiotaLocal.numeroDeIdiota
            binding.nameTextview.text = idiotaLocal.nombre

            binding.root.setOnClickListener {
                onItemClickListener(idiotaLocal)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<IdiotaLocal>() {
        override fun areItemsTheSame(oldItem: IdiotaLocal, newItem: IdiotaLocal): Boolean {
            return oldItem.numeroDeIdiota == newItem.numeroDeIdiota
        }

        override fun areContentsTheSame(oldItem: IdiotaLocal, newItem: IdiotaLocal): Boolean {
            return oldItem == newItem && oldItem.imagenUriString == newItem.imagenUriString
        }
    }
}
