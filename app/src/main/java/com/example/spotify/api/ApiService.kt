package com.example.spotify.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("chart/0/artists?limit=10")
    fun getTop10Artist(): Call<ApiResponse>

    @GET("artist/{id}/top?limit=10")
    fun getTop10Songs(@Path("id") id: Int): Call<TopSongsResponse>
}