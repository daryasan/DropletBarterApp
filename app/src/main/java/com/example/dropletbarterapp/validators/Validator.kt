package com.example.dropletbarterapp.validators

import android.content.Context

class Validator(val context: Context) {

    private val toaster: Toaster = Toaster()

    // TODO decent validators i beg

    fun validatePassword(password: String): Boolean {
        if (toaster.checkNullsAndGetToast(
                "Пароль не может быть пустым!",
                context,
                password
            )
        ) {
            if (password.length < 6) return false
            if (password.filter { it.isDigit() }.firstOrNull() == null) return false
            if (password.filter { it.isLetter() }
                    .firstOrNull { it.isUpperCase() } == null) return false
            if (password.filter { it.isLetter() }
                    .firstOrNull { it.isLowerCase() } == null) return false
            return true
        }
        return false
    }

    fun validateAndRepeatPassword(password: String, repeatPassword: String): Boolean {
        return (validatePassword(password) && repeatPassword == password)
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