package com.example.dropletbarterapp.models

import com.example.dropletbarterapp.ui.models.UICategory
import com.google.gson.annotations.SerializedName

class Advertisement(
    @SerializedName("id") val id: Long,
    @SerializedName("photo") val photo: String?,
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("statusActive") val statusActive: Boolean? = null,
    @SerializedName("category") val category: Int,
    @SerializedName("ownerId") val ownerId: Long
) {
    val categoryEnum: Category = UICategory.getCategoryByPos(category)
}