package com.example.dropletbarterapp.server.api

import com.example.dropletbarterapp.models.User
import com.example.dropletbarterapp.server.api.dto.LoginByEmailDTO
import com.example.dropletbarterapp.server.api.dto.LoginByPhoneDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun signInByEmail(@Body loginByEmailDTO: LoginByEmailDTO): User

    @POST("auth/login")
    suspend fun signInByPhone(@Body loginByPhoneDTO: LoginByPhoneDTO): User

    @POST("auth/register")
    suspend fun signUpByEmail(@Body loginByEmailDTO: LoginByEmailDTO):  User

    @POST("auth/register")
    suspend fun signUpByPhone(@Body loginByPhoneDTO: LoginByPhoneDTO): User
}