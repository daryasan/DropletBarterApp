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
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.adapters.AdvertisementsAdapter

class AdvertisementFragment : Fragment() {

    companion object {
        fun newInstance() = AdvertisementFragment()
    }

    private lateinit var viewModel: AdvertisementViewModel
    private lateinit var binding: FragmentAdvertisementBinding
    private lateinit var adapter: AdvertisementsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[AdvertisementViewModel::class.java]
        binding = FragmentAdvertisementBinding.inflate(layoutInflater)

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
                //fragment.arguments = bundle TODO pass args
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.adsLayout, newInstance())
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // viewModel
    }


    private fun setSimilar() {
        adapter.advertisements = List(10) {
            Advertisement(
                null, "Похожая книга", "Новая книжка", true, Category.OTHER, null
            )
        }
    }

}