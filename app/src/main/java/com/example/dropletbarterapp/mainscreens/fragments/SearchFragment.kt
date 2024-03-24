package com.example.dropletbarterapp.mainscreens.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.FragmentSearchBinding
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.uimodels.UICategory
import com.example.dropletbarterapp.ui.adapters.AdvertisementsAdapter

class SearchFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: AdvertisementsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding = FragmentSearchBinding.inflate(layoutInflater)
        adapter = AdvertisementsAdapter()


        // adapter for dropdown list
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categoryArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.filterCategory.adapter = adapter
        }
        binding.filterCategory.onItemSelectedListener = this


        // adapter for ads
        adapter.setOnAdvertisementClickListener(object :
            AdvertisementsAdapter.OnAdvertisementClickListener {
            override fun onAdvertisementClick(advertisement: Advertisement) {
                val fragment = AdvertisementFragment.newInstance()
                val bundle = Bundle()
                //bundle.putParcelable("advertisement", advertisement)
                fragment.arguments = bundle
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.searchLayout, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })


        // go back
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragmentManager = requireActivity().supportFragmentManager
                if (fragmentManager.backStackEntryCount > 0) {
                    if (fragmentManager.backStackEntryCount == 1) {
                        requireActivity().onBackPressed()
                    }
                    fragmentManager.popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoryPos = arguments?.getInt("categoryPos")
        if (categoryPos != null) {
            // TODO get all ads
            binding.filterCategory.setSelection(categoryPos)
        }

        val query = arguments?.getString("query")
        if (query != null) {
            binding.searchBarMain.setQuery(query, false)
        }

        binding.searchBarMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    setAdvertisements(viewModel.findAdvertisements())
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })


        if (binding.checkboxClose.isActivated) {
            setAdvertisements(viewModel.findCloserAdvertisements())
        } else {
            setAdvertisements(viewModel.findAdvertisements())
        }

        binding.checkboxClose.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setAdvertisements(viewModel.findCloserAdvertisements())
            } else {
                setAdvertisements(viewModel.findAdvertisements())
            }
        }

        binding.recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        if (adapter.advertisements.isEmpty()) {
            binding.nothingFound.alpha = 1f
        } else {
            binding.nothingFound.alpha = 0f
        }
    }


    private fun setAdvertisements(ads: List<Advertisement>) {
        adapter.advertisements = ads
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        val category = p0?.getItemAtPosition(p2)
        if (category.toString() == "Не выбрано") {
            setAdvertisements(viewModel.findAdvertisements())
        } else {
            setAdvertisements(
                viewModel.findCategoryAdvertisement(
                    adapter.advertisements,
                    UICategory.findCategoryByName(category.toString())
                )
            )
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        setAdvertisements(viewModel.findAdvertisements())
    }

}