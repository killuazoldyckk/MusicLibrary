package com.example.spotify.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("chart/0/artists?limit=10")
    fun getTop50Artist(): Call<ApiResponse>
}