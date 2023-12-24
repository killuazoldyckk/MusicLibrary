// PopularAdapter.kt
package com.example.spotify.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spotify.R

class PopularAdapter(private val listPopular: ArrayList<Popular>) : RecyclerView.Adapter<PopularAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val popular = listPopular[position]

        Glide.with(holder.itemView)
            .load(popular.photoUrl)
            .into(holder.imageView1)



        holder.textView1.text = popular.name
    }

    override fun getItemCount(): Int = listPopular.size

    fun setData(newList: List<Popular>) {
        listPopular.clear()
        listPopular.addAll(newList)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView1: ImageView = itemView.findViewById(R.id.imageView)
        val textView1: TextView = itemView.findViewById(R.id.textView)
    }
}
