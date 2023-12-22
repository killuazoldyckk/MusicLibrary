package com.example.spotify

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Popular(val penyanyi: String, val photo: Int) : Parcelable
