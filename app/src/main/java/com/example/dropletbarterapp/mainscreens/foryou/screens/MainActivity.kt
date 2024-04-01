package com.example.dropletbarterapp.mainscreens.foryou.screens

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.auth.screens.LoginActivity
import com.example.dropletbarterapp.databinding.ActivityMainBinding
import com.example.dropletbarterapp.mainscreens.fragments.AdvertisementFragment
import com.example.dropletbarterapp.mainscreens.fragments.AnotherProfileFragment
import com.example.dropletbarterapp.mainscreens.fragments.SearchFragment
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.adapters.AdvertisementsAdapter
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.ui.Navigation
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AdvertisementsAdapter
    private lateinit var viewModel: MainViewModel

    //private val toaster = Toaster()
    private var isLoading = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = MainViewModel()
        setContentView(binding.root)
        Dependencies.initDependencies(this)

        // if not authorized -> authorize
        if (Dependencies.tokenService.getAccessToken() == null) {
            authorize()
        }
        Navigation.setNavigation(this, R.id.main)

        adapter = AdvertisementsAdapter()
        //adapter.advertisements = viewModel.findAllAdvertisements()
        adapter.advertisements = mutableListOf()

        binding.recyclerView.adapter = adapter
        adapter.setOnAdvertisementClickListener(object :
            AdvertisementsAdapter.OnAdvertisementClickListener {
            override fun onAdvertisementClick(advertisement: Advertisement) {
                disableAndHideElements()
                startAdvertisementFragment(advertisement)
            }
        })
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
        binding.buttonAllAdvertisements.setOnClickListener {
            adapter.advertisements = viewModel.findAllAdvertisements()
        }

        binding.buttonSharedUsage.setOnClickListener {
            adapter.advertisements = viewModel.findSharedUsage()
        }

        binding.buttonClose.setOnClickListener {
            adapter.advertisements = mutableListOf()
        }
    }

    private fun startAdvertisementFragment(advertisement: Advertisement) {
        disableAndHideElements()
        val bundle = Bundle()
        bundle.putLong("adsId", advertisement.id)
        val fragment = AdvertisementFragment.newInstance()
        fragment.layoutResource = R.id.forYouLayout
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.forYouLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}