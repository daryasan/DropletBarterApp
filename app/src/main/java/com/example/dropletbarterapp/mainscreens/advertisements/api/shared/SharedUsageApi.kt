package com.example.dropletbarterapp.mainscreens.advertisements.api.shared

import com.example.dropletbarterapp.mainscreens.advertisements.dto.AddAdvertisementRequest
import com.example.dropletbarterapp.models.Advertisement
import retrofit2.http.*

interface SharedUsageApi {

    @POST("/shared/add")
    suspend fun addToSharedUsage(
        @Header("Authorization") accessToken: String,
        @Body request: AddAdvertisementRequest
    )

    @PATCH("/shared/delete")
    suspend fun deleteFromSharedUsage(
        @Header("Authorization") accessToken: String,
        @Body request: AddAdvertisementRequest
    )

    @GET("/shared/{id}")
    suspend fun findForUser(
        @Header("Authorization") accessToken: String,
        @Path("id") userId: Long
    ): List<Advertisement>

}