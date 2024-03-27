package com.example.dropletbarterapp.mainscreens.profile.screens.fragments

import androidx.lifecycle.ViewModel
import com.auth0.android.jwt.JWT
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.mainscreens.profile.dto.UserEditLoginsEmailDto
import com.example.dropletbarterapp.mainscreens.profile.dto.UserEditLoginsPasswordDto
import com.example.dropletbarterapp.mainscreens.profile.dto.UserEditLoginsPhoneDto
import com.example.dropletbarterapp.utils.Dependencies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class ChangeLoginsViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun getUserData(): UserDataDto {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        return Dependencies.userRepository.findUserById(
            Dependencies.tokenService.getAccessToken().toString(),
            jwt.getClaim("id").asString()!!.toLong()
        )
    }

    suspend fun editEmail(email: String) {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        Dependencies.userRepository.changeEmail(
            Dependencies.tokenService.getAccessToken().toString(),
            jwt.getClaim("id").asString()!!.toLong(),
            UserEditLoginsEmailDto(email)
        )
        val newToken =
            Dependencies.authRepository.refreshTokensById(jwt.getClaim("id").asString()!!.toLong())
        Dependencies.tokenService.setTokens(newToken)
    }

    suspend fun editPhone(phone: Long) {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        Dependencies.userRepository.changePhone(
            Dependencies.tokenService.getAccessToken().toString(),
            jwt.getClaim("id").asString()!!.toLong(),
            UserEditLoginsPhoneDto(phone)
        )
//        val newToken =
//            Dependencies.authRepository.refreshTokensById(jwt.getClaim("id").asString()!!.toLong())
//        Dependencies.tokenService.setTokens(newToken)
    }

    suspend fun editPassword(password: String) {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        Dependencies.userRepository.changePassword(
            Dependencies.tokenService.getAccessToken().toString(),
            jwt.getClaim("id").asString()!!.toLong(),
            UserEditLoginsPasswordDto(password)
        )

    }

}