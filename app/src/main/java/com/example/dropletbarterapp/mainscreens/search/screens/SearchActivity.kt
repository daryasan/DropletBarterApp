package com.example.dropletbarterapp.mainscreens.search.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.ActivitySearchBinding
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.ui.adapters.CategoryAdapter
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
        adapter.setOnCategoryClickListener(object :
            CategoryAdapter.OnCategoryClickListener {
            override fun onCategoryClick(advertisement: Advertisement) {
                TODO("Not yet implemented")
            }
        })

        Navigation.setNavigation(this, R.id.search)

    }
}