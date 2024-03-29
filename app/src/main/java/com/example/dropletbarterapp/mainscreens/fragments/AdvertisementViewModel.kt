package com.example.dropletbarterapp.mainscreens.fragments

import androidx.lifecycle.ViewModel
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.utils.Dependencies

class AdvertisementViewModel : ViewModel() {

    suspend fun findAdvertisement(id: Long): Advertisement {
        return Dependencies.advertisementRepository.findAdvertisement(
            Dependencies.tokenService.getAccessToken().toString(),
            id
        )
    }

    suspend fun findOwner(advertisement: Advertisement): UserDataDto {
        return Dependencies.userRepository.findUserById(
            Dependencies.tokenService.getAccessToken().toString(),
            advertisement.ownerId
        )
    }

}