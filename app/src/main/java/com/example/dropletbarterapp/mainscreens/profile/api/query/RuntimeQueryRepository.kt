package com.example.dropletbarterapp.mainscreens.profile.api.query

import com.example.dropletbarterapp.mainscreens.profile.dto.QueryDto
import com.example.dropletbarterapp.models.Query
import com.example.dropletbarterapp.ui.models.UICategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class RuntimeQueryRepository(retrofit: Retrofit) : QueryRepository {

    private val queryApi = retrofit.create(QueryApi::class.java)

    override suspend fun addQuery(
        accessToken: String,
        adsId: Long,
        userId: Long
    ): Query {
        return withContext(Dispatchers.IO) {

            val queryDto = QueryDto(
                adsId,
                userId,
                0
            )

            return@withContext queryApi.addQuery(
                "Bearer $accessToken",
                queryDto
            )
        }
    }

    override suspend fun findQuery(accessToken: String, id: Long): Query {
        return withContext(Dispatchers.IO) {
            return@withContext queryApi.findQuery(
                "Bearer $accessToken",
                id
            )
        }
    }

    override suspend fun editQuery(
        accessToken: String,
        id: Long,
        adsId: Long,
        userId: Long,
        status: Int
    ): Query {
        return withContext(Dispatchers.IO) {
            val queryDto = QueryDto(
                userId,
                adsId,
                status
            )
            return@withContext queryApi.editQuery(
                "Bearer $accessToken", id, queryDto
            )
        }
    }

    override suspend fun findQueryForUser(
        accessToken: String,
        userId: Long
    ): MutableList<Query> {
        return withContext(Dispatchers.IO) {
            return@withContext queryApi.findQueriesForUser(
                "Bearer $accessToken",
                userId
            )
        }
    }
}