package com.example.dropletbarterapp.mainscreens.foryou.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.auth.screens.LoginActivity
import com.example.dropletbarterapp.databinding.ActivityMainBinding
import com.example.dropletbarterapp.mainscreens.fragments.AdvertisementFragment
import com.example.dropletbarterapp.mainscreens.profile.screens.fragments.EditDataFragment
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.adapters.AdvertisementsAdapter
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.utils.Navigation


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AdvertisementsAdapter
    //private val toaster = Toaster()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //enableAndShowElements()
        Dependencies.initDependencies(this)

        // FOR TESTING DB
        //Dependencies.tokenService.killTokens()

        // if not authorized -> authorize
        if (Dependencies.tokenService.getAccessToken() == null) {
            authorize()
        }

        Navigation.setNavigation(this, R.id.main)

        adapter = AdvertisementsAdapter()
        adapter.advertisements = List(7) {
            Advertisement(
                null, "Рекомендуемая книга", "Новая книжка", true, Category.OTHER, null

            )
        }
        // ?
        binding.recyclerView.adapter = adapter
        adapter.setOnAdvertisementClickListener(object :
            AdvertisementsAdapter.OnAdvertisementClickListener {
            override fun onAdvertisementClick(advertisement: Advertisement) {
                disableAndHideElements()
                val fragment = AdvertisementFragment.newInstance()
                //fragment.arguments = bundle
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.forYouLayout, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })

        setButtonsChange()

    }

    override fun onBackPressed() {
        enableAndShowElements()
    }

    private fun authorize() {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        overridePendingTransition(0, 0)
    }

    private fun disableAndHideElements() {
        binding.layoutForYouRoot.alpha = 0f
        binding.layoutForYouRoot.isEnabled = false
    }

    private fun enableAndShowElements() {
        binding.layoutForYouRoot.alpha = 1f
        binding.layoutForYouRoot.isEnabled = true
    }

    private fun setButtonsChange() {
        binding.buttonMyAdvertisements.setOnClickListener {
            adapter.advertisements = List(7) {
                Advertisement(
                    null, "Рекомендуемая книга", "Новая книжка", true, Category.OTHER, null

                )
            }

        }

        binding.buttonFavourites.setOnClickListener {
            adapter.advertisements = List(10) {
                Advertisement(
                    null, "Книга недавняя", "Новая книжка", true, Category.OTHER, null

                )
            }
        }

        binding.buttonSharedUsage.setOnClickListener {
            adapter.advertisements = List(15) {
                Advertisement(
                    null, "Книга рядом", "Новая книжка", true, Category.OTHER, null

                )
            }
        }
    }

}