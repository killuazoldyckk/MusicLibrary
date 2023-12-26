package com.example.spotify.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val name: String, //Artist name
    val albumCover: String, // URL for the album cover image
    val songName: String, // Title of the song
    val contributors: List<Contributor>, // List of contributors
    val preview: String, // URL for the track preview
    var isFavorite: Boolean = false,
    var isPlaying: Boolean = false // New property for play state
) : Parcelable

@Parcelize
data class Contributor(
    val name: String, // Contributor name
) : Parcelable

