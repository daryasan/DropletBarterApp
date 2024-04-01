package com.example.dropletbarterapp.mainscreens.fragments

import androidx.lifecycle.ViewModel
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.models.UICategory
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.utils.Similarity
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.util.*

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

    fun hideAdvertisement(
        advertisement: Advertisement
    ) {
        try {
            runBlocking {
                Dependencies.advertisementRepository.editAdvertisement(
                    Dependencies.tokenService.getAccessToken().toString(),
                    advertisement.id,
                    Base64.getDecoder().decode(advertisement.photo),
                    advertisement.name,
                    advertisement.description,
                    UICategory.getCategoryByPos(advertisement.category),
                    false
                )
            }
        } catch (e: HttpException) {
            runBlocking {
                Dependencies.tokenService.refreshTokens()
                Dependencies.advertisementRepository.editAdvertisement(
                    Dependencies.tokenService.getAccessToken().toString(),
                    advertisement.id,
                    Base64.getDecoder().decode(advertisement.photo),
                    advertisement.name,
                    advertisement.description,
                    UICategory.getCategoryByPos(advertisement.category),
                    false
                )
            }
        }
    }

    fun findSuggestions(size: Int): List<Advertisement> {
        var ads: List<Advertisement>
        try {
            runBlocking {
                ads = Similarity.findOtherAdvertisements(findAllAdvertisements(), size)
            }
        } catch (e: HttpException) {
            runBlocking {
                Dependencies.tokenService.refreshTokens()
                ads = Similarity.findOtherAdvertisements(findAllAdvertisements(), size)
            }
        }
        return ads
    }

    private suspend fun findAllAdvertisements(): List<Advertisement> {
        val ads = Dependencies.advertisementRepository.findAllAdvertisements(
            Dependencies.tokenService.getAccessToken().toString()
        )
        removeArchived(ads)
        return ads
    }

    fun removeArchived(ads: List<Advertisement>) {
        ads.toMutableSet().removeIf { !it.statusActive }
        ads.toList()
    }

}