package com.example.dropletbarterapp.screens

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    companion object {
        var currentUser: User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authorize()

        // set navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.main
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
    }

    private fun authorize() {
        startActivity(Intent(applicationContext, AuthActivity::class.java))
        overridePendingTransition(0, 0)
    }


}