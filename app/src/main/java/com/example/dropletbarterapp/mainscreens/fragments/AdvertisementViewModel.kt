package com.example.dropletbarterapp.mainscreens.fragments

import androidx.lifecycle.ViewModel
import com.auth0.android.jwt.JWT
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

    fun sendPendingRequest(advertisement: Advertisement) {
        try {
            runBlocking {
                Dependencies.queryRepository.addQuery(
                    Dependencies.tokenService.getAccessToken().toString(),
                    advertisement.id,
                    getUserId()
                )
            }
        } catch (e: HttpException) {
            runBlocking {
                Dependencies.tokenService.refreshTokens()
                Dependencies.queryRepository.addQuery(
                    Dependencies.tokenService.getAccessToken().toString(),
                    getUserId(),
                    advertisement.id
                )
            }
        }

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

    fun findSuggestions(advertisement: Advertisement, size: Int): List<Advertisement> {
        var ads: List<Advertisement>
        try {
            runBlocking {
                ads = Similarity.findOtherAdvertisements(
                    advertisement,
                    findAllAdvertisements().toMutableList(),
                    size
                )
            }
        } catch (e: HttpException) {
            runBlocking {
                Dependencies.tokenService.refreshTokens()
                ads = Similarity.findOtherAdvertisements(
                    advertisement,
                    findAllAdvertisements().toMutableList(),
                    size
                )
            }
        }
        return removeArchived(ads)
    }

    private suspend fun findAllAdvertisements(): List<Advertisement> {
        val ads = Dependencies.advertisementRepository.findAllAdvertisements(
            Dependencies.tokenService.getAccessToken().toString()
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

    fun getUserId(): Long {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        return jwt.getClaim("id").asString()!!.toLong()

    }

}