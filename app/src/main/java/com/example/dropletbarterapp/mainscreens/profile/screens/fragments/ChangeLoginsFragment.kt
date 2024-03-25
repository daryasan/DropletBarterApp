package com.example.dropletbarterapp.mainscreens.profile.screens.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.FragmentChangeLoginsBinding
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.mainscreens.profile.screens.ProfileActivity
import com.example.dropletbarterapp.validators.Validator

class ChangeLoginsFragment : Fragment() {

    companion object {
        fun newInstance() = ChangeLoginsFragment()
    }

    private lateinit var viewModel: ChangeLoginsViewModel
    private lateinit var binding: FragmentChangeLoginsBinding

    //private lateinit var userData: UserDataDto

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val validator = Validator(requireContext())
        viewModel = ViewModelProvider(this)[ChangeLoginsViewModel::class.java]
        binding = FragmentChangeLoginsBinding.inflate(layoutInflater)

        binding.buttonChangeEmail.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            if (validator.validateLogin(email, true)) {
                viewModel.editEmail(email)
            }

        }

        binding.buttonChangePhone.setOnClickListener {
            val phone = binding.editTextPhone.text.toString()
            if (validator.validateLogin(phone, false)) {
                viewModel.editPhone(phone.toLong())
            }

        }

        binding.buttonChangePassword.setOnClickListener {
            val password = binding.editTextEditPassword.text.toString()
            if (validator.validateAndRepeatPassword(
                    password,
                    binding.editTextRepeatPassword.text.toString()
                )
            ) {
                viewModel.editPassword(password)
            }
        }

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

//    override fun onDestroyView() {
//        super.onDestroyView()
//        (activity as ProfileActivity).enableAndShowElements()
//        requireActivity().supportFragmentManager.popBackStack()
//    }

}