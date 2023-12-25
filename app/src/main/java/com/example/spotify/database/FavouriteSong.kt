package com.example.spotify.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_songs")
data class FavouriteSong(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "song_name")
    val songName: String,

    @ColumnInfo(name = "album_cover")
    val albumCover: String,

    @ColumnInfo(name = "contributors")
    val contributors: String,

    @ColumnInfo(name = "favourite")
    val favourite: Boolean
)
