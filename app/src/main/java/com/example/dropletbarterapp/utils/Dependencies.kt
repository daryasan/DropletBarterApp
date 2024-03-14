package com.example.dropletbarterapp.utils

import android.content.Context
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.auth.AuthRepository
import com.example.dropletbarterapp.auth.RuntimeAuthRepository
import com.example.dropletbarterapp.profile.RuntimeUserRepository
import com.example.dropletbarterapp.profile.UserRepository
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Dependencies {

    private lateinit var applicationContext: Context

    fun initDependencies(context: Context) {
        applicationContext = context
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(applicationContext.getString(R.string.api_url))
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .build()
    }

    val authRepository: AuthRepository by lazy { RuntimeAuthRepository(retrofit) }
    val userRepository: UserRepository by lazy { RuntimeUserRepository(retrofit) }
    val tokenService: TokenService by lazy { TokenSharedPreferencesService(applicationContext) }
}