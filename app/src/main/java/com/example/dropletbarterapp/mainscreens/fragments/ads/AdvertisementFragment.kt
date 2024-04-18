package com.example.dropletbarterapp.mainscreens.fragments.ads

import android.content.Intent
import android.net.Uri
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
import com.example.dropletbarterapp.mainscreens.fragments.AnotherProfileFragment
import com.example.dropletbarterapp.mainscreens.fragments.EditAdvertisementFragment
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.adapters.AdvertisementsAdapter
import com.example.dropletbarterapp.ui.images.CircleCrop
import com.example.dropletbarterapp.ui.images.ImageUtils
import com.example.dropletbarterapp.ui.images.SquareCrop
import com.example.dropletbarterapp.ui.models.UICategory
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
    var layoutResource: Int = R.id.searchLayout


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

        layoutResource = requireArguments().getInt("layoutResource")

        runBlocking {
            advertisement = viewModel.findAdvertisement(adsId)
            ownerData = viewModel.findOwner(advertisement)
            setData()
        }

        binding.linLayoutSeller.setOnClickListener {
            startProfileFragment(advertisement)
        }

        binding.textViewAddress.setOnClickListener {
            startMaps()
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

        if (advertisement.ownerId == viewModel.getUserId()) {
            binding.buttonTake.text = "Изменить"
            binding.buttonContact.text = "Скрыть"

            binding.buttonTake.setOnClickListener {
                startEditAdsFragment()
            }

            binding.buttonContact.setOnClickListener {
                if (advertisement.statusActive) {
                    viewModel.hideAdvertisement(advertisement)
                }
                binding.buttonContact.text = "В архиве!"
                binding.imageViewAdsImage.alpha = 0.7f
                binding.buttonTake.isEnabled = false
            }

        } else {
            if (advertisement.category == UICategory.getPosByCategory(Category.SHARED_USAGE)) {
                binding.buttonTake.text = "Использовать"
            }
            binding.buttonTake.setOnClickListener {
                viewModel.sendPendingRequest(advertisement)
                binding.buttonTake.text = "Отправлено"
            }

            binding.buttonContact.setOnClickListener {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel: ${ownerData.phone}")))
            }
        }


        // go back
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                @Suppress("DEPRECATION")
                requireActivity().onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


    private fun setSimilar() {
        adapter.advertisements = viewModel.findSuggestions(advertisement, 10)
    }

    private fun setData() {

        ImageUtils.loadImageBitmap(
            advertisement.photo,
            requireContext(),
            binding.imageViewAdsImage,
            SquareCrop()
        )

        if (!advertisement.statusActive) {
            binding.imageViewAdsImage.alpha = 0.7f
            binding.buttonTake.isEnabled = false
            binding.buttonContact.text = "В архиве!"
        }

        binding.textViewAdsName.text = advertisement.name
        binding.textViewAdsDescription.text = advertisement.description
        binding.textViewSellerName.text =
            getString(R.string.firstLastName, ownerData.firstName, ownerData.lastName)
        if (ownerData.address != null) {
            binding.textViewAddress.text = ownerData.address
        }
        if (advertisement.description == null) {
            binding.textViewAdsDescription.height = 0
        }

        ImageUtils.loadImageBitmap(
            ownerData.photo,
            requireContext(),
            binding.imageViewSellerPhoto,
            CircleCrop()
        )
    }

    private fun startAdsFragment(advertisement: Advertisement) {
        val bundle = Bundle()
        bundle.putLong("adsId", advertisement.id)
        bundle.putInt("layoutResource", layoutResource)
        val fragment = newInstance()
        fragment.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(layoutResource, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun startEditAdsFragment() {
        val bundle = Bundle()
        bundle.putLong("adsId", advertisement.id)
        val fragment = EditAdvertisementFragment.newInstance()
        fragment.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(layoutResource, fragment) // TRUE
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun startProfileFragment(advertisement: Advertisement) {
        val bundle = Bundle()
        bundle.putLong("ownerId", advertisement.ownerId)
        val fragment = AnotherProfileFragment.newInstance()
        bundle.putInt("layoutResource", layoutResource)
        fragment.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(layoutResource, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun startMaps() {
        if (ownerData.address != null && ownerData.address != "") {
            val bundle = Bundle()
            bundle.putString("address", ownerData.address)
            val fragment = ShowAdsOnMapFragment.newInstance()
            fragment.arguments = bundle
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(layoutResource, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }


}