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
import com.example.dropletbarterapp.databinding.FragmentEditBinding
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.mainscreens.profile.screens.ProfileActivity
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.validators.Toaster
import com.example.dropletbarterapp.validators.Validator
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class ChangeLoginsFragment : Fragment() {

    companion object {
        fun newInstance() = ChangeLoginsFragment()
    }

    private lateinit var viewModel: ChangeLoginsViewModel
    private lateinit var binding: FragmentChangeLoginsBinding
    private val toaster = Toaster()
    private lateinit var userData: UserDataDto


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ChangeLoginsViewModel::class.java]

        fetchUserData()

        val validator = Validator(requireContext())

        binding.buttonChangeEmail.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            if (validator.validateLogin(email, true)) {
                try {
                    runBlocking {
                        viewModel.editEmail(email)
                    }
                } catch (e: HttpException) {
                    runBlocking {
                        Dependencies.tokenService.refreshTokens()
                        viewModel.editEmail(email)
                    }
                } catch (e: Exception) {
                    requireActivity().onBackPressed()
                }

                toaster.getToast(requireContext(), "Почта успешно изменена!")
            } else {
                toaster.getToast(requireContext(), "Неверный формат электронный почты!")
            }
        }

        binding.buttonChangePhone.setOnClickListener {
            var phone = binding.editTextPhone.text.toString()
            if (phone[0] == '+') {
                phone = phone.substring(1, phone.length - 1)
            }
            if (validator.validateLogin(phone, false)) {
                try {
                    runBlocking {
                        viewModel.editPhone(phone.toLong())
                    }
                } catch (e: HttpException) {
                    runBlocking {
                        Dependencies.tokenService.refreshTokens()
                        viewModel.editPhone(phone.toLong())
                    }
                } catch (e: Exception) {
                    requireActivity().onBackPressed()
                }

                toaster.getToast(requireContext(), "Телефон успешно изменен!")
            } else {
                toaster.getToast(requireContext(), "Неверный формат номера телефона!")
            }

        }

        binding.buttonChangePassword.setOnClickListener {
            val password = binding.editTextEditPassword.text.toString()
            if (validator.validateAndRepeatPassword(
                    password,
                    binding.editTextRepeatPassword.text.toString()
                )
            ) {
                try {
                    runBlocking {
                        viewModel.editPassword(password)
                    }
                } catch (e: HttpException) {
                    runBlocking {
                        Dependencies.tokenService.refreshTokens()
                        viewModel.editPassword(password)
                    }
                } catch (e: Exception) {
                    requireActivity().onBackPressed()
                }

                toaster.getToast(requireContext(), "Пароль успешно изменен!")
            } else {
                toaster.getToast(
                    requireContext(),
                    "Пароль должен содержать хотя бы одну заглавную букву, одно строчную и одну цифру"
                )
            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChangeLoginsBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun setData(userDataDto: UserDataDto) {
        if (userDataDto.email != null) {
            binding.editTextEmail.setText(userDataDto.email)
        }
        if (userDataDto.phone != null) {
            binding.editTextPhone.setText(userDataDto.phone.toString())
        }

    }

    private fun fetchUserData() {
        try {
            runBlocking {
                userData = viewModel.getUserData()
            }

        } catch (e: Exception) {
            toaster.getToast(requireContext(), e.message.toString())
            requireActivity().onBackPressed()
        }
        setData(userData)
    }

}