package com.example.dropletbarterapp.validators

import android.widget.EditText
import android.widget.TextView
import com.example.dropletbarterapp.R

class UIMessageMan {
    private val validator: Validator
        get() = Validator()

    fun checkIfNullsAndGetMessage(
        message: String,
        field: EditText,
        messageHolder: TextView
    ): Boolean {
        if (validator.validateFieldIsNull(field.text.toString())) {
            field.setBackgroundResource(R.drawable.edit_text_error)
            messageHolder.text = message
            return false
        }
        deleteMessage(field, messageHolder)
        return true
    }

    fun getMessage(
        field: EditText,
        messageHolder: TextView, message: String
    ) {
        field.setBackgroundResource(R.drawable.edit_text_error)
        messageHolder.text = message
    }

    fun deleteMessage(
        field: EditText,
        messageHolder: TextView,
    ) {
        field.setBackgroundResource(R.drawable.edit_text)
        messageHolder.text = ""
    }

    fun checkLoginAndGetMessage(
        field: EditText,
        messageHolder: TextView
    ): Boolean {
        if (checkIfNullsAndGetMessage(
                "Заполните поле!",
                field,
                messageHolder
            ) && validator.validateLogin(field.text.toString())
        ) {
            deleteMessage(field, messageHolder)
            return true
        }
        field.setBackgroundResource(R.drawable.edit_text_error)
        messageHolder.text = "Неверный формат почты"
        return false
    }

    fun checkPasswordAndGetMessage(
        field: EditText,
        messageHolder: TextView
    ): Boolean {
        if (checkIfNullsAndGetMessage(
                "Заполните поле!",
                field,
                messageHolder
            ) && validator.validatePassword(field.text.toString())
        ) {
            deleteMessage(field, messageHolder)
            return true
        }
        field.setBackgroundResource(R.drawable.edit_text_error)
        messageHolder.text = "Неверный формат пароля"
        return false
    }

    fun checkPhoneAndGetMessage(
        field: EditText,
        messageHolder: TextView
    ): Boolean {
        if (checkIfNullsAndGetMessage(
                "Заполните поле",
                field,
                messageHolder
            ) && validator.validatePhone(field.text.toString().toLong())
        ) {
            deleteMessage(field, messageHolder)
            return true
        }
        field.setBackgroundResource(R.drawable.edit_text_error)
        messageHolder.text = "Неверный формат телефона"
        return false
    }

    fun checkRepeatPasswordAndGetMessage(
        field: EditText,
        repeatPassword: EditText,
        messageHolder: TextView,
        messageHolderRepeat: TextView,
    ): Boolean {
        if (!checkIfNullsAndGetMessage(
                "Заполните поле",
                field,
                messageHolder
            )
        ) {
            return false
        }
        if (!checkPasswordAndGetMessage(field, messageHolder)) {
            return false
        }
        if (repeatPassword.text.toString() != field.text.toString()) {
            repeatPassword.setBackgroundResource(R.drawable.edit_text_error)
            messageHolderRepeat.text = "Пароли не совпадают"
            return false
        }
        deleteMessage(field, messageHolder)
        return true
    }


}