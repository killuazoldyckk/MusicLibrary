package com.example.spotify.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spotify.repository.FavouriteSongRepository
import com.example.spotify.ui.insert.FavouriteAddUpdateViewModel
import com.example.spotify.ui.library.LibraryViewModel

class ViewModelFactory private constructor(
    private val mApplication: Application,
    private val repository: FavouriteSongRepository
) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application, repository: FavouriteSongRepository): ViewModelFactory {
            return INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(application, repository).also { INSTANCE = it }
            }
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LibraryViewModel::class.java) ->
                LibraryViewModel(mApplication, repository) as T
            modelClass.isAssignableFrom(FavouriteAddUpdateViewModel::class.java) ->
                FavouriteAddUpdateViewModel(mApplication) as T
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
