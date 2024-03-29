package com.example.dropletbarterapp.mainscreens.advertisements.dto

import com.google.gson.annotations.SerializedName

class AddAdvertisementRequest(
    @SerializedName("userId") val userId: Long,
    @SerializedName("adsId") val adsId: Long
)