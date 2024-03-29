package com.example.dropletbarterapp.mainscreens.advertisements.api.ads

import com.example.dropletbarterapp.mainscreens.advertisements.dto.AdvertisementDataDto
import com.example.dropletbarterapp.mainscreens.advertisements.dto.AdvertisementEditDto
import com.example.dropletbarterapp.models.Advertisement
import org.springframework.web.bind.annotation.GetMapping
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AdvertisementApi {
    @POST("/advertisement/add")
    suspend fun addAdvertisement(
        @Header("Authorization") accessToken: String,
        @Body advertisementEditDto: AdvertisementEditDto
    ): AdvertisementDataDto

    @GET("/advertisement/{id}")
    suspend fun findAdvertisement(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Long
    ): Advertisement

    @GET("/advertisement/all")
    suspend fun findAllAdvertisements(
        @Header("Authorization") accessToken: String,
    ): MutableList<Advertisement>


    @GET("/advertisement/category/{category_id}")
    suspend fun findAdvertisementsByCategory(
        @Header("Authorization") accessToken: String,
        @Path("category_id") id: Int
    ): MutableList<Advertisement>


    @GET("/advertisement/user/{user_id}")
    suspend fun findAdvertisementsOwnedByUser(
        @Header("Authorization") accessToken: String,
        @Path("user_id") id: Long
    ): MutableList<Advertisement>


}