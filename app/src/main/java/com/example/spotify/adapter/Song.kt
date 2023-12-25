package com.example.spotify.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val name: String, //Artist name
    val albumCover: String, // URL for the album cover image
    val songName: String, // Title of the song
    val contributors: List<Contributor>, // List of contributors
    var isFavorite: Boolean = false
) : Parcelable

@Parcelize
data class Contributor(
    val name: String, // Contributor name
) : Parcelable

