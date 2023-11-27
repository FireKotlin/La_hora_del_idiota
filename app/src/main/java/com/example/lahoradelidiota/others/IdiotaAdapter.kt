package com.example.lahoradelidiota.others
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lahoradelidiota.databinding.ListItemBinding

class IdiotaAdapter: ListAdapter<Idiota, IdiotaAdapter.ViewHolder>(
    DiffCallback
) {
    companion object DiffCallback : DiffUtil.ItemCallback<Idiota>() {
        override fun areItemsTheSame(oldItem: Idiota, newItem: Idiota): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Idiota, newItem: Idiota): Boolean {
            return oldItem.imagenUrl == newItem.imagenUrl
        }
    }

    private lateinit var onItemClickListener: ((idiota: Idiota) -> Unit)

    fun setOnItemClickListener(onItemClickListener: (idiota: Idiota) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val idiota = getItem(position)
        holder.bind(idiota)

    }
    inner class ViewHolder(private val binding: ListItemBinding):
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