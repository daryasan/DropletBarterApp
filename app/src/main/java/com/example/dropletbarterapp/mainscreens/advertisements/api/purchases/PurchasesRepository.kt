package com.example.dropletbarterapp.mainscreens.advertisements.api.purchases

import com.example.dropletbarterapp.models.Advertisement

interface PurchasesRepository {

    suspend fun addToPurchases(
        accessToken: String,
        userId: Long,
        adsId: Long
    )

//    suspend fun deleteFromPurchases(
//        accessToken: String,
//        userId: Long,
//        adsId: Long
//    )

    suspend fun findForUser(
        accessToken: String,
        userId: Long
    ): List<Advertisement>

}