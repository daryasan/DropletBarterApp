package com.example.dropletbarterapp

import android.content.Context
import com.example.dropletbarterapp.auth.AuthRepository
import com.example.dropletbarterapp.auth.UserAuthRepository
import com.example.dropletbarterapp.utils.TokenService
import com.example.dropletbarterapp.utils.TokenSharedPreferencesService
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

    val authRepository: AuthRepository by lazy { UserAuthRepository(retrofit) }
    val tokenService: TokenService by lazy { TokenSharedPreferencesService(applicationContext) }
}