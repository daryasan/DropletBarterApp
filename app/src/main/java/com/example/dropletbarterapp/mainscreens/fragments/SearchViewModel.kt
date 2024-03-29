package com.example.dropletbarterapp.mainscreens.fragments

import androidx.lifecycle.ViewModel
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.models.UICategory

class SearchViewModel : ViewModel() {

    fun findCloserAdvertisements(): MutableList<Advertisement> {
        return mutableListOf()
    }

    fun findAdvertisements(): MutableList<Advertisement> {
        return mutableListOf()
    }

    fun findCategoryAdvertisement(
        currentAdvertisement: List<Advertisement>,
        category: Category
    ): MutableList<Advertisement> {

        return mutableListOf()
    }

}