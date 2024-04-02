package com.example.dropletbarterapp.auth

import com.example.dropletbarterapp.auth.dto.TokenEntity

interface AuthRepository {

    suspend fun signInByEmail(email: String, password: String): TokenEntity

    suspend fun signInByPhone(phone: Long, password: String): TokenEntity

    suspend fun signUpByEmail(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: Long
    ): TokenEntity

    suspend fun signUpByPhone(phone: Long, password: String): TokenEntity

    suspend fun logOut(tokenEntity: TokenEntity)

    suspend fun refreshTokensById(id: Long): TokenEntity
}