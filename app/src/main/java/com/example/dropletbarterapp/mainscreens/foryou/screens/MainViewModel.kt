package com.example.dropletbarterapp.mainscreens.foryou.screens

import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.utils.Dependencies
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class MainViewModel {

    fun findAllAdvertisements(): List<Advertisement> {
        var advertisements: List<Advertisement>
        try {
            runBlocking {
                advertisements =
                    Dependencies.advertisementRepository.findAllAdvertisements(
                        Dependencies.tokenService.getAccessToken().toString()
                    )
            }
        } catch (e: HttpException) {
            runBlocking {
                Dependencies.tokenService.refreshTokens()
                advertisements =
                    Dependencies.advertisementRepository.findAllAdvertisements(
                        Dependencies.tokenService.getAccessToken().toString()
                    )
            }
        }

        return removeArchived(advertisements).toSet().shuffled()
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

    fun findSharedUsage(): List<Advertisement> {
        var advertisements: List<Advertisement>
        try {
            runBlocking {
                advertisements =
                    Dependencies.advertisementRepository.findAdvertisementsByCategory(
                        Dependencies.tokenService.getAccessToken().toString(),
                        Category.SHARED_USAGE
                    )
            }
        } catch (e: HttpException) {
            runBlocking {
                Dependencies.tokenService.refreshTokens()
                advertisements =
                    Dependencies.advertisementRepository.findAdvertisementsByCategory(
                        Dependencies.tokenService.getAccessToken().toString(),
                        Category.SHARED_USAGE
                    )
            }
        }
        return removeArchived(advertisements)
    }

    fun findRecent(): List<Advertisement> {
        var advertisements: List<Advertisement>
        try {
            runBlocking {
                advertisements =
                    Dependencies.advertisementRepository.findAllAdvertisements(
                        Dependencies.tokenService.getAccessToken().toString()
                    )
            }
        } catch (e: HttpException) {
            runBlocking {
                Dependencies.tokenService.refreshTokens()
                advertisements =
                    Dependencies.advertisementRepository.findAllAdvertisements(
                        Dependencies.tokenService.getAccessToken().toString()
                    )
            }
        }

        return removeArchived(advertisements.reversed())
    }

}