package com.example.dropletbarterapp.mainscreens.fragments

import android.location.Location
import androidx.lifecycle.ViewModel
import com.auth0.android.jwt.JWT
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.models.UICategory
import com.example.dropletbarterapp.utils.Dependencies
import info.debatty.java.stringsimilarity.JaroWinkler
import info.debatty.java.stringsimilarity.NormalizedLevenshtein
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class SearchViewModel : ViewModel() {

    private var allAdvertisement = listOf<Advertisement>()
    private var allAdsWithAddresses = mapOf<Long, String?>()

    fun findAdvertisements(
        query: String?,
        categoryPos: Int?,
        closer: Boolean
    ): List<Advertisement> {

        try {
            val category0 = if (categoryPos == 0) null else categoryPos
            var ads: List<Advertisement>

            // 0 parameters
            if (query == null && category0 == null && !closer) return findAdvertisements()

            // 1 parameter
            if (query != null && category0 == null && !closer) return findAdvertisements(query)
            if (query == null && category0 != null && !closer) return findAdvertisements(category0)
            if (query == null && category0 == null && closer) return findAdvertisements(true)

            // 2 parameters
            if (query != null && category0 != null && !closer) return findAdvertisements(
                query,
                category0
            )
            if (query != null && category0 == null && closer) return findAdvertisements(
                query,
                true
            )
            if (query == null && category0 != null && closer) return findAdvertisements(
                category0,
                true
            )


            // all parameters
            if (query != null && category0 != null && closer) return findAdvertisements(
                query,
                category0,
                true
            )

            return listOf()
        } catch (e: HttpException) {
            runBlocking {
                Dependencies.tokenService.refreshTokens()
            }
            return findAdvertisements(query, categoryPos, closer)
        } catch (e: Exception) {
            return listOf()
        }


    }

    private fun findAdvertisements(): List<Advertisement> {
        var ads: List<Advertisement>
        runBlocking {
            ads = findAllAdvertisements()
        }
        return ads
    }

    private fun findAdvertisements(query: String): List<Advertisement> {
        var ads: List<Advertisement>
        runBlocking {
            ads = findAdvertisementsByQuery(query)
        }
        return ads
    }

    private fun findAdvertisements(categoryPos: Int): List<Advertisement> {
        var ads: List<Advertisement>
        runBlocking {
            ads = findCategoryAdvertisement(UICategory.getCategoryByPos(categoryPos))
        }
        return removeArchived(ads)
    }

    private fun findAdvertisements(closer: Boolean): List<Advertisement> {
        var ads: List<Advertisement>

        runBlocking {
            ads = findCloserAdvertisements()
        }

        return ads
    }

    private fun findAdvertisements(query: String, categoryPos: Int): List<Advertisement> {
        var adsByQuery: List<Advertisement>
        var adsByCategory: List<Advertisement>

        runBlocking {
            adsByQuery = findAdvertisementsByQuery(query)
            adsByCategory = findCategoryAdvertisement(UICategory.getCategoryByPos(categoryPos))
        }

        return getIntersect(adsByCategory, adsByQuery).toList()
    }

    private fun findAdvertisements(query: String, closer: Boolean): List<Advertisement> {

        var adsByQuery: List<Advertisement>
        var adsByAddress: List<Advertisement>

        runBlocking {
            adsByQuery = findAdvertisementsByQuery(query)
            adsByAddress = findCloserAdvertisements()
        }

        return getIntersect(adsByAddress, adsByQuery).toList()
    }

    private fun findAdvertisements(categoryPos: Int, closer: Boolean): List<Advertisement> {

        var adsByCategory: List<Advertisement>
        var adsByAddress: MutableList<Advertisement>
        runBlocking {
            adsByAddress = findCloserAdvertisements()
            adsByCategory =
                findCategoryAdvertisement(UICategory.getCategoryByPos(categoryPos))
        }

        return getIntersect(adsByAddress, adsByCategory).toList()
    }

    private fun findAdvertisements(
        query: String,
        categoryPos: Int,
        closer: Boolean
    ): List<Advertisement> {
        var adsByQuery: List<Advertisement>
        var adsByCategory: List<Advertisement>
        var adsByAddress: List<Advertisement>

        runBlocking {
            adsByQuery = findAdvertisementsByQuery(query)
            adsByCategory = findCategoryAdvertisement(UICategory.getCategoryByPos(categoryPos))
            adsByAddress = findCloserAdvertisements()
        }

        return getIntersect(adsByAddress, getIntersect(adsByQuery, adsByCategory)).toList()

    }

    private suspend fun findAllAdvertisements(): List<Advertisement> {
        if (allAdvertisement.isEmpty()) {
            val ads = Dependencies.advertisementRepository.findAllAdvertisements(
                Dependencies.tokenService.getAccessToken().toString()
            )
            val map = mutableMapOf<Long, String?>()
            val addresses = mutableListOf<String?>()
            for (ad in ads) {
                addresses.add(
                    Dependencies.userRepository.findUserById(
                        Dependencies.tokenService.getAccessToken().toString(),
                        ad.ownerId
                    ).address
                )
            }
            for (i in 0 until ads.size) {
                map += Pair(ads[i].id, addresses[i])
            }
            allAdvertisement = removeArchived(ads)
            allAdsWithAddresses = map
        }
        return allAdvertisement
    }

    private suspend fun findCloserAdvertisements(): MutableList<Advertisement> {

        val address = getUserAddress()
        return if (address == null || address == "") {
            mutableListOf()
        } else {
            val ads = findAllAdvertisements()
            val similarAds = mutableListOf<Advertisement>()
            for (ad in ads) {
                if (getDistance(address, allAdsWithAddresses[ad.id])) {
                    similarAds.add(ad)
                }
            }
            similarAds
        }

    }

    private suspend fun findAdvertisementsByQuery(
        query: String
    ): List<Advertisement> {

        val ads = findAllAdvertisements()
        val jw = JaroWinkler()
        val nl = NormalizedLevenshtein()

        val regex = Regex(query, setOf(RegexOption.IGNORE_CASE, RegexOption.LITERAL))
        val similarAds = ads.filter { ad ->
            jw.similarity(query, ad.name) > 0.8 ||
                    nl.similarity(query, ad.name) > 0.8 ||
                    regex.find(ad.name) != null
        }
        return similarAds
    }

    private suspend fun findCategoryAdvertisement(
        category: Category
    ): List<Advertisement> {
        val ads = Dependencies.advertisementRepository.findAdvertisementsByCategory(
            Dependencies.tokenService.getAccessToken().toString(),
            category
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


    private suspend fun getUserAddress(): String? {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        return Dependencies.userRepository.findUserById(
            Dependencies.tokenService.getAccessToken().toString(),
            jwt.getClaim("id").asString()!!.toLong()
        ).address
    }


    private fun getDistance(
        userAddress: String,
        otherAddress: String?
    ): Boolean {
        if (otherAddress == null || otherAddress == "") return false
        val addressUser = Dependencies.geocoder.getFromLocationName(userAddress, 1)?.get(0)
        val addressOther = Dependencies.geocoder.getFromLocationName(otherAddress, 1)?.get(0)
        return if (addressUser != null && addressOther != null) {
            val loc1 = Location("User")
            val loc2 = Location("Other")
            loc1.latitude = addressUser.latitude
            loc1.longitude = addressUser.longitude
            loc2.latitude = addressOther.latitude
            loc2.longitude = addressOther.longitude
            loc1.distanceTo(loc2) < 6000
        } else {
            false
        }
    }

    private fun getIntersect(
        list1: List<Advertisement>,
        list2: List<Advertisement>
    ): MutableList<Advertisement> {
        val intersect = mutableListOf<Advertisement>()
        for (a1 in list1) {
            for (a2 in list2) {
                if (a1.id == a2.id) {
                    intersect.add(a1)
                    break
                }
            }
        }

        return intersect

    }

//    suspend fun findOwner(advertisement: Advertisement): UserDataDto {
//        return Dependencies.userRepository.findUserById(
//            Dependencies.tokenService.getAccessToken().toString(),
//            advertisement.ownerId
//        )
//    }
}
