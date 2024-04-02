package com.example.dropletbarterapp.models

import com.example.dropletbarterapp.ui.models.StatusConverter
import com.google.gson.annotations.SerializedName

class Query(
    @SerializedName("id") val id: Long,
    @SerializedName("userId") val userId: Long,
    @SerializedName("adsId") val adsId: Long,
    @SerializedName("status") val status: Int,
) {
    var statusEnum = StatusConverter.getStatusByPosition(status)
}