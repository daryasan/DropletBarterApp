package com.example.dropletbarterapp.search.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.advertisements.screens.AdvertisementsAdapter
import com.example.dropletbarterapp.databinding.ActivityAdvertisementsBinding
import com.example.dropletbarterapp.databinding.ActivitySearchBinding
import com.example.dropletbarterapp.utils.Navigation

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CategoryAdapter()
        binding.recyclerView.adapter = adapter

        Navigation.setNavigation(this, R.id.search)

    }
}