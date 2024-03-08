package com.example.dropletbarterapp.screens

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.Dependencies
import com.example.dropletbarterapp.auth.screens.LoginActivity
import com.example.dropletbarterapp.databinding.ActivityMainBinding
import com.example.dropletbarterapp.utils.TokenSharedPreferencesService


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //private lateinit var dependencies: Dependencies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Dependencies.initDependencies(this)


        // if not authorized -> authorize
        if (Dependencies.tokenService.getAccessToken() == null) {
            authorize()
        }

        // set navigation
        binding.bottomNavigation.selectedItemId = R.id.main
        binding.bottomNavigation.setOnItemSelectedListener { item: MenuItem ->
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
    }

    private fun authorize() {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        overridePendingTransition(0, 0)
    }


}