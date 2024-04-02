package com.example.dropletbarterapp.mainscreens.profile.api.user

import com.example.dropletbarterapp.mainscreens.profile.dto.*
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
        firstName: String,
        lastName: String,
        photo: ByteArray?,
        address: String?
    ): UserDataDto {
        return withContext(Dispatchers.IO) {
            val newUserData = UserEditDto(
                firstName, lastName, address = address, photo = photo
            )
            return@withContext userApi.editUser(
                "Bearer $accessToken", userId, newUserData
            )
        }
    }

    override suspend fun changeEmail(
        accessToken: String,
        userId: Long,
        email: UserEditLoginsEmailDto
    ) {
        return withContext(Dispatchers.IO) {
            return@withContext userApi.editEmail(
                "Bearer $accessToken", userId, email
            )
        }
    }

    override suspend fun changePhone(
        accessToken: String,
        userId: Long,
        phone: UserEditLoginsPhoneDto
    ) {
        return withContext(Dispatchers.IO) {
            return@withContext userApi.editPhone(
                "Bearer $accessToken", userId, phone
            )
        }
    }

    override suspend fun changePassword(
        accessToken: String,
        userId: Long,
        password: UserEditLoginsPasswordDto
    ) {
        return withContext(Dispatchers.IO) {
            return@withContext userApi.changePassword(
                "Bearer $accessToken", userId, password
            )
        }
    }


}