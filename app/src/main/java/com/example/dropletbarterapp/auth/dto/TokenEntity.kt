package com.example.dropletbarterapp.auth.dto

import com.google.gson.annotations.SerializedName

data class TokenEntity(
    @SerializedName("accessToken") var accessToken: String? = null,
    @SerializedName("refreshToken") var refreshToken: String? = null
)