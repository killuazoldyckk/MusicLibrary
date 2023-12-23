package com.example.spotify.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ApiResponse(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem?>? = null
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("picture_xl")
	val pictureXl: String? = null,

	@field:SerializedName("tracklist")
	val tracklist: String? = null,

	@field:SerializedName("picture_big")
	val pictureBig: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("picture_small")
	val pictureSmall: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("position")
	val position: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("picture_medium")
	val pictureMedium: String? = null,

	@field:SerializedName("radio")
	val radio: Boolean? = null
) : Parcelable
