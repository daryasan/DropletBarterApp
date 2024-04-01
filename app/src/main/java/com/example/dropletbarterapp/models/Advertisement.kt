package com.example.dropletbarterapp.models

import com.google.gson.annotations.SerializedName

class Advertisement(
    @SerializedName("id") val id: Long,
    @SerializedName("photo") val photo: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String? = null,
    @SerializedName("statusActive") val statusActive: Boolean,
    @SerializedName("category") val category: Int,
    @SerializedName("ownerId") val ownerId: Long
) {
    //val tags = TagGenerator.generateTags(this)
}