package com.example.dropletbarterapp.models

import com.google.gson.annotations.SerializedName
import lombok.AllArgsConstructor
import lombok.Data
import lombok.RequiredArgsConstructor
import org.springframework.data.annotation.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Table

class User(
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val phone: Long? = 0,
    @SerializedName("password") val password: String? = null,
    @SerializedName("firstName") val firstName: String? = null,
    @SerializedName("lastName") val lastName: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("photo") val photo: String? = null,
    @SerializedName("rating") val rating: Double = 0.0,
    @SerializedName("items") val items: Long = 0,
    // @SerializedName("favourites") val favourites: MutableList<Advertisement>,
    //@SerializedName("purchases") val purchases: MutableList<Advertisement>,
    //@SerializedName("reviews") val reviews: MutableList<Review>,
    //@SerializedName("chats") val chats: MutableList<Chat>
)