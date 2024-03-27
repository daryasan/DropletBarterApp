package com.example.dropletbarterapp.mainscreens.profile.dto

import com.google.gson.annotations.SerializedName
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

class UserEditDto(
    @SerializedName("firstName")
    var firstName: String? = null,
    @SerializedName("lastName")
    var lastName: String? = null,
    @SerializedName("address")
    var address: String? = null,
    @SerializedName("photo")
    var photo: ByteArray? = null
)