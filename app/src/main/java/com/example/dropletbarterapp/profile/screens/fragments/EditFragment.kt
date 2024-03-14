package com.example.dropletbarterapp.profile.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.ActivityProfileBinding
import com.example.dropletbarterapp.databinding.FragmentEditBinding
import com.example.dropletbarterapp.profile.dto.UserDataDto
import com.example.dropletbarterapp.profile.screens.ProfileActivity
import com.example.dropletbarterapp.validators.Toaster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class EditFragment(override val coroutineContext: CoroutineContext) : Fragment(), CoroutineScope {

    private lateinit var userDataDto: UserDataDto
    private val viewModel: EditViewModel by viewModels()
    private lateinit var binding: FragmentEditBinding

    companion object {
        fun newInstance(): EditFragment {
            return EditFragment(Dispatchers.Main)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        @Suppress("DEPRECATION")
        userDataDto = requireArguments().getParcelable("userData")!!
        binding = FragmentEditBinding.inflate(layoutInflater)
        viewModel.setData(userDataDto)
        binding.buttonSave.setOnClickListener {
            viewModel.saveEditedData()
        }
        viewModel.saveEditedData()
        return view
    }

}