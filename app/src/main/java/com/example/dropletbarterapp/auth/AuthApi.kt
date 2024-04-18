package com.example.dropletbarterapp.auth

import com.example.dropletbarterapp.auth.dto.LoginByEmailDTO
import com.example.dropletbarterapp.auth.dto.RegisterDTO
import com.example.dropletbarterapp.auth.dto.TokenEntity
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {
    @POST("auth/loginEmail")
    suspend fun signInByEmail(@Body loginByEmailDTO: LoginByEmailDTO): TokenEntity

    @POST("auth/registerEmail")
    suspend fun signUpByEmail(@Body registerDTO: RegisterDTO): TokenEntity

    @POST("api/logout/")
    suspend fun logOut(@Body tokenEntity: TokenEntity)

    @POST("auth/refresh/{user_id}")
    suspend fun refreshTokens(@Path("user_id") userId: Long): TokenEntity

}