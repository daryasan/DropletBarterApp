package com.example.dropletbarterapp.mainscreens.advertisements.api.ads

import com.example.dropletbarterapp.mainscreens.advertisements.dto.AdvertisementDataDto
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category

interface AdvertisementRepository {

    suspend fun addAdvertisement(
        accessToken: String,
        photo: ByteArray,
        name: String,
        description: String?,
        category: Category,
        ownerId: Long
    ): AdvertisementDataDto


    suspend fun findAdvertisement(
        accessToken: String,
        id: Long
    ): Advertisement

    suspend fun editAdvertisement(
        accessToken: String,
        id: Long,
        photo: ByteArray,
        name: String,
        description: String?,
        category: Category,
        statusActive: Boolean
    ): Advertisement


    suspend fun findAllAdvertisements(
        accessToken: String,
    ): MutableList<Advertisement>


    suspend fun findAdvertisementsByCategory(
        accessToken: String,
        category: Category
    ): MutableList<Advertisement>


    suspend fun findAdvertisementsOwnedByUser(
        accessToken: String,
        ownerId: Long
    ): MutableList<Advertisement>

}