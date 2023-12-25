package com.example.spotify.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface FavouriteSongDao {

    @Insert
    fun insert(favouriteSong: FavouriteSong)

    @Update
    fun update(favouriteSong: FavouriteSong)

    @Delete
    fun delete(favouriteSong: FavouriteSong)

    @Query("SELECT * FROM favourite_songs")
    fun getAllFavouriteSongs(): LiveData<List<FavouriteSong>>

    @Query("SELECT * FROM favourite_songs WHERE song_name = :songName LIMIT 1")
    fun isSongFavourite(songName: String): FavouriteSong?


}
