package com.example.dropletbarterapp.validators

import android.content.Context
import android.view.View

interface ErrorSender {

    val validator: Validator

    fun checkNullsAndGetMessage(context: Context, message: String, field: String?) : Boolean

    fun checkNullsAndGetMessage(context: Context, message: String, vararg field: String?) : Boolean

    fun showMessage(context: Context, message: String)

}