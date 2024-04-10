package com.example.dropletbarterapp.validators

import android.content.Context
import android.widget.Toast

class Toaster() : ErrorSender {

    override val validator: Validator
        get() = Validator()

    fun getToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun checkNullsAndGetMessage(
        context: Context,
        message: String,
        field: String?
    ): Boolean {
        return if (validator.validateFieldIsNull(field)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }

    }

    override fun checkNullsAndGetMessage(
        context: Context,
        message: String,
        vararg field: String?
    ): Boolean {
        for (f in field) {
            if (validator.validateFieldIsNull(f)) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    override fun showMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}