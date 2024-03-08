package com.example.dropletbarterapp.auth.dto

import com.google.gson.annotations.SerializedName
import lombok.AllArgsConstructor
import lombok.Data

class LoginByEmailDTO(
    @SerializedName("email")
    private val email: String,
    @SerializedName("password")
    private val password: String
) {}