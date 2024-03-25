package com.example.dropletbarterapp.mainscreens.advertisements.screens

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.ActivityAdvertisementsBinding
import com.example.dropletbarterapp.mainscreens.advertisements.screens.fragments.AddAdvertisementFragment
import com.example.dropletbarterapp.mainscreens.fragments.AdvertisementFragment
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.adapters.AdvertisementsAdapter
import com.example.dropletbarterapp.utils.Navigation

class AdvertisementsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdvertisementsBinding
    private lateinit var adapter: AdvertisementsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdvertisementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set navigation
        Navigation.setNavigation(this, R.id.advertisements)

        adapter = AdvertisementsAdapter()
        adapter.advertisements = List(10) {
            Advertisement(
                null, "Моя книга", "Новая книжка", true, Category.OTHER, null

            )
        }

        checkVisibility()
        binding.recyclerView.adapter = adapter

        adapter.setOnAdvertisementClickListener(object :
            AdvertisementsAdapter.OnAdvertisementClickListener {
            override fun onAdvertisementClick(advertisement: Advertisement) {
                disableAndHideElements()
                val fragment = AdvertisementFragment.newInstance()
                //fragment.arguments = bundle
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayoutAds, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })

        binding.buttonAddAds.setOnClickListener {
            disableAndHideElements()
            val fragment = AddAdvertisementFragment.newInstance()
            //fragment.arguments = bundle
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayoutAds, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        // change lists
        setButtonsChange()

    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            if (fragmentManager.backStackEntryCount == 1) {
                enableAndShowElements()
            }
        }
    }

    private fun disableAndHideElements() {
        binding.layoutAdsRoot.alpha = 0f
        binding.layoutAdsRoot.isEnabled = false
    }

    private fun enableAndShowElements() {
        binding.layoutAdsRoot.alpha = 1f
        binding.layoutAdsRoot.isEnabled = true
    }

    private fun setButtonsChange() {
        binding.buttonMyAdvertisements.setOnClickListener {
            adapter.advertisements = List(10) {
                Advertisement(
                    null, "Моя книга", "Новая книжка", true, Category.OTHER, null

                )
            }
            checkVisibility()
            binding.buttonEmptyAction.text = "Добавьте объявление"
        }

        binding.buttonFavourites.setOnClickListener {
            adapter.advertisements = List(10) {
                Advertisement(
                    null, "Любимая Книга", "Новая книжка", true, Category.OTHER, null

                )
            }
            checkVisibility()
            binding.buttonEmptyAction.text = "Поиск"
        }

        binding.buttonSharedUsage.setOnClickListener {
            adapter.advertisements = List(10) {
                Advertisement(
                    null, "Общая книга", "Новая книжка", true, Category.OTHER, null

                )
            }
            checkVisibility()
            binding.buttonEmptyAction.text = "Поиск"
        }
    }

    private fun checkVisibility() {
        if (adapter.advertisements.isEmpty()) {
            binding.buttonEmptyAction.visibility = View.VISIBLE
        } else {
            binding.buttonEmptyAction.visibility = View.INVISIBLE
        }
    }

}