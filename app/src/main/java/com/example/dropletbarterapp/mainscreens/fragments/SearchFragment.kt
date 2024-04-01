package com.example.dropletbarterapp.mainscreens.fragments

import android.annotation.SuppressLint
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
import com.example.dropletbarterapp.ui.adapters.AdvertisementsAdapter
import com.example.dropletbarterapp.ui.models.UICategory
import kotlinx.coroutines.runBlocking

class SearchFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: AdvertisementsAdapter
    private var query: String? = null
    private var categoryPos: Int? = 0
    private var closer = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding = FragmentSearchBinding.inflate(layoutInflater)


        // go back
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AdvertisementsAdapter()

        query = arguments?.getString("query")
//        if (query != null) {
//            binding.searchBarMain.setQuery(query, false)
//        }

        categoryPos = arguments?.getInt("categoryPos")
        if (categoryPos != null) {
            binding.filterCategory.setSelection(categoryPos!!)
        }

        setAdvertisements()
        binding.recyclerView.adapter = adapter

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
                startAdsFragment(advertisement)
            }
        })

//        binding.searchBarMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                if (p0 != null) {
//                    query = p0
//                    setAdvertisements()
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//                return false
//            }
//        })


        if (binding.checkboxClose.isActivated) {
            closer = true
            setAdvertisements()
        } else {
            closer = false
            setAdvertisements()
        }

        binding.checkboxClose.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                closer = true
                setAdvertisements()
            } else {
                closer = false
                setAdvertisements()
            }
        }
        binding.filterCategory.setSelection(categoryPos!!)

    }

    override fun onResume() {
        super.onResume()
        if (adapter.advertisements.isEmpty()) {
            binding.nothingFound.alpha = 1f
        } else {
            binding.nothingFound.alpha = 0f
        }
        binding.filterCategory.setSelection(categoryPos!!)
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setAdvertisements() {
        adapter.advertisements = viewModel.findAdvertisements(query, categoryPos, closer)
        adapter.notifyDataSetChanged()
        if (adapter.advertisements.isEmpty()) {
            binding.nothingFound.alpha = 1f
        } else {
            binding.nothingFound.alpha = 0f
        }

    }

    private fun startAdsFragment(advertisement: Advertisement) {
        val bundle = Bundle()
        bundle.putLong("adsId", advertisement.id)
        val fragment = AdvertisementFragment.newInstance()
        fragment.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.searchLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        val category = p0?.getItemAtPosition(p2)
        if (category.toString() == "Не выбрано") {
            categoryPos = null
            setAdvertisements()
        } else {
            categoryPos = p2
            setAdvertisements()
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        categoryPos = null
        setAdvertisements()
    }

}