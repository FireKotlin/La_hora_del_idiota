package com.example.lahoradelidiota

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lahoradelidiota.photoactivity.image
class photoAdapter : RecyclerView.Adapter<photoAdapter.ViewHolder>() {

    var dataList = emptyList<image>()

    internal fun setDataList(dataList: MutableList<image>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var title: TextView

        init {
            image = itemView.findViewById(R.id.image_item)
            title = itemView.findViewById(R.id.title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = dataList[position]
        holder.title.text = image.description

        Glide.with(holder.itemView.context)
            .load(image.url)
            .into(holder.image)
    }
    override fun getItemCount() = dataList.size
}