package com.example.dropletbarterapp.mainscreens.fragments

import androidx.lifecycle.ViewModel
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.utils.Dependencies
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class EditAdvertisementViewModel : ViewModel() {

    fun editAdvertisement(
        advertisement: Advertisement,
        photo: ByteArray,
        name: String,
        description: String?,
        category: Category,
        statusActive: Boolean
    ) {
        try {
            runBlocking {
                Dependencies.advertisementRepository.editAdvertisement(
                    Dependencies.tokenService.getAccessToken().toString(),
                    advertisement.id,
                    photo,
                    name,
                    description,
                    category,
                    statusActive
                )
            }
        } catch (e: HttpException) {
            runBlocking {
                Dependencies.tokenService.refreshTokens()
                Dependencies.advertisementRepository.editAdvertisement(
                    Dependencies.tokenService.getAccessToken().toString(),
                    advertisement.id,
                    photo,
                    name,
                    description,
                    category,
                    statusActive
                )
            }
        }
    }

    suspend fun findAdvertisement(id: Long): Advertisement {
        return Dependencies.advertisementRepository.findAdvertisement(
            Dependencies.tokenService.getAccessToken().toString(),
            id
        )
    }
}