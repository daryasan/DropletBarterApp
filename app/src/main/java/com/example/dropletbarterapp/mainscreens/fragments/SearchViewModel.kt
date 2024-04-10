package com.example.dropletbarterapp.mainscreens.fragments

import androidx.lifecycle.ViewModel
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

    fun findAdvertisements(
        query: String?,
        categoryPos: Int?,
        closer: Boolean
    ): List<Advertisement> {

        try {
            val category0 = if (categoryPos == 0) null else categoryPos


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

        return adsByQuery.intersect(adsByCategory.toSet()).toList()
    }

    private fun findAdvertisements(query: String, closer: Boolean): List<Advertisement> {

        var adsByQuery: List<Advertisement>
        var adsByAddress: List<Advertisement>

        runBlocking {
            adsByQuery = findAdvertisementsByQuery(query)
            adsByAddress = findCloserAdvertisements()
        }

        return adsByQuery.intersect(adsByAddress.toSet()).toList()
    }

    private fun findAdvertisements(categoryPos: Int, closer: Boolean): List<Advertisement> {
        var adsByAddress: List<Advertisement>
        var adsByCategory: List<Advertisement>

        runBlocking {
            adsByAddress = findCloserAdvertisements()
            adsByCategory = findCategoryAdvertisement(UICategory.getCategoryByPos(categoryPos))
        }

        return adsByAddress.intersect(adsByCategory.toSet()).toList()
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

        return adsByQuery.intersect(adsByCategory.intersect(adsByAddress.toSet())).toList()

    }

    private suspend fun findAllAdvertisements(): List<Advertisement> {
        if (allAdvertisement.isEmpty()) {
            val ads = Dependencies.advertisementRepository.findAllAdvertisements(
                Dependencies.tokenService.getAccessToken().toString()
            )
            allAdvertisement = removeArchived(ads)
        }
        return allAdvertisement
    }

    private suspend fun findCloserAdvertisements(): MutableList<Advertisement> {
        return mutableListOf()
    }

    private suspend fun findAdvertisementsByQuery(query: String): List<Advertisement> {

        val ads = findAllAdvertisements()
        val jw = JaroWinkler()
        val nl = NormalizedLevenshtein()

        val similarAds = ads.filter { ad ->
            jw.similarity(query, ad.name) > 0.8 || nl.similarity(query, ad.name) > 0.8
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


}