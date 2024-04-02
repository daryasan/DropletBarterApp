package com.example.dropletbarterapp.mainscreens.advertisements.api.purchases

import com.example.dropletbarterapp.mainscreens.advertisements.dto.AddAdvertisementByQueryRequest
import com.example.dropletbarterapp.mainscreens.advertisements.dto.AddAdvertisementRequest
import com.example.dropletbarterapp.models.Advertisement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class RuntimePurchasesRepository(retrofit: Retrofit) : PurchasesRepository {

    private val purchasesApi = retrofit.create(PurchasesApi::class.java)

    override suspend fun addToPurchases(
        accessToken: String,
        userId: Long,
        adsId: Long,
        queryId: Long
    ) {
        withContext(Dispatchers.IO) {
            purchasesApi.addToPurchases(
                accessToken = "Bearer $accessToken",
                request = AddAdvertisementByQueryRequest(
                    userId = userId,
                    adsId = adsId,
                    queryId = queryId
                )
            )
        }
    }

    override suspend fun findForUser(accessToken: String, userId: Long): List<Advertisement> {
        return withContext(Dispatchers.IO) {
            return@withContext purchasesApi.findForUser(
                accessToken = "Bearer $accessToken",
                userId = userId
            )
        }
    }
}