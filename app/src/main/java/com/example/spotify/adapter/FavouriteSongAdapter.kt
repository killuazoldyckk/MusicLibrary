package com.example.spotify.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spotify.R
import com.example.spotify.database.FavouriteSong
import com.example.spotify.databinding.ItemSongrvBinding

class FavouriteSongAdapter(private val listFavouriteSong: List<FavouriteSong>) :
    RecyclerView.Adapter<FavouriteSongAdapter.FavouriteSongViewHolder>() {

    inner class FavouriteSongViewHolder(private val binding: ItemSongrvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favouriteSong: FavouriteSong) {
            binding.apply {
                // Set the data for each view using favouriteSong
                Glide.with(root.context)
                    .load(favouriteSong.albumCover)
                    .centerCrop()
                    .into(albumcover)

                songName.text = favouriteSong.songName
                contributorsName.text = favouriteSong.contributors

                // Set the appropriate favorite icon based on the state
                val favoriteIconResId =
                    if (favouriteSong.favourite) R.drawable.love_acttive else R.drawable.love_inactive
                favouritebtn.setImageResource(favoriteIconResId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteSongViewHolder {
        val binding = ItemSongrvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavouriteSongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteSongViewHolder, position: Int) {
        val favouriteSong = listFavouriteSong[position]
        holder.bind(favouriteSong)
    }

    override fun getItemCount(): Int {
        return listFavouriteSong.size
    }

    // Other methods...

}
