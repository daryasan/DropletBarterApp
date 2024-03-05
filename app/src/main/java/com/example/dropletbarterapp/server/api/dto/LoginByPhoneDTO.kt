package com.example.dropletbarterapp.server.api.dto

import com.google.gson.annotations.SerializedName
import lombok.AllArgsConstructor
import lombok.Data

class LoginByPhoneDTO(
    @SerializedName("phone")
    private val phone: Long,
    @SerializedName("password")
    private val password: String
) {}