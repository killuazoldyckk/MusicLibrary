package com.example.spotify.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.spotify.database.FavouriteSong
import com.example.spotify.database.FavouriteSongDao
import com.example.spotify.database.FavouriteSongRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavouriteSongRepository(application: Application) {

    private var favouriteSongDao: FavouriteSongDao

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavouriteSongRoomDatabase.getDatabase(application)
        favouriteSongDao = db.favouriteSongDao()
    }

    fun getAllFavouriteSongs(): LiveData<List<FavouriteSong>> {
        return favouriteSongDao.getAllFavouriteSongs()
    }

    fun insert(favouriteSong: FavouriteSong) {
        executorService.execute {
            favouriteSongDao.insert(favouriteSong)
        }
    }

    fun delete(favouriteSong: FavouriteSong) {
        executorService.execute {
            favouriteSongDao.delete(favouriteSong)
        }
    }
}
