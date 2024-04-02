package com.example.dropletbarterapp.mainscreens.profile.screens.fragments

import android.R
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dropletbarterapp.databinding.FragmentEditBinding
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.mainscreens.profile.screens.fragments.maps.YandexApi
import com.example.dropletbarterapp.ui.images.CircleCrop
import com.example.dropletbarterapp.ui.images.ImageLoader
import com.example.dropletbarterapp.ui.images.ImageUtils
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.validators.Toaster
import com.yandex.mapkit.MapKitFactory
import kotlinx.coroutines.runBlocking
import retrofit2.*

import retrofit2.converter.gson.GsonConverterFactory

class EditDataFragment : Fragment() {

    companion object {
        fun newInstance() = EditDataFragment()

    }

    private lateinit var viewModel: EditDataViewModel
    val toaster = Toaster()
    private lateinit var binding: FragmentEditBinding
    var photo: ByteArray? = null
    private lateinit var imageLoader: ImageLoader

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[EditDataViewModel::class.java]
        imageLoader = ImageLoader(requireContext(), this, binding.imageViewEditPhoto, CircleCrop())

        binding.buttonChangePhoto.setOnClickListener {
            imageLoader.addPhoto()
            binding.buttonChangePhoto.text = "Изменить фото"
        }

        binding.buttonSave.setOnClickListener {
            if (toaster.checkNullsAndGetToast(
                    "Введите имя и фамилию, чтобы сохранить!",
                    requireContext(),
                    binding.editTextFirstName.text.toString(),
                    binding.editTextLastName.text.toString()
                )
            ) {
                photo = imageLoader.photo
                try {
                    runBlocking {
                        viewModel.saveEditedData(
                            binding.editTextFirstName.text.toString(),
                            binding.editTextLastName.text.toString(),
                            photo,
                            binding.editTextAddress.text.toString(),
                        )
                    }
                } catch (e: HttpException) {
                    runBlocking {
                        Dependencies.tokenService.refreshTokens()
                        viewModel.saveEditedData(
                            binding.editTextFirstName.text.toString(),
                            binding.editTextLastName.text.toString(),
                            photo,
                            binding.editTextAddress.text.toString(),
                        )
                    }
                } catch (e: Exception) {
                    requireActivity().onBackPressed()
                }

                requireActivity().onBackPressed()
            }
        }

        // address
        val adapter =
            ArrayAdapter(
                requireContext(),
                R.layout.simple_dropdown_item_1line,
                listOf<String>()
            )
        binding.editTextAddress.setAdapter(adapter)

//        binding.editTextAddress.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//                val retrofit = Retrofit.Builder().baseUrl("https://geocode-maps.yandex.ru/")
//                    .addConverterFactory(GsonConverterFactory.create()).build()
//                val api: YandexApi = retrofit.create(YandexApi::class.java)
//                val call = api.suggestAddresses(
//                    "b97b0bac-f872-49a3-a911-2699275df4db",
//                    p0.toString()
//                )
//
//                call.enqueue(object : Callback<String> {
//                    override fun onResponse(call: Call<String>, response: Response<String>) {
//                        if (response.isSuccessful) {
//                            val addressesList = mutableListOf<String>()
//                            response.body()?.let { address ->
//                                addressesList.add(address)
//                            }
//                            adapter.clear()
//                            adapter.addAll(addressesList)
//                            adapter.notifyDataSetChanged()
//                        } else {
//                            Log.d(
//                                "call",
//                                "error in api ${response.message()} ${response.body()} ${response.code()}"
//                            )
//                        }
//                    }
//
//                    override fun onFailure(call: Call<String>, t: Throwable) {
//                        Log.d("call", "error in api ${t.message}")
//                    }
//
//                })
//
//            }
//
//            override fun afterTextChanged(p0: Editable?) {}
//
//        })

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        try {
            runBlocking {
                val userData = viewModel.getUserData()
                setData(userData)
            }
        } catch (e: HttpException) {
            runBlocking {
                Dependencies.tokenService.refreshTokens()
                val userData = viewModel.getUserData()
                setData(userData)
            }
        } catch (e: Exception) {
            requireActivity().onBackPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(layoutInflater)
        MapKitFactory.setApiKey("b97b0bac-f872-49a3-a911-2699275df4db");
        return binding.root

    }


    private fun setData(data: UserDataDto) {
        if (data.firstName != null) {
            binding.editTextFirstName.setText(data.firstName)
        }
        if (data.lastName != null) {
            binding.editTextLastName.setText(data.lastName)
        }
        if (data.address != null) {
            binding.editTextAddress.setText(data.address)
        }
        ImageUtils.loadImageBitmap(
            data.photo,
            requireContext(),
            binding.imageViewEditPhoto,
            CircleCrop()
        )
        if (data.photo == null) {
            binding.buttonChangePhoto.text = "Добавить фото"
        } else {
            binding.buttonChangePhoto.text = "Изменить фото"
        }

    }

}