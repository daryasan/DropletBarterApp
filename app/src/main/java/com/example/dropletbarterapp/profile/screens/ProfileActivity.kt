package com.example.dropletbarterapp.profile.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.jwt.JWT
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.auth.screens.LoginActivity
import com.example.dropletbarterapp.databinding.ActivityProfileBinding
import com.example.dropletbarterapp.profile.dto.UserDataDto
import com.example.dropletbarterapp.profile.screens.fragments.EditFragment
import com.example.dropletbarterapp.utils.Navigation
import kotlinx.coroutines.*

class ProfileActivity : AppCompatActivity(), CoroutineScope {

    private var job: Job = Job()
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userDataDto: UserDataDto

    override val coroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Dependencies.initDependencies(this)

        try {
            runBlocking {
                launch {
                    userDataDto = getUserData()
                    setUserDataToScreen(userDataDto)
                }
            }
        } catch (e: Exception) {
            Dependencies.tokenService.killTokens()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            overridePendingTransition(0, 0)
        }

        Navigation.setNavigation(this, R.id.profile)

        binding.buttonSettings.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("userData", userDataDto)
            val fragment = EditFragment.newInstance()
            fragment.arguments = bundle
        }

        binding.buttonLogOut.setOnClickListener {
            launch {
                logOut()
                onResult()
            }
        }
    }

    private suspend fun getUserData(): UserDataDto {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        return Dependencies.userRepository.findUserById(
            Dependencies.tokenService.getAccessToken().toString(),
            jwt.getClaim("id").asString()!!.toLong()
        )
    }

    private fun setUserDataToScreen(userDataDto: UserDataDto) {
        if (userDataDto.photo == null) {
            binding.imageViewAvatar.setImageResource(R.drawable.empty_profile_image)
        }
//        } else {
//            binding.imageViewAvatar.setImageURI(userDataDto.photo)
//        }
        binding.textViewUserFirstLastName.text =
            if (userDataDto.firstName == null && userDataDto.lastName == null) {
                getString(R.string.alertMesFirstLastName)
            } else if (userDataDto.firstName == null) {
                userDataDto.lastName
            } else if (userDataDto.lastName == null) {
                userDataDto.firstName
            } else {
                getString(R.string.firstLastName, userDataDto.firstName, userDataDto.lastName)
            }

        binding.textViewEmail.text = userDataDto.email ?: getString(R.string.alertMesEmail)
        binding.textViewAddress.text = if (userDataDto.address == null) {
            getString(R.string.alertMesAddress)
        } else {
            userDataDto.address
        }
        binding.textViewItems.text = getString(R.string.itemsNumber, userDataDto.items)
        binding.textViewPhone.text = if (userDataDto.phone == null) {
            getString(R.string.alertMesPhone)
        } else {
            userDataDto.phone.toString()
        }
    }

    private fun onResult() {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        overridePendingTransition(0, 0)
    }

    private fun logOut() {
        Dependencies.tokenService.killTokens()
    }

}