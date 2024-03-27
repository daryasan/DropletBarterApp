package com.example.dropletbarterapp.mainscreens.profile.screens.fragments

import androidx.lifecycle.ViewModel
import com.auth0.android.jwt.JWT
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.mainscreens.profile.screens.fragments.maps.YandexApi
import com.example.dropletbarterapp.validators.Toaster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import kotlin.coroutines.CoroutineContext

class EditDataViewModel : ViewModel(), CoroutineScope {

    companion object {
        private val retrofit = Retrofit.Builder().baseUrl("https://geocode-maps.yandex.ru/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val api: YandexApi = retrofit.create(YandexApi::class.java)

    }


    suspend fun saveEditedData(firstName: String, lastName: String, photo : ByteArray?, address: String?) {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        Dependencies.userRepository.editPersonalData(
            Dependencies.tokenService.getAccessToken().toString(),
            jwt.getClaim("id").asString()!!.toLong(),
            firstName, lastName, address = address, photo = photo
        )
    }

    suspend fun getUserData(): UserDataDto {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        return Dependencies.userRepository.findUserById(
            Dependencies.tokenService.getAccessToken().toString(),
            jwt.getClaim("id").asString()!!.toLong()
        )
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


}