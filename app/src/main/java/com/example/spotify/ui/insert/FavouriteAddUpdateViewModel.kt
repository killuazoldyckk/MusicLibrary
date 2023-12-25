package com.example.spotify.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.spotify.database.FavouriteSong
import com.example.spotify.repository.FavouriteSongRepository

class FavouriteAddUpdateViewModel (application: Application): ViewModel() {
    private val mFavouriteSongRepository: FavouriteSongRepository = FavouriteSongRepository(application)

    fun insert(favouriteSong: FavouriteSong){
        mFavouriteSongRepository.insert(favouriteSong)
    }

    fun update(favouriteSong: FavouriteSong){
        mFavouriteSongRepository.update(favouriteSong)
    }

    fun delete(favouriteSong: FavouriteSong){
        mFavouriteSongRepository.delete(favouriteSong)
    }
}