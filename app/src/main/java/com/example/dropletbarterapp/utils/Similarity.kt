package com.example.dropletbarterapp.utils

import com.example.dropletbarterapp.models.Advertisement

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
        return allAdvertisements.shuffled().take(size)

    }
}


