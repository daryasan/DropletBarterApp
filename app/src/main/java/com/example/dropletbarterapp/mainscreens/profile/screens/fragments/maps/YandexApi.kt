package com.example.dropletbarterapp.mainscreens.profile.screens.fragments.maps

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YandexApi {
    @GET("/1.x/")
    fun suggestAddresses(
        @Query("apikey") apiKey: String,
        @Query("geocode") geocode: String
    ): Call<String>
}