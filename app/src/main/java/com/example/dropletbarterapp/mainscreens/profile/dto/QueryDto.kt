package com.example.dropletbarterapp.mainscreens.profile.dto

import com.google.gson.annotations.SerializedName

class QueryDto(
    @SerializedName("userId") val userId: Long,
    @SerializedName("adsId") val adsId: Long,
    @SerializedName("status") val status: Int,
)