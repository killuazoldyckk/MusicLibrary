package com.example.spotify.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spotify.R
import com.example.spotify.database.FavouriteSong
import com.example.spotify.databinding.ItemSongrvBinding
import com.example.spotify.helper.FavouriteSongDiffCallback

class FavouriteSongAdapter(private val onDeleteClickListener: OnDeleteClickListener) :
    RecyclerView.Adapter<FavouriteSongAdapter.FavouriteSongViewHolder>() {

    private val listFavouriteSong = ArrayList<FavouriteSong>()

    interface OnDeleteClickListener {
        fun onDeleteClick(favouriteSong: FavouriteSong)
    }

    fun setListFavouriteSong(listFavouriteSong: List<FavouriteSong>) {
        val diffCallback = FavouriteSongDiffCallback(this.listFavouriteSong, listFavouriteSong)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavouriteSong.clear()
        this.listFavouriteSong.addAll(listFavouriteSong)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class FavouriteSongViewHolder(private val binding: ItemSongrvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favouriteSong: FavouriteSong) {
            with(binding) {
                songName.text = favouriteSong.songName
                contributorsName.text = favouriteSong.contributors

                // Load album cover image using Glide
                Glide.with(itemView)
                    .load(favouriteSong.albumCover)
                    .centerCrop()
                    .into(albumcover)

                // Set the appropriate favorite icon based on the state
                val favoriteIconResId = R.drawable.love_acttive
                favouritebtn.setImageResource(favoriteIconResId)

                // Handle delete button click
                favouritebtn.setOnClickListener {
                    onDeleteClickListener.onDeleteClick(favouriteSong)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteSongViewHolder {
        val binding = ItemSongrvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteSongViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFavouriteSong.size
    }

    override fun onBindViewHolder(holder: FavouriteSongViewHolder, position: Int) {
        holder.bind(listFavouriteSong[position])
    }
}
