package com.example.spotify.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Popular(
    val name: String,
    val photoUrl: String
) : Parcelable
