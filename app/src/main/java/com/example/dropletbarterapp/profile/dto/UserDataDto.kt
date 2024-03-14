package com.example.dropletbarterapp.profile.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class UserDataDto(
    @SerializedName("id") var id: Long,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val phone: Long? = 0,
    @SerializedName("firstName") var firstName: String? = null,
    @SerializedName("lastName") var lastName: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("photo") var photo: String? = null,
    @SerializedName("items") var items: Long = 0
) : Parcelable {
}