package com.example.dropletbarterapp.mainscreens.profile

import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.mainscreens.profile.dto.UserEditLoginsEmailDto
import com.example.dropletbarterapp.mainscreens.profile.dto.UserEditLoginsPasswordDto
import com.example.dropletbarterapp.mainscreens.profile.dto.UserEditLoginsPhoneDto

interface UserRepository {

    suspend fun findUserById(token: String, id: Long): UserDataDto

    suspend fun editPersonalData(
        accessToken: String,
        userId: Long,
        firstName: String,
        lastName: String,
        photo: ByteArray? = null,
        address: String? = null
    ): UserDataDto

    suspend fun changeEmail(
        accessToken: String,
        userId: Long,
        email: UserEditLoginsEmailDto
    )

    suspend fun changePhone(
        accessToken: String,
        userId: Long,
        phone: UserEditLoginsPhoneDto
    )

    suspend fun changePassword(
        accessToken: String,
        userId: Long,
        password: UserEditLoginsPasswordDto
    )

}