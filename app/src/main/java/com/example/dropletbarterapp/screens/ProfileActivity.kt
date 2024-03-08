package com.example.dropletbarterapp.screens

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.Dependencies
import com.example.dropletbarterapp.auth.dto.TokenEntity
import com.example.dropletbarterapp.auth.screens.LoginActivity
import com.example.dropletbarterapp.databinding.ActivityProfileBinding
import com.example.dropletbarterapp.utils.TokenSharedPreferencesService
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivityProfileBinding

    private var job: Job = Job()

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


        // set navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.profile
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->

            // choose navigation activity
            if (item.itemId == R.id.main) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                overridePendingTransition(0, 0)
                return@setOnItemSelectedListener true
            }
            if (item.itemId == R.id.search) {
                startActivity(Intent(applicationContext, SearchActivity::class.java))
                overridePendingTransition(0, 0)
                return@setOnItemSelectedListener true
            }
            if (item.itemId == R.id.advertisements) {
                startActivity(Intent(applicationContext, AdvertisementsActivity::class.java))
                overridePendingTransition(0, 0)
                return@setOnItemSelectedListener true
            }
            if (item.itemId == R.id.chats) {
                startActivity(Intent(applicationContext, ChatsActivity::class.java))
                overridePendingTransition(0, 0)
                return@setOnItemSelectedListener true
            }
            if (item.itemId == R.id.profile) {
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
                overridePendingTransition(0, 0)
                return@setOnItemSelectedListener true
            }
            false
        }

        binding.buttonConnect.setText(
            Dependencies.tokenService.getAccessToken().toString()
        )

        binding.buttonConnect.setOnClickListener {
            launch {
                logOut()
                onResult()
            }
        }
    }

    private fun onResult() {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        overridePendingTransition(0, 0)
    }

    private suspend fun logOut() {
        Dependencies.tokenService.killTokens()
    }

}