package com.example.dropletbarterapp.validators

import android.content.Context

class Validator(val context: Context) {

    val toaster: Toaster = Toaster()

    fun validatePassword(password: String): Boolean {
        return toaster.checkNullsAndGetToast(
            "Заполните все поля!",
            context,
            password
        )
    }

    private fun validateEmail(email: String): Boolean {
        return toaster.checkNullsAndGetToast("Заполните все поля!", context, email) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validatePhone(phone: Long): Boolean {
        return toaster.checkNullsAndGetToast("Заполните все поля!", context, phone.toString()) &&
                android.util.Patterns.PHONE.matcher(phone.toString()).matches()
    }

    fun validateLogin(login: String, isEmail: Boolean): Boolean {
        return if (isEmail) {
            validateEmail(login)
        } else {
            validatePhone(login.toLong())
        }
    }

}