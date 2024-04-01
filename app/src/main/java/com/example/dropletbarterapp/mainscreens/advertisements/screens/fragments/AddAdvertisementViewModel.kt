package com.example.dropletbarterapp.mainscreens.advertisements.screens.fragments

import androidx.lifecycle.ViewModel
import com.auth0.android.jwt.JWT
import com.example.backend.dto.AdvertisementsDataDto
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.models.UICategory
import com.example.dropletbarterapp.utils.Dependencies

class AddAdvertisementViewModel : ViewModel() {

    suspend fun addAdvertisement(
        photo: ByteArray,
        name: String,
        description: String,
        category: Category
    ) {
        Dependencies.advertisementRepository.addAdvertisement(
            Dependencies.tokenService.getAccessToken().toString(),
            photo,
            name,
            description,
            category,
            getUserId()
        )
    }

    private fun getUserId(): Long {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        return jwt.getClaim("id").asString()!!.toLong()
    }

}