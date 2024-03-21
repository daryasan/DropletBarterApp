package com.example.dropletbarterapp.mainscreens.profile

import com.example.dropletbarterapp.models.User
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.mainscreens.profile.dto.UserEditDto
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
        @Body userEditDto : UserEditDto
    ) : UserDataDto

}