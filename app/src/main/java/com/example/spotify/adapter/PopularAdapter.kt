// PopularAdapter.kt
package com.example.spotify.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.spotify.api.DataItem
import com.example.spotify.databinding.ItemCardviewBinding

class PopularAdapter(private var listPopular: List<DataItem>) :
    RecyclerView.Adapter<PopularAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemCardviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val popular = listPopular[position]
        holder.bind(popular)
    }

    override fun getItemCount(): Int = listPopular.size

    fun setData(newList: List<DataItem>) {
        listPopular = newList
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemCardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(popular: DataItem) {
            with(binding) {
                // Load image using Glide
                Glide.with(itemView.context)
                    .load(popular.pictureSmall)
                    .apply(RequestOptions.centerCropTransform())
                    .into(imageView)

                // Set the name
                textView.text = popular.name
            }
        }
    }
}
