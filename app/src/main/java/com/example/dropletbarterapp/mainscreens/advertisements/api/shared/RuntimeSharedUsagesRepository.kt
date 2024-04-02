package com.example.dropletbarterapp.mainscreens.advertisements.api.shared

import com.example.dropletbarterapp.mainscreens.advertisements.dto.AddAdvertisementByQueryRequest
import com.example.dropletbarterapp.mainscreens.advertisements.dto.AddAdvertisementRequest
import com.example.dropletbarterapp.models.Advertisement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class RuntimeSharedUsagesRepository(retrofit: Retrofit) : SharedUsageRepository {

    private val sharedUsageApi = retrofit.create(SharedUsageApi::class.java)

    override suspend fun addToSharedUsage(
        accessToken: String,
        userId: Long,
        adsId: Long,
        queryId: Long
    ) {
        withContext(Dispatchers.IO) {
            sharedUsageApi.addToSharedUsage(
                accessToken = "Bearer $accessToken",
                request = AddAdvertisementByQueryRequest(
                    userId = userId,
                    adsId = adsId,
                    queryId = queryId
                )
            )
        }
    }

    override suspend fun deleteFromSharedUsage(accessToken: String, userId: Long, adsId: Long) {
        withContext(Dispatchers.IO) {
            sharedUsageApi.deleteFromSharedUsage(
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
            return@withContext sharedUsageApi.findForUser(
                accessToken = "Bearer $accessToken",
                userId = userId
            )
        }
    }
}