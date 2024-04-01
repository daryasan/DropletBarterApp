package com.example.dropletbarterapp.mainscreens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.FragmentAnotherProfileBinding
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.ui.adapters.AdvertisementsAdapter
import com.example.dropletbarterapp.ui.images.CircleCrop
import com.example.dropletbarterapp.ui.images.ImageUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

class AnotherProfileFragment : Fragment(), CoroutineScope {

    companion object {
        fun newInstance() = AnotherProfileFragment()
    }

    private lateinit var viewModel: AnotherProfileViewModel
    private lateinit var binding: FragmentAnotherProfileBinding
    private lateinit var adapter: AdvertisementsAdapter
    private lateinit var userDataDto: UserDataDto
    private lateinit var adsForUser: List<Advertisement>
    var layoutResource: Int = R.id.searchLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnotherProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AnotherProfileViewModel::class.java]
        val ownerId = requireArguments().getLong("ownerId")

        runBlocking {
            launch {
                userDataDto = viewModel.findUser(ownerId)
                adsForUser = viewModel.findAdvertisementsForUser(userDataDto.id)
                setData()
            }
        }

        setAdvertisements()

        adapter.setOnAdvertisementClickListener(object :
            AdvertisementsAdapter.OnAdvertisementClickListener {
            override fun onAdvertisementClick(advertisement: Advertisement) {
                startAdsFragment(advertisement)
            }
        })

        // go back
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                @Suppress("DEPRECATION")
                requireActivity().onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun setData() {
        ImageUtils.loadImageBitmap(
            userDataDto.photo,
            requireContext(),
            binding.imageViewAnotherProfilePhoto,
            CircleCrop()
        )

        binding.textViewUserName.text =
            if (userDataDto.firstName == null && userDataDto.lastName == null) {
                getString(R.string.alertMesFirstLastName)
            } else if (userDataDto.firstName == null) {
                userDataDto.lastName
            } else if (userDataDto.lastName == null) {
                userDataDto.firstName
            } else {
                getString(R.string.firstLastName, userDataDto.firstName, userDataDto.lastName)
            }
    }


    private fun setAdvertisements() {
        adapter = AdvertisementsAdapter()
        adapter.advertisements = adsForUser
        binding.recyclerViewAds.adapter = adapter
    }

    private fun startAdsFragment(advertisement: Advertisement) {
        val fragment = AdvertisementFragment.newInstance()
        val bundle = Bundle()
        bundle.putLong("adsId", advertisement.id)
        fragment.arguments = bundle
        fragment.layoutResource = layoutResource
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(layoutResource, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

}