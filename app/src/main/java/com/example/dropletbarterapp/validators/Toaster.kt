package com.example.dropletbarterapp.validators

import android.content.Context
import android.widget.Toast

class Toaster {
    fun checkNullsAndGetToast(
        message: String,
        context: Context,
        vararg checked: String
    ): Boolean {
        for (s in checked) {
            if (s.isEmpty()) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    fun getToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getToastWrongLogin(context: Context){
        Toast.makeText(context, "Неверный адрес почты!", Toast.LENGTH_SHORT).show()
        Toast.makeText(context, "Номер телефона не может быть пустым!", Toast.LENGTH_SHORT).show()
    }

    fun checkNullsAndGetToast(message: String, context: Context, checked: String): Boolean {
        if (checked.isEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}