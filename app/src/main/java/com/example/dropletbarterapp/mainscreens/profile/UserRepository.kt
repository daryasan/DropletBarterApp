package com.example.dropletbarterapp.mainscreens.profile

import com.example.dropletbarterapp.models.User
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto

interface UserRepository {

    suspend fun findUserById(token: String, id: Long): UserDataDto

    suspend fun editPersonalData(
        accessToken: String,
        userId: Long,
        firstName: String? = null,
        lastName: String? = null,
        photo: String? = null,
        address: String? = null
    ): UserDataDto

}