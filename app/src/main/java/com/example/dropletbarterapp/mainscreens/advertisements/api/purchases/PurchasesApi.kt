package com.example.dropletbarterapp.mainscreens.advertisements.api.purchases

import com.example.dropletbarterapp.mainscreens.advertisements.dto.AddAdvertisementByQueryRequest
import com.example.dropletbarterapp.mainscreens.advertisements.dto.AddAdvertisementRequest
import com.example.dropletbarterapp.models.Advertisement
import retrofit2.http.*

interface PurchasesApi {

    @POST("/purchases/add")
    suspend fun addToPurchases(
        @Header("Authorization") accessToken: String,
        @Body request: AddAdvertisementByQueryRequest
    )

    @GET("/purchases/{id}")
    suspend fun findForUser(
        @Header("Authorization") accessToken: String,
        @Path("id") userId: Long
    ): List<Advertisement>

}