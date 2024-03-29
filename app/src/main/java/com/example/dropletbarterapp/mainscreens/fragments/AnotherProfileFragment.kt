package com.example.dropletbarterapp.mainscreens.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.FragmentAnotherProfileBinding
import com.example.dropletbarterapp.databinding.FragmentChangeLoginsBinding
import com.example.dropletbarterapp.mainscreens.foryou.screens.MainActivity
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.adapters.AdvertisementsAdapter

class AnotherProfileFragment : Fragment() {

    companion object {
        fun newInstance() = AnotherProfileFragment()
    }

    private lateinit var viewModel: AnotherProfileViewModel
    private lateinit var binding: FragmentAnotherProfileBinding
    private lateinit var adapter: AdvertisementsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[AnotherProfileViewModel::class.java]
        binding = FragmentAnotherProfileBinding.inflate(layoutInflater)

        adapter = AdvertisementsAdapter()
        setAdvertisements()
        binding.recyclerViewAds.adapter = adapter

        adapter.setOnAdvertisementClickListener(object :
            AdvertisementsAdapter.OnAdvertisementClickListener {
            override fun onAdvertisementClick(advertisement: Advertisement) {
                val fragment = AdvertisementFragment.newInstance()
                //fragment.arguments = bundle TODO pass args
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.anotherProfileLayout, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })

        // go back
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


    private fun setAdvertisements() {
        adapter = AdvertisementsAdapter()
        adapter.advertisements = mutableListOf()
    }

}