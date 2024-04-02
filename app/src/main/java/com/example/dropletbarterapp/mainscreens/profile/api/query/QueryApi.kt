package com.example.dropletbarterapp.mainscreens.profile.api.query

import com.example.dropletbarterapp.mainscreens.profile.dto.QueryDto
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Query
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface QueryApi {
    @POST("/query/add")
    suspend fun addQuery(
        @Header("Authorization") accessToken: String,
        @Body queryDto: QueryDto
    ): Query

    @GET("/query/{id}")
    suspend fun findQuery(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Long
    ): Query

    @PATCH("/query/edit/{id}")
    suspend fun editQuery(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Long,
        @Body queryDto: QueryDto
    ): Query


    @GET("/query/user/{id}")
    suspend fun findQueriesForUser(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Long
    ): MutableList<Query>


}