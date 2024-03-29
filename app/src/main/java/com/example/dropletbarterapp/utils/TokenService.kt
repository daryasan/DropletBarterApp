package com.example.dropletbarterapp.utils

import com.example.dropletbarterapp.auth.dto.TokenEntity

interface TokenService {

    fun setTokens(tokens: TokenEntity)

    fun killTokens()

    fun getAccessToken(): String?

    fun getRefreshToken(): String?

    suspend fun refreshTokens()
}