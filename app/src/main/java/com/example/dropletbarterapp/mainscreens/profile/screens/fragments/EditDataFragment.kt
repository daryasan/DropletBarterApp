package com.example.dropletbarterapp.mainscreens.profile.screens.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.FragmentEditBinding
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.mainscreens.profile.screens.ProfileActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


class EditDataFragment : Fragment() {

    companion object {
        fun newInstance() = EditDataFragment()
    }

    private lateinit var viewModel: EditDataViewModel

    //private lateinit var userDataDto: UserDataDto
    private lateinit var binding: FragmentEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[EditDataViewModel::class.java]
        binding = FragmentEditBinding.inflate(layoutInflater)
        //viewModel.setData(userDataDto)
        binding.buttonSave.setOnClickListener {
            //viewModel.saveEditedData()

            returnToActivity()
        }
        return binding.root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        //userDataDto = requireArguments().getParcelable("userData")
    }


//    private fun setData(data: UserDataDto) {
//        if (data.firstName != null) {
//            binding.editTextFirstName.setText(data.firstName)
//        }
//        if (data.lastName != null) {
//            binding.editTextLastName.setText(data.lastName)
//        }
//        if (data.address != null) {
//            binding.editTextAddress.setText(data.address)
//        }
//
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as ProfileActivity).enableAndShowElements()
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun returnToActivity() {
        (requireActivity() as ProfileActivity).enableAndShowElements()
        requireActivity().supportFragmentManager.popBackStack()
    }

}