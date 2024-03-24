package com.example.dropletbarterapp.mainscreens.search.screens

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CursorAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.ActivitySearchBinding
import com.example.dropletbarterapp.mainscreens.fragments.AdvertisementFragment
import com.example.dropletbarterapp.mainscreens.fragments.SearchFragment
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.models.uimodels.UICategory
import com.example.dropletbarterapp.ui.adapters.CategoryAdapter
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.utils.Navigation

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: CategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Dependencies.initDependencies(this)

        Navigation.setNavigation(this, R.id.search)

        //search
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    disableAndHideElements()
                    val fragment = SearchFragment.newInstance()
                    val bundle = Bundle()
                    bundle.putString("query", p0)
                    fragment.arguments = bundle
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.searchLayout, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        // category adapter
        adapter = CategoryAdapter()
        binding.recyclerView.adapter = adapter
        adapter.setOnCategoryClickListener(object :
            CategoryAdapter.OnCategoryClickListener {
            override fun onCategoryClick(category: Category) {
                disableAndHideElements()
                val fragment = SearchFragment.newInstance()
                val bundle = Bundle()
                bundle.putInt(
                    "categoryPos",
                    UICategory.getCategoryPos(category)
                )
                fragment.arguments = bundle
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.searchLayout, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })


    }

    override fun onBackPressed() {
        enableAndShowElements()
    }

    private fun disableAndHideElements() {
        binding.layoutSearchRoot.alpha = 0f
        binding.layoutSearchRoot.isEnabled = false
    }

    private fun enableAndShowElements() {
        binding.layoutSearchRoot.alpha = 1f
        binding.layoutSearchRoot.isEnabled = true
    }

}