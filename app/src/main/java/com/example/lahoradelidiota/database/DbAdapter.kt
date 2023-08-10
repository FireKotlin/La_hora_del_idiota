package com.example.lahoradelidiota.database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lahoradelidiota.databinding.DbItemBinding
import com.example.lahoradelidiota.others.Idiota

class  DbAdapter : ListAdapter<Idiota, DbAdapter.ViewHolder>(
    DiffCallback
) {
    // ... (resto del código del adaptador)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val idiota = getItem(position)
        holder.bind(idiota)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Idiota>() {
        override fun areItemsTheSame(oldItem: Idiota, newItem: Idiota): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Idiota, newItem: Idiota): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }
    }

    private lateinit var onItemClickListener: ((idiota: Idiota) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DbItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)

    }

    inner class ViewHolder(private val binding: DbItemBinding):
        RecyclerView.ViewHolder(binding.root) {



        fun bind(idiota: Idiota) {
            binding.idiotNumber.text = idiota.numeroDeIdiota
            binding.nameTextview.text = idiota.nombre

            binding.root.setOnClickListener {
                if (::onItemClickListener.isInitialized) {
                    onItemClickListener(idiota)

                }
            }
        }
    }
}
