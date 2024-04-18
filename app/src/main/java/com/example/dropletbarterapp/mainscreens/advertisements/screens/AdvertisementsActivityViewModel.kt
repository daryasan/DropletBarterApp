package com.example.dropletbarterapp.mainscreens.advertisements.screens

import com.auth0.android.jwt.JWT
import com.example.dropletbarterapp.mainscreens.advertisements.dto.AdvertisementDataDto
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.utils.Dependencies

class AdvertisementsActivityViewModel {

    suspend fun findMyAdvertisements(): List<Advertisement> {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        val ads = Dependencies.advertisementRepository.findAdvertisementsOwnedByUser(
            Dependencies.tokenService.getAccessToken().toString(),
            jwt.getClaim("id").asString()!!.toLong()
        )
        return ads.sortedBy { !it.statusActive }
    }

    suspend fun getFavourites(): List<Advertisement> {
        return Dependencies.favouritesRepository.findForUser(
            Dependencies.tokenService.getAccessToken().toString(),
            getUserId()
        ).toSet().toList()
    }

    suspend fun getSharedUsage(): List<Advertisement> {
        return Dependencies.sharedUsageRepository.findForUser(
            Dependencies.tokenService.getAccessToken().toString(),
            getUserId()
        ).toSet().toList()
    }

    suspend fun getPurchases(): List<Advertisement> {
        return Dependencies.purchasesRepository.findForUser(
            Dependencies.tokenService.getAccessToken().toString(),
            getUserId()
        ).toSet().toList()
    }

    private fun getUserId(): Long {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        return jwt.getClaim("id").asString()!!.toLong()
    }

}