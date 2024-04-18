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
        return removeArchived(ads)
    }

    private fun removeArchived(ads: List<Advertisement>): List<Advertisement> {
        val withoutArchived = mutableListOf<Advertisement>()
        for (a in ads) {
            if (a.statusActive) {
                withoutArchived.add(a)
            }
        }
        return withoutArchived.toList()

    }
}