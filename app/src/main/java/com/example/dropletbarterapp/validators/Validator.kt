package com.example.dropletbarterapp.validators

class Validator {

    fun validateFieldIsNull(field: String?): Boolean {
        return field == null || field == ""
    }

    fun validatePassword(password: String): Boolean {
        if (password.length < 6) return false
        if (password.firstOrNull { it.isDigit() } == null) return false
        if (password.filter { it.isLetter() }
                .firstOrNull { it.isUpperCase() } == null) return false
        if (password.filter { it.isLetter() }
                .firstOrNull { it.isLowerCase() } == null) return false
        return true
    }

    fun validateAndRepeatPassword(password: String, repeatPassword: String): Boolean {
        return (validatePassword(password) && repeatPassword == password)
    }

    fun validatePhone(phone: Long): Boolean {
        var newPhone = phone
        if (phone.toString()[0] == '+') {
            newPhone = phone.toString().substring(1, phone.toString().length - 1).toLong()
        }
        return android.util.Patterns.PHONE.matcher(newPhone.toString()).matches() &&
                (newPhone.toString().length == 11 || newPhone.toString().length == 12)
    }

    fun validateLogin(login: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(login).matches()
    }

}