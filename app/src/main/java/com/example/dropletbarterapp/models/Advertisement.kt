package com.example.dropletbarterapp.models

class Advertisement(
    val photo: String?,
    val name: String? = null,
    val description: String? = null,
    val status_active: Boolean? = null,
    val category: Category? = null,
    val owner: User? = null
) {}