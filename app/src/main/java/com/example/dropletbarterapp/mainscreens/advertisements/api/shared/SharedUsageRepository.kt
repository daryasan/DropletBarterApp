package com.example.dropletbarterapp.mainscreens.advertisements.api.shared

import com.example.dropletbarterapp.models.Advertisement

interface SharedUsageRepository {

    suspend fun addToSharedUsage(
        accessToken: String,
        userId: Long,
        adsId: Long,
        queryId : Long
    )

    suspend fun deleteFromSharedUsage(
        accessToken: String,
        userId: Long,
        adsId: Long
    )

    suspend fun findForUser(
        accessToken: String,
        userId: Long
    ): List<Advertisement>
}