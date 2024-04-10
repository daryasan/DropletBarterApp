package com.example.dropletbarterapp.mainscreens.foryou.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
import com.example.dropletbarterapp.ui.models.UICategory
import com.yandex.mapkit.MapKitFactory
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AdvertisementsAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = MainViewModel()
        setContentView(binding.root)
        Dependencies.initDependencies(this)

        // if not authorized -> authorize
        if (Dependencies.tokenService.getAccessToken() == null) {
            authorize()
        } else {

            runBlocking {
                Dependencies.tokenService.refreshTokens()
            }
            Navigation.setNavigation(this, R.id.main)

            adapter = AdvertisementsAdapter()
            chooseActiveButton(binding.buttonAllAdvertisements)
            adapter.advertisements = viewModel.findAllAdvertisements()

            binding.recyclerView.adapter = adapter
            adapter.setOnAdvertisementClickListener(object :
                AdvertisementsAdapter.OnAdvertisementClickListener {
                override fun onAdvertisementClick(advertisement: Advertisement) {
                    disableAndHideElements()
                    startAdvertisementFragment(advertisement)
                }
            })
            setButtonsChange()


            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    startSearchFragment(p0.toString())
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return true
                }

            })

        }
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
            chooseActiveButton(binding.buttonAllAdvertisements)
            adapter.advertisements = viewModel.findAllAdvertisements()
        }

        binding.buttonSharedUsage.setOnClickListener {
            chooseActiveButton(binding.buttonSharedUsage)
            adapter.advertisements = viewModel.findSharedUsage()
        }

        binding.buttonRecent.setOnClickListener {
            chooseActiveButton(binding.buttonRecent)
            adapter.advertisements = viewModel.findRecent()
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

    private fun chooseActiveButton(button: Button) {
        button.setBackgroundResource(R.drawable.rounded_button)
        button.setTextColor(resources.getColor(R.color.white))

        if (button != binding.buttonRecent) {
            binding.buttonRecent.setBackgroundResource(R.drawable.rounded_button_unactive)
            binding.buttonRecent.setTextColor(resources.getColor(R.color.main_color))
        }

        if (button != binding.buttonAllAdvertisements) {
            binding.buttonAllAdvertisements.setBackgroundResource(R.drawable.rounded_button_unactive)
            binding.buttonAllAdvertisements.setTextColor(resources.getColor(R.color.main_color))
        }

        if (button != binding.buttonSharedUsage) {
            binding.buttonSharedUsage.setBackgroundResource(R.drawable.rounded_button_unactive)
            binding.buttonSharedUsage.setTextColor(resources.getColor(R.color.main_color))
        }
    }

    private fun startSearchFragment(query: String) {
        disableAndHideElements()
        val fragment = SearchFragment.newInstance()
        val bundle = Bundle()
        bundle.putString(
            "query",
            query
        )
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.forYouLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}