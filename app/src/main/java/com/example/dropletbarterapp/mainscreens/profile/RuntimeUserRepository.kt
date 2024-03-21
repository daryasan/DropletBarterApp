package com.example.dropletbarterapp.mainscreens.profile

import com.example.dropletbarterapp.models.User
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.mainscreens.profile.dto.UserEditDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class RuntimeUserRepository(retrofit: Retrofit) : UserRepository {

    private val userApi = retrofit.create(UserApi::class.java)

    override suspend fun findUserById(token: String, id: Long): UserDataDto {
        return withContext(Dispatchers.IO) {
            return@withContext userApi.findUserById("Bearer $token", id)
        }
    }

    override suspend fun editPersonalData(
        accessToken: String,
        userId: Long,
        firstName: String?,
        lastName: String?,
        photo: String?,
        address: String?
    ): UserDataDto {
        return withContext(Dispatchers.IO) {
            val newUserData = UserEditDto(
                firstName, lastName, address
            )
            return@withContext userApi.editUser(
                "Bearer $accessToken", userId, newUserData
            )
        }
    }
}