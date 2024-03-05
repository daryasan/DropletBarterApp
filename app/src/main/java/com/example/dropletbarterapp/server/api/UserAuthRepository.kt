package com.example.dropletbarterapp.server.api

import com.example.dropletbarterapp.models.User
import com.example.dropletbarterapp.server.api.dto.LoginByEmailDTO
import com.example.dropletbarterapp.server.api.dto.LoginByPhoneDTO
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class UserAuthRepository(retrofit: Retrofit) {

    private val authApi = retrofit.create(AuthApi::class.java)
//    private val URL = "http://10.110.184.76:8080"
//    private val retrofit = Retrofit.Builder().baseUrl(URL)
//        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//        .build()
//    private val authApi = retrofit.create(AuthApi::class.java)

//    private fun setCurrentUser(user: User) {
//        MainActivity.currentUser = user
//    }

    suspend fun signInByEmail(email: String, password: String) : User {
        return withContext(Dispatchers.IO) {
            val dto = LoginByEmailDTO(email, password)

            return@withContext authApi.signInByEmail(dto);
        }
    }

    suspend fun signInByPhone(phone: Long, password: String): User {
        return withContext(Dispatchers.IO) {
            val dto = LoginByPhoneDTO(phone, password)

            return@withContext authApi.signInByPhone(dto);
        }
    }

    suspend fun signUpByEmail(email: String, password: String) : User {
        return withContext(Dispatchers.IO) {
            val dto = LoginByEmailDTO(email, password)

            return@withContext authApi.signUpByEmail(dto)
        }
    }

    suspend fun signUpByPhone(phone: Long, password: String): User {
        return withContext(Dispatchers.IO) {
            val dto = LoginByPhoneDTO(phone, password)

            return@withContext authApi.signUpByPhone(dto)
        }
    }
}