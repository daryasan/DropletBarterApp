package com.example.dropletbarterapp.mainscreens.advertisements.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.ActivityAdvertisementsBinding
import com.example.dropletbarterapp.mainscreens.advertisements.screens.fragments.AddAdvertisementFragment
import com.example.dropletbarterapp.mainscreens.fragments.AdvertisementFragment
import com.example.dropletbarterapp.mainscreens.search.screens.SearchActivity
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.adapters.AdvertisementsAdapter
import com.example.dropletbarterapp.ui.Navigation
import com.example.dropletbarterapp.utils.Dependencies
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class AdvertisementsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdvertisementsBinding
    private lateinit var adapter: AdvertisementsAdapter
    private lateinit var viewModel: AdvertisementsActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdvertisementsBinding.inflate(layoutInflater)
        viewModel = AdvertisementsActivityViewModel()
        setContentView(binding.root)

        // set navigation
        Navigation.setNavigation(this, R.id.advertisements)

        adapter = AdvertisementsAdapter()
        try {
            runBlocking {
                adapter.advertisements = viewModel.findMyAdvertisements()
            }
        } catch (e: HttpException) {
            runBlocking {
                Dependencies.tokenService.refreshTokens()
                adapter.advertisements = viewModel.findMyAdvertisements()
            }
        }


        checkVisibility()
        binding.recyclerView.adapter = adapter

        adapter.setOnAdvertisementClickListener(object :
            AdvertisementsAdapter.OnAdvertisementClickListener {
            override fun onAdvertisementClick(advertisement: Advertisement) {
                startAdsFragment(advertisement)
            }
        })

        binding.buttonAddAds.setOnClickListener {
            startAddAdsFragment()
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
            try {
                runBlocking {
                    adapter.advertisements = viewModel.findMyAdvertisements()
                }
            } catch (e: HttpException) {
                runBlocking {
                    Dependencies.tokenService.refreshTokens()
                    adapter.advertisements = viewModel.findMyAdvertisements()
                }
            }

            checkVisibility()
            binding.buttonEmptyAction.text = "Добавьте объявление"
            binding.buttonAddAds.setOnClickListener {
                startAddAdsFragment()
            }
        }

        binding.buttonFavourites.setOnClickListener {
            try {
                runBlocking {
                    adapter.advertisements = viewModel.getFavourites()
                }
            } catch (e: HttpException) {
                runBlocking {
                    Dependencies.tokenService.refreshTokens()
                    adapter.advertisements = viewModel.getFavourites()
                }
            }
            checkVisibility()
            binding.buttonEmptyAction.text = "Поиск"
            binding.buttonEmptyAction.setOnClickListener {
                startSearch()
            }
        }

        binding.buttonSharedUsage.setOnClickListener {
            try {
                runBlocking {
                    adapter.advertisements = viewModel.getSharedUsage()
                }
            } catch (e: HttpException) {
                runBlocking {
                    Dependencies.tokenService.refreshTokens()
                    adapter.advertisements = viewModel.getSharedUsage()
                }
            }
            checkVisibility()
            binding.buttonEmptyAction.text = "Поиск"
            binding.buttonEmptyAction.setOnClickListener {
                startSearch()
            }
        }

        binding.buttonPurchases.setOnClickListener {
            try {
                runBlocking {
                    adapter.advertisements = viewModel.getPurchases()
                }
            } catch (e: HttpException) {
                runBlocking {
                    Dependencies.tokenService.refreshTokens()
                    adapter.advertisements = viewModel.getPurchases()
                }
            }
            checkVisibility()
            binding.buttonEmptyAction.text = "Поиск"
            binding.buttonEmptyAction.setOnClickListener {
                startSearch()
            }
        }

    }

    private fun checkVisibility() {
        if (adapter.advertisements.isEmpty()) {
            binding.buttonEmptyAction.visibility = View.VISIBLE
        } else {
            binding.buttonEmptyAction.visibility = View.INVISIBLE
        }
    }

    private fun startAddAdsFragment() {
        disableAndHideElements()
        val fragment = AddAdvertisementFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutAds, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun startAdsFragment(advertisement: Advertisement) {
        disableAndHideElements()
        val fragment = AdvertisementFragment.newInstance()
        val bundle = Bundle()
        bundle.putLong("adsId", advertisement.id)
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutAds, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun startSearch() {
        startActivity(
            Intent(
                applicationContext,
                SearchActivity::class.java
            )
        )
        overridePendingTransition(0, 0)
    }


}