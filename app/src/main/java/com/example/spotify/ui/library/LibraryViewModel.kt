package com.example.spotify.ui.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.spotify.database.FavouriteSong
import com.example.spotify.repository.FavouriteSongRepository

class LibraryViewModel(application: Application, repository: FavouriteSongRepository) : AndroidViewModel(application) {

    private val mFavouriteSongRepository: FavouriteSongRepository = repository

    fun getAllFavouriteSongs(): LiveData<List<FavouriteSong>> {
        return mFavouriteSongRepository.getAllFavouriteSongs()
    }

    fun deleteFavouriteSong(favouriteSong: FavouriteSong) {
        mFavouriteSongRepository.delete(favouriteSong)
    }
}
