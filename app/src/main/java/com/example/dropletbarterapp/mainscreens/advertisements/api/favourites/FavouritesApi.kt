package com.example.dropletbarterapp.mainscreens.advertisements.api.favourites

import com.example.dropletbarterapp.mainscreens.advertisements.dto.AddAdvertisementRequest
import com.example.dropletbarterapp.models.Advertisement
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface FavouritesApi {

    @POST("/favourites/add")
    suspend fun addToFavourites(
        @Header("Authorization") accessToken: String,
        @Body request: AddAdvertisementRequest
    )

    @PATCH("/favourites/delete")
    suspend fun deleteFromFavourites(
        @Header("Authorization") accessToken: String,
        @Body request: AddAdvertisementRequest
    )

    @GET("/favourites/{id}")
    suspend fun findForUser(
        @Header("Authorization") accessToken: String,
        @Path("id") userId: Long
    ): List<Advertisement>

}