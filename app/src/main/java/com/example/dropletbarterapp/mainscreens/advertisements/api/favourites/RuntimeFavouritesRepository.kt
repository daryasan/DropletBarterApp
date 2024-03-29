package com.example.dropletbarterapp.mainscreens.advertisements.api.favourites


import com.example.dropletbarterapp.mainscreens.advertisements.dto.AddAdvertisementRequest
import com.example.dropletbarterapp.models.Advertisement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class RuntimeFavouritesRepository(retrofit: Retrofit) : FavouritesRepository {

    private val favouritesApi = retrofit.create(FavouritesApi::class.java)

    override suspend fun addToFavourites(accessToken: String, userId: Long, adsId: Long) {
        withContext(Dispatchers.IO) {
            favouritesApi.addToFavourites(
                accessToken = "Bearer $accessToken",
                request = AddAdvertisementRequest(
                    userId = userId,
                    adsId = adsId
                )
            )
        }
    }

    override suspend fun deleteFromFavourites(accessToken: String, userId: Long, adsId: Long) {
        withContext(Dispatchers.IO) {
            favouritesApi.deleteFromFavourites(
                accessToken = "Bearer $accessToken",
                request = AddAdvertisementRequest(
                    userId = userId,
                    adsId = adsId
                )
            )
        }
    }

    override suspend fun findForUser(accessToken: String, userId: Long): List<Advertisement> {
        return withContext(Dispatchers.IO) {
            return@withContext favouritesApi.findForUser(
                accessToken = "Bearer $accessToken",
                userId = userId
            )
        }
    }
}