package com.example.dropletbarterapp.mainscreens.advertisements.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.ActivityAdvertisementsBinding
import com.example.dropletbarterapp.mainscreens.advertisements.screens.fragments.AddAdvertisementFragment
import com.example.dropletbarterapp.mainscreens.fragments.ads.AdvertisementFragment
import com.example.dropletbarterapp.mainscreens.search.screens.SearchActivity
import com.example.dropletbarterapp.models.Advertisement
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

        chooseActiveButton(binding.buttonMyAdvertisements)
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
        binding.buttonEmptyAction.setOnClickListener {
            startAddAdsFragment()
        }

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
                chooseActiveButton(binding.buttonMyAdvertisements)
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
            binding.buttonEmptyAction.setOnClickListener {
                startAddAdsFragment()
            }
        }

        binding.buttonFavourites.setOnClickListener {
            try {
                chooseActiveButton(binding.buttonFavourites)
                runBlocking {
                    adapter.advertisements = viewModel.getFavourites()
                    adapter.notifyDataSetChanged()
                }
            } catch (e: HttpException) {
                runBlocking {
                    Dependencies.tokenService.refreshTokens()
                    adapter.advertisements = viewModel.getFavourites()
                    adapter.notifyDataSetChanged()
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
                chooseActiveButton(binding.buttonSharedUsage)
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
                chooseActiveButton(binding.buttonPurchases)
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
        bundle.putInt("layoutResource", R.id.frameLayoutAds)
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

    private fun chooseActiveButton(button: Button) {
        button.setBackgroundResource(R.drawable.rounded_button)
        button.setTextColor(resources.getColor(R.color.white))

        if (button != binding.buttonMyAdvertisements) {
            binding.buttonMyAdvertisements.setBackgroundResource(R.drawable.rounded_button_unactive)
            binding.buttonMyAdvertisements.setTextColor(resources.getColor(R.color.main_color))
        }

        if (button != binding.buttonFavourites) {
            binding.buttonFavourites.setBackgroundResource(R.drawable.rounded_button_unactive)
            binding.buttonFavourites.setTextColor(resources.getColor(R.color.main_color))
        }

        if (button != binding.buttonPurchases) {
            binding.buttonPurchases.setBackgroundResource(R.drawable.rounded_button_unactive)
            binding.buttonPurchases.setTextColor(resources.getColor(R.color.main_color))
        }

        if (button != binding.buttonSharedUsage) {
            binding.buttonSharedUsage.setBackgroundResource(R.drawable.rounded_button_unactive)
            binding.buttonSharedUsage.setTextColor(resources.getColor(R.color.main_color))
        }

    }


}