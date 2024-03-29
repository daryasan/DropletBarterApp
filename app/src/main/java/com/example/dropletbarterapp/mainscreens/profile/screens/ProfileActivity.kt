package com.example.dropletbarterapp.mainscreens.profile.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.jwt.JWT
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.auth.screens.LoginActivity
import com.example.dropletbarterapp.databinding.ActivityProfileBinding
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.mainscreens.profile.screens.fragments.ChangeLoginsFragment
import com.example.dropletbarterapp.mainscreens.profile.screens.fragments.EditDataFragment
import com.example.dropletbarterapp.ui.images.CircleCrop
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.ui.Navigation
import com.example.dropletbarterapp.ui.images.ImageUtils
import kotlinx.coroutines.*
import retrofit2.HttpException


class ProfileActivity : AppCompatActivity(), CoroutineScope {

    private var job: Job = Job()
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userDataDto: UserDataDto
    private val fragmentManager = supportFragmentManager

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
        enableAndShowElements()
        // TODO key??
        // MapKitFactory.setApiKey("b97b0bac-f872-49a3-a911-2699275df4db")

        Navigation.setNavigation(this, R.id.profile)

        binding.buttonSettings.setOnClickListener {
            startEditDataFragment()
        }

        binding.buttonSettingsEmail.setOnClickListener {
            startChangeLoginsFragment()
        }

        binding.buttonLogOut.setOnClickListener {
            launch {
                logOut()
                onResult()
            }
        }

        binding.imageViewAvatar.setOnClickListener {
            startEditDataFragment()
        }
    }


    override fun onResume() {
        fetchUser()
        super.onResume()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            if (fragmentManager.backStackEntryCount == 1) {
                enableAndShowElements()
                fetchUser()
            }
        }
    }

    private fun disableAndHideElements() {
        binding.layoutProfileRoot.alpha = 0f
        binding.layoutProfileRoot.isEnabled = false
    }

    private fun enableAndShowElements() {
        binding.layoutProfileRoot.alpha = 1f
        binding.layoutProfileRoot.isEnabled = true
    }

    private suspend fun getUserData(): UserDataDto {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        return Dependencies.userRepository.findUserById(
            Dependencies.tokenService.getAccessToken().toString(),
            jwt.getClaim("id").asString()!!.toLong()
        )
    }

    private fun setUserDataToScreen(userDataDto: UserDataDto) {

        ImageUtils.loadImageBitmap(userDataDto.photo, this, binding.imageViewAvatar, CircleCrop())

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

    private fun fetchUser() {
        try {
            runBlocking {
                launch {
                    userDataDto = getUserData()
                    setUserDataToScreen(userDataDto)
                }
            }
        } catch (e: HttpException) {
            runBlocking {
                Dependencies.tokenService.refreshTokens()
                userDataDto = getUserData()
                setUserDataToScreen(userDataDto)
            }
        }
    }

    private fun startEditDataFragment() {
        disableAndHideElements()
        val fragment = EditDataFragment.newInstance()
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.profileLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun startChangeLoginsFragment() {
        disableAndHideElements()
        val fragment = ChangeLoginsFragment.newInstance()
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.profileLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}