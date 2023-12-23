package com.example.spotify.adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PopularItemViewModel(
    val name: String?,
    val pictureSmall: String?
) : Parcelable
