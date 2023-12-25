package com.example.spotify.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spotify.R
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class SongAdapter(private var listSong: ArrayList<Song> = ArrayList()
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private var favoriteClickListener: OnFavoriteClickListener? = null

    interface OnFavoriteClickListener {
        fun onFavoriteClick(position: Int)
    }

    fun setOnFavoriteClickListener(listener: OnFavoriteClickListener) {
        this.favoriteClickListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_songrv, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = listSong[position]

        // Load album cover image using Glide
        Glide.with(holder.itemView)
            .load(song.albumCover)
            .centerCrop()
            .into(holder.albumCover)

        // Set other song details
        holder.songName.text = song.songName

        // Set the appropriate favorite icon based on the state
        val favoriteIconResId = if (song.isFavorite) R.drawable.love_acttive else R.drawable.love_inactive
        holder.favoriteImageView.setImageResource(favoriteIconResId)

        // Handle favorite button click
        holder.favoriteImageView.setOnClickListener {
            handleFavoriteButtonClick(position)
        }

        // Handle contributors
        val contributors = song.contributors
        val contributorsText = if (contributors.size == 1) {
            // Display the single contributor's name
            contributors[0].name
        } else {
            // Join multiple contributors' names using ", "
            contributors.joinToString { it.name }
        }
        holder.contributorsName.text = contributorsText

    }

    fun setFavoriteState(position: Int, isFavorite: Boolean) {
        if (position >= 0 && position < listSong.size) {
            listSong[position].isFavorite = isFavorite
            notifyItemChanged(position)
        }
    }


    private fun handleFavoriteButtonClick(position: Int) {
        // Toggle the favorite state
        listSong[position].isFavorite = !listSong[position].isFavorite
        notifyItemChanged(position)

        // Notify the listener if set
        favoriteClickListener?.onFavoriteClick(position)
    }


    override fun getItemCount(): Int = listSong.size

    fun setData(newList: List<Song>) {
        listSong.clear()
        listSong.addAll(newList)
        notifyDataSetChanged()
    }

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumCover: ShapeableImageView = itemView.findViewById(R.id.albumcover) // Album cover image
        val songName: TextView = itemView.findViewById(R.id.songName)
        val contributorsName: TextView = itemView.findViewById(R.id.contributors_name)
        val favoriteImageView: ImageView = itemView.findViewById(R.id.favouritebtn)

    }
}