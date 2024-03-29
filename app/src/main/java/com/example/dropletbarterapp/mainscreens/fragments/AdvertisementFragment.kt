package com.example.dropletbarterapp.mainscreens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.FragmentAdvertisementBinding
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.adapters.AdvertisementsAdapter
import com.example.dropletbarterapp.ui.images.ImageUtils
import com.example.dropletbarterapp.ui.images.SquareCrop
import kotlinx.coroutines.runBlocking

class AdvertisementFragment : Fragment() {

    companion object {
        fun newInstance() = AdvertisementFragment()
    }

    private lateinit var viewModel: AdvertisementViewModel
    private lateinit var binding: FragmentAdvertisementBinding
    private lateinit var adapter: AdvertisementsAdapter
    private lateinit var advertisement: Advertisement
    private lateinit var ownerData: UserDataDto


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdvertisementBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AdvertisementViewModel::class.java]
        val adsId = requireArguments().getLong("adsId")

        runBlocking {
            advertisement = viewModel.findAdvertisement(adsId)
            ownerData = viewModel.findOwner(advertisement)
            setData()
        }

        binding.linLayoutSeller.setOnClickListener {
            val fragment = AnotherProfileFragment.newInstance()
            //fragment.arguments = bundle TODO pass args
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.adsLayout, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        adapter = AdvertisementsAdapter()
        setSimilar()
        binding.recyclerView.post {
            binding.recyclerView.adapter = adapter
        }

        adapter.setOnAdvertisementClickListener(object :
            AdvertisementsAdapter.OnAdvertisementClickListener {
            override fun onAdvertisementClick(advertisement: Advertisement) {
                startAdsFragment(advertisement)
            }
        })


        // go back
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


    private fun setSimilar() {
        adapter.advertisements = mutableListOf()
    }

    private fun setData() {
        ImageUtils.loadImageBitmap(
            advertisement.photo,
            requireContext(),
            binding.imageViewAdsImage,
            SquareCrop()
        )
        binding.textViewAdsName.text = advertisement.name
        binding.textViewAdsDescription.text = advertisement.description
        binding.textViewAdsName.text =
            getString(R.string.firstLastName, ownerData.firstName, ownerData.lastName)
        if (ownerData.address != null) {
            binding.textViewAddress.text = ownerData.address
        }

        ImageUtils.loadImageBitmap(
            ownerData.photo,
            requireContext(),
            binding.imageViewSellerPhoto,
            SquareCrop()
        )

    }

    private fun startAdsFragment(advertisement: Advertisement) {
        val bundle = Bundle()
        bundle.putLong("adsId", advertisement.id)
        val fragment = newInstance()
        fragment.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.adsLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}