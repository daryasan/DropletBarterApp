package com.example.dropletbarterapp.auth.dto

import com.google.gson.annotations.SerializedName

class RegisterDTO(
    @SerializedName("email")
    private val email: String,
    @SerializedName("password")
    private val password: String,
    @SerializedName("firstName")
    private val firstName: String,
    @SerializedName("lastName")
    private val lastName: String,
    @SerializedName("phone")
    private val phone: Long
)