package com.example.spotify.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Popular(
    val id: Int,
    val name: String,
    val photoUrlMedium: String,
    val photoUrlBig: String
) : Parcelable
