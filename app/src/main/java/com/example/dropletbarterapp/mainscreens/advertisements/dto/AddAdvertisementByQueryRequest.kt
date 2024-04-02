package com.example.dropletbarterapp.mainscreens.advertisements.dto

import com.google.gson.annotations.SerializedName

class AddAdvertisementByQueryRequest(
    @SerializedName("userId") val userId: Long,
    @SerializedName("adsId") val adsId: Long,
    @SerializedName("queryId") val queryId: Long,
)