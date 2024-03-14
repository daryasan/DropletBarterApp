package com.example.dropletbarterapp.profile.screens.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.auth0.android.jwt.JWT
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.databinding.FragmentEditBinding
import com.example.dropletbarterapp.profile.dto.UserDataDto
import com.example.dropletbarterapp.validators.Toaster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EditViewModel(
    application: Application,
    private val binding: FragmentEditBinding
) :
    AndroidViewModel(application), CoroutineScope {

    private val toaster: Toaster = Toaster()
    private val context = application.applicationContext

    fun setData(data: UserDataDto) {
        if (data.firstName != null) {
            binding.editTextFirstName.setText(data.firstName)
        }
        if (data.lastName != null) {
            binding.editTextLastName.setText(data.lastName)
        }
        if (data.address != null) {
            binding.editTextAddress.setText(data.address)
        }

    }

    fun saveEditedData() {
        if (toaster.checkNullsAndGetToast(
                "Введите имя!",
                context,
                binding.editTextLastName.text.toString()
            )
        ) {
            val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
            launch {
                Dependencies.userRepository.editPersonalData(
                    Dependencies.tokenService.getAccessToken().toString(),
                    jwt.getClaim("id").asString()!!.toLong(),
                    binding.editTextFirstName.text.toString(),
                    binding.editTextLastName.text.toString(),
                    binding.editTextAddress.text.toString()
                )
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


}