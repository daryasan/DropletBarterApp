package com.example.dropletbarterapp.mainscreens.advertisements.dto

import android.os.Parcelable
import com.example.backend.models.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Parcelize
class AdvertisementDataDto(
    @SerializedName("photo") val photo: String,
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("statusActive") val statusActive: Boolean? = null,
    @SerializedName("category") val category: Int,
    @SerializedName("ownerId") val ownerId: Long? = null,
) : Parcelable {}