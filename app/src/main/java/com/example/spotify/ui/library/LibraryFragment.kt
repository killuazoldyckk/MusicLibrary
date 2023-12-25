package com.example.spotify.ui.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spotify.database.FavouriteSong
import com.example.spotify.databinding.ItemSongrvBinding

class FavouriteSongAdapter :
    ListAdapter<FavouriteSong, FavouriteSongAdapter.FavouriteSongViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteSongViewHolder {
        val binding = ItemSongrvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteSongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteSongViewHolder, position: Int) {
        val favouriteSong = getItem(position)
        holder.bind(favouriteSong)
    }

    inner class FavouriteSongViewHolder(private val binding: ItemSongrvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favouriteSong: FavouriteSong) {
            binding.apply {
                // Bind your views here using favouriteSong data
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<FavouriteSong>() {
        override fun areItemsTheSame(oldItem: FavouriteSong, newItem: FavouriteSong): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavouriteSong, newItem: FavouriteSong): Boolean {
            return oldItem == newItem
        }
    }
}

