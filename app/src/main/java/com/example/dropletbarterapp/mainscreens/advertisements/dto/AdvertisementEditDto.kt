package com.example.dropletbarterapp.mainscreens.advertisements.dto

import com.google.gson.annotations.SerializedName

class AdvertisementEditDto(
    @SerializedName("photo") val photo: ByteArray,
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("statusActive") val statusActive: Boolean? = null,
    @SerializedName("category") val category: Int,
    @SerializedName("ownerId") val ownerId: Long? = null,
)