package com.example.dropletbarterapp.mainscreens.profile

import com.example.dropletbarterapp.mainscreens.profile.dto.*
import retrofit2.http.*

interface UserApi {

    @GET("/users/{user_id}")
    suspend fun findUserById(
        @Header("Authorization") accessToken: String,
        @Path("user_id") userId: Long
    ): UserDataDto

    @PATCH("users/edit/{user_id}")
    suspend fun editUser(
        @Header("Authorization") accessToken: String,
        @Path("user_id") userId: Long,
        @Body userEditDto: UserEditDto
    ): UserDataDto

    @PATCH("users/changeEmail/{user_id}")
    suspend fun editEmail(
        @Header("Authorization") accessToken: String,
        @Path("user_id") userId: Long,
        @Body email : UserEditLoginsEmailDto
    )

    @PATCH("users/changePhone/{user_id}")
    suspend fun editPhone(
        @Header("Authorization") accessToken: String,
        @Path("user_id") userId: Long,
        @Body phone : UserEditLoginsPhoneDto
    )

    @PATCH("users/changePassword/{user_id}")
    suspend fun changePassword(
        @Header("Authorization") accessToken: String,
        @Path("user_id") userId: Long,
        @Body password : UserEditLoginsPasswordDto
    )

}