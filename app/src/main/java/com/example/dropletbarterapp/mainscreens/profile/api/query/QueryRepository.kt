package com.example.dropletbarterapp.mainscreens.profile.api.query

import com.example.dropletbarterapp.models.Query

interface QueryRepository {

    suspend fun addQuery(
        accessToken: String,
        adsId: Long,
        userId: Long
    ): Query


    suspend fun findQuery(
        accessToken: String,
        id: Long
    ): Query

    suspend fun editQuery(
        accessToken: String,
        id: Long,
        adsId: Long,
        userId: Long,
        status: Int
    ): Query

    suspend fun findQueryForUser(
        accessToken: String,
        userId: Long
    ): MutableList<Query>

}