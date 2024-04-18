package com.example.dropletbarterapp.auth

import com.example.dropletbarterapp.auth.dto.LoginByEmailDTO
import com.example.dropletbarterapp.auth.dto.RegisterDTO
import com.example.dropletbarterapp.auth.dto.TokenEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class RuntimeAuthRepository(retrofit: Retrofit) : AuthRepository {

    private val authApi = retrofit.create(AuthApi::class.java)

    override suspend fun signInByEmail(email: String, password: String): TokenEntity {
        return withContext(Dispatchers.IO) {
            val dto = LoginByEmailDTO(email, password)

            return@withContext authApi.signInByEmail(dto);
        }
    }

    override suspend fun signUpByEmail(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: Long
    ): TokenEntity {
        return withContext(Dispatchers.IO) {
            val dto = RegisterDTO(
                email, password,
                firstName = firstName,
                lastName = lastName,
                phone = phone
            )

            return@withContext authApi.signUpByEmail(dto)
        }
    }

    override suspend fun logOut(tokenEntity: TokenEntity) {
        withContext(Dispatchers.IO) {
            authApi.logOut(tokenEntity)
        }
    }

    override suspend fun refreshTokensById(id: Long): TokenEntity {
        return withContext(Dispatchers.IO) {
            return@withContext authApi.refreshTokens(id)
        }
    }

}