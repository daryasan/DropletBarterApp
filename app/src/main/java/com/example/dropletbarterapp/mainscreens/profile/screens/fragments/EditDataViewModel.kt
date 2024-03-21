package com.example.dropletbarterapp.mainscreens.profile.screens.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.auth0.android.jwt.JWT
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.databinding.FragmentEditBinding
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.validators.Toaster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EditDataViewModel : ViewModel(), CoroutineScope {

    private val toaster: Toaster = Toaster()

//    fun saveEditedData() {
//        if (toaster.checkNullsAndGetToast(
//                "Введите имя!",
//                context,
//                binding.editTextLastName.text.toString()
//            )
//        ) {
//            val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
//            launch {
//                Dependencies.userRepository.editPersonalData(
//                    Dependencies.tokenService.getAccessToken().toString(),
//                    jwt.getClaim("id").asString()!!.toLong(),
//                    binding.editTextFirstName.text.toString(),
//                    binding.editTextLastName.text.toString(),
//                    binding.editTextAddress.text.toString()
//                )
//            }
//        }
//    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


}