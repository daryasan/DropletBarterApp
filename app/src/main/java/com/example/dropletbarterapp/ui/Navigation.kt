package com.example.dropletbarterapp.ui

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.mainscreens.advertisements.screens.AdvertisementsActivity
import com.example.dropletbarterapp.mainscreens.profile.screens.ProfileActivity
import com.example.dropletbarterapp.mainscreens.chats.screens.ChatsActivity
import com.example.dropletbarterapp.mainscreens.foryou.screens.MainActivity
import com.example.dropletbarterapp.mainscreens.search.screens.SearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

object Navigation {

    fun setNavigation(activity: Activity, selectedItem: Int) {
        val bottomNavigationView =
            activity.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = selectedItem
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            if (item.itemId == R.id.main) {
                activity.startActivity(
                    Intent(
                        activity.applicationContext,
                        MainActivity::class.java
                    )
                )
                activity.overridePendingTransition(0, 0)
                return@setOnItemSelectedListener true
            }
            if (item.itemId == R.id.search) {
                activity.startActivity(
                    Intent(
                        activity.applicationContext,
                        SearchActivity::class.java
                    )
                )
                activity.overridePendingTransition(0, 0)
                return@setOnItemSelectedListener true
            }
            if (item.itemId == R.id.advertisements) {
                activity.startActivity(
                    Intent(
                        activity.applicationContext,
                        AdvertisementsActivity::class.java
                    )
                )
                activity.overridePendingTransition(0, 0)
                return@setOnItemSelectedListener true
            }
            if (item.itemId == R.id.chats) {
                activity.startActivity(
                    Intent(
                        activity.applicationContext,
                        ChatsActivity::class.java
                    )
                )
                activity.overridePendingTransition(0, 0)
                return@setOnItemSelectedListener true
            }
            if (item.itemId == R.id.profile) {
                activity.startActivity(
                    Intent(
                        activity.applicationContext,
                        ProfileActivity::class.java
                    )
                )
                activity.overridePendingTransition(0, 0)
                return@setOnItemSelectedListener true
            }
            false
        }
    }
}