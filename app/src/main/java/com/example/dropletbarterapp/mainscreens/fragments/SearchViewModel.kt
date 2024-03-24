package com.example.dropletbarterapp.mainscreens.fragments

import androidx.lifecycle.ViewModel
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.models.uimodels.UICategory

class SearchViewModel : ViewModel() {

    fun findCloserAdvertisements(): List<Advertisement> {
        return List(10) {
            Advertisement(
                null, "Книга поближе", "Новая книжка", true, Category.OTHER, null
            )
        }
    }

    fun findAdvertisements(): List<Advertisement> {
        return List(10) {
            Advertisement(
                null, "Поисковая книга", "Новая книжка", true, Category.OTHER, null
            )
        }
    }

    fun findCategoryAdvertisement(
        currentAdvertisement: List<Advertisement>,
        category: Category
    ): List<Advertisement> {

        return List(10) {
            Advertisement(
                null,
                "${UICategory.findUICategoryForCategoryName(category).name} книга",
                "Новая книжка",
                true,
                Category.OTHER,
                null
            )
        }
    }

}