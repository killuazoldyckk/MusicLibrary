package com.example.spotify.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete

@Dao
interface FavouriteSongDao {

    @Insert
    fun insert(favouriteSong: FavouriteSong)

    @Delete
    fun delete(favouriteSong: FavouriteSong)

    @Query("SELECT * FROM favourite_songs")
    fun getAllFavouriteSongs(): LiveData<List<FavouriteSong>>
}
