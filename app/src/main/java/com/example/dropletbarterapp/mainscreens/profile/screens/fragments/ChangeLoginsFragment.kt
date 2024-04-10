package com.example.dropletbarterapp.mainscreens.profile.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dropletbarterapp.databinding.FragmentChangeLoginsBinding
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.validators.Toaster
import com.example.dropletbarterapp.validators.UIMessageMan
import com.example.dropletbarterapp.validators.Validator
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class ChangeLoginsFragment : Fragment() {

    companion object {
        fun newInstance() = ChangeLoginsFragment()
    }

    private lateinit var viewModel: ChangeLoginsViewModel
    private val uiMessageMan = UIMessageMan()
    private lateinit var binding: FragmentChangeLoginsBinding
    private val toaster = Toaster()
    private lateinit var userData: UserDataDto


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ChangeLoginsViewModel::class.java]

        fetchUserData()

        val validator = Validator()

        binding.buttonChangeEmail.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            if (uiMessageMan.checkLoginAndGetMessage(
                    binding.editTextEmail,
                    binding.errorMessageEmail
                )
            ) {
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
            val phone = binding.editTextPhone.text.toString()

            if (uiMessageMan.checkPhoneAndGetMessage(
                    binding.editTextPhone,
                    binding.errorMessagePhone
                )
            ) {
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
            }
        }

        binding.buttonChangePassword.setOnClickListener {
            val password = binding.editTextEditPassword.text.toString()
            if (uiMessageMan.checkRepeatPasswordAndGetMessage(
                    binding.editTextEditPassword,
                    binding.editTextRepeatPassword,
                    binding.errorMessagePassword,
                    binding.errorMessageRepeatPassword
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