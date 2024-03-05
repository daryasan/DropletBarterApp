package com.example.dropletbarterapp.screens

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.sql.Connection

class ProfileActivity : AppCompatActivity() {
    private val connection: Connection? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

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
        val bt = findViewById<Button>(R.id.button_connect)
        bt.setText(MainActivity.Companion.currentUser!!.password)
    }

    companion object {
        private const val ip =
            "localhost" // this is the host ip that your data base exists on you can use 10.0.2.2 for local host                                                    found on your pc. use if config for windows to find the ip if the database exists on                                                    your pc
        private const val port = "5432" // the port sql server runs on
        private const val Classes =
            "net.sourceforge.jtds.jdbc.Driver" // the driver that is required for this connection use                                                                           "org.postgresql.Driver" for connecting to postgresql
        private const val database = "droplet" // the data base name
        private const val username = "postgres" // the user name
        private const val password = "1234" // the password
        private const val url =
            "jdbc:jtds:sqlserver://" + ip + ":" + port + "/" + database // the connection url string
    }
}