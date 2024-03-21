package com.example.dropletbarterapp.mainscreens.profile.screens.fragments

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class ChangeLoginsViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    fun editEmail(email: String) {

    }

    fun editPhone(phone: Long) {

    }

    fun editPassword(password: String) {

    }

}