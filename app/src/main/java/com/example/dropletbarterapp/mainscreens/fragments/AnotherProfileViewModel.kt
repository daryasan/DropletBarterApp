package com.example.dropletbarterapp.mainscreens.fragments

import androidx.lifecycle.ViewModel
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.utils.Dependencies

class AnotherProfileViewModel : ViewModel() {

    suspend fun findUser(id: Long): UserDataDto {
        return Dependencies.userRepository.findUserById(
            Dependencies.tokenService.getAccessToken().toString(),
            id
        )
    }

    suspend fun findAdvertisementsForUser(id: Long): List<Advertisement> {
        var ads = Dependencies.advertisementRepository.findAdvertisementsOwnedByUser(
            Dependencies.tokenService.getAccessToken().toString(),
            id
        )
        removeArchived(ads)
        return ads
    }

    fun removeArchived(ads: List<Advertisement>) {
        ads.toMutableSet().removeIf { !it.statusActive }
        ads.toList()
    }

}