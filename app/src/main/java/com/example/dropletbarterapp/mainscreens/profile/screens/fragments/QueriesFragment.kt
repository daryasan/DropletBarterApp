package com.example.dropletbarterapp.mainscreens.profile.screens.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.FragmentQueriesBinding
import com.example.dropletbarterapp.ui.adapters.QueryAdapter

class QueriesFragment : Fragment() {

    companion object {
        fun newInstance() = QueriesFragment()
    }

    private lateinit var viewModel: QueriesViewModel
    private lateinit var binding: FragmentQueriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQueriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[QueriesViewModel::class.java]

        val adapter = QueryAdapter()
        adapter.queries = viewModel.getQueries()
        if (adapter.queries.isEmpty()) {
            binding.emptyQueries.alpha = 1f
        } else {
            binding.emptyQueries.alpha = 0f
        }

        binding.recyclerViewQueries.adapter = adapter

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

}