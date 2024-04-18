package com.example.dropletbarterapp.utils

import com.example.dropletbarterapp.models.Advertisement
import info.debatty.java.stringsimilarity.JaroWinkler
import info.debatty.java.stringsimilarity.NormalizedLevenshtein

object Similarity {

    fun findOtherAdvertisements(
        advertisement: Advertisement,
        allAdvertisements: MutableList<Advertisement>,
        size: Int
    ): List<Advertisement> {
        allAdvertisements.removeIf { it.id == advertisement.id }
        if (allAdvertisements.size <= size) {
            return allAdvertisements
        }

        val jw = JaroWinkler()
        val nl = NormalizedLevenshtein()
        val regex = Regex(advertisement.name, setOf(RegexOption.IGNORE_CASE, RegexOption.LITERAL))
        val similarAds = allAdvertisements.filter { ad ->
            jw.similarity(advertisement.name, ad.name) > 0.8 ||
                    nl.similarity(advertisement.name, ad.name) > 0.8 ||
                    regex.find(ad.name) != null
        }.toMutableList()

        if (similarAds.size < size){
            similarAds += (allAdvertisements - similarAds.toSet()).shuffled().take(size - similarAds.size)
        }

        return similarAds.toSet().toMutableList()

    }
}


