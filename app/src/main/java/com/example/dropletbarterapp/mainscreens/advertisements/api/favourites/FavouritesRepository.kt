package com.example.dropletbarterapp.mainscreens.advertisements.api.favourites

import com.example.backend.dto.AddAdvertisementRequest
import com.example.dropletbarterapp.models.Advertisement
import retrofit2.http.*

interface FavouritesRepository {


    suspend fun addToFavourites(
        accessToken: String,
        userId: Long,
        adsId: Long
    )

    suspend fun deleteFromFavourites(
        accessToken: String,
        userId: Long,
        adsId: Long
    )

    suspend fun findForUser(
        accessToken: String,
        userId: Long
    ): List<Advertisement>

}