package com.example.dropletbarterapp.mainscreens.advertisements.screens

import com.auth0.android.jwt.JWT
import com.example.dropletbarterapp.mainscreens.advertisements.dto.AdvertisementDataDto
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.utils.Dependencies

class AdvertisementsActivityViewModel {

    suspend fun findMyAdvertisements(): List<Advertisement> {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        return Dependencies.advertisementRepository.findAdvertisementsOwnedByUser(
            Dependencies.tokenService.getAccessToken().toString(),
            jwt.getClaim("id").asString()!!.toLong()
        )
    }

     suspend fun getFavourites(): List<Advertisement> {
        return Dependencies.favouritesRepository.findForUser(
            Dependencies.tokenService.getAccessToken().toString(),
            getUserId()
        )
    }

    private fun getUserId(): Long {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        return jwt.getClaim("id").asString()!!.toLong()
    }

}