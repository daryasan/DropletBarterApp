package com.example.dropletbarterapp.mainscreens.advertisements.api.ads

import com.example.dropletbarterapp.mainscreens.advertisements.dto.AdvertisementDataDto
import com.example.dropletbarterapp.mainscreens.advertisements.dto.AdvertisementEditDto
import com.example.dropletbarterapp.mainscreens.profile.dto.UserEditDto
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.models.UICategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class RuntimeAdvertisementRepository(retrofit: Retrofit) : AdvertisementRepository {

    private val advertisementApi = retrofit.create(AdvertisementApi::class.java)

    override suspend fun addAdvertisement(
        accessToken: String,
        photo: ByteArray,
        name: String,
        description: String?,
        category: Category,
        ownerId: Long
    ): AdvertisementDataDto {
        return withContext(Dispatchers.IO) {

            val advertisementEditDto = AdvertisementEditDto(
                photo,
                name,
                description,
                true,
                UICategory.getPosByCategory(category),
                ownerId
            )

            return@withContext advertisementApi.addAdvertisement(
                "Bearer $accessToken",
                advertisementEditDto
            )
        }
    }

    override suspend fun findAdvertisement(accessToken: String, id: Long): Advertisement {
        return withContext(Dispatchers.IO) {
            return@withContext advertisementApi.findAdvertisement(
                "Bearer $accessToken",
                id
            )
        }

    }

    override suspend fun editAdvertisement(
        accessToken: String,
        id: Long,
        photo: ByteArray,
        name: String,
        description: String?,
        category: Category,
        statusActive: Boolean
    ): Advertisement {
        return withContext(Dispatchers.IO) {
            val advertisementEditDto = AdvertisementEditDto(
                photo,
                name,
                description,
                statusActive,
                UICategory.getPosByCategory(category)
            )
            return@withContext advertisementApi.editAdvertisement(
                "Bearer $accessToken", id, advertisementEditDto
            )
        }
    }

    override suspend fun findAllAdvertisements(accessToken: String): MutableList<Advertisement> {
        return withContext(Dispatchers.IO) {
            return@withContext advertisementApi.findAllAdvertisements(
                "Bearer $accessToken",
            )
        }
    }

    override suspend fun findAdvertisementsByCategory(
        accessToken: String,
        category: Category
    ): MutableList<Advertisement> {
        return withContext(Dispatchers.IO) {
            return@withContext advertisementApi.findAdvertisementsByCategory(
                "Bearer $accessToken",
                UICategory.getPosByCategory(category)
            )
        }
    }

    override suspend fun findAdvertisementsOwnedByUser(
        accessToken: String,
        ownerId: Long
    ): MutableList<Advertisement> {
        return withContext(Dispatchers.IO) {
            return@withContext advertisementApi.findAdvertisementsOwnedByUser(
                "Bearer $accessToken",
                ownerId
            )
        }
    }
}