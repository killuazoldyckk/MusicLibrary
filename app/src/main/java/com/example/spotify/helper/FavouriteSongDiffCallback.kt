package com.example.spotify.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.spotify.database.FavouriteSong

class FavouriteSongDiffCallback(private val oldNoteList: List<FavouriteSong>, private val newFavouriteSongList: List<FavouriteSong>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldNoteList.size
    }

    override fun getNewListSize(): Int {
        return newFavouriteSongList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].id == newFavouriteSongList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNode = oldNoteList[oldItemPosition]
        val newNote = newFavouriteSongList[newItemPosition]
        return oldNode.songName == newNote.songName && oldNode.contributors == newNote.contributors && oldNode.favourite == newNote.favourite && oldNode.albumCover == newNote.albumCover
    }


}