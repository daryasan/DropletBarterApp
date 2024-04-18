package com.example.dropletbarterapp.mainscreens.profile.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.R
import com.example.dropletbarterapp.databinding.FragmentEditBinding
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.mainscreens.profile.screens.fragments.maps.MapsFragment
import com.example.dropletbarterapp.ui.images.CircleCrop
import com.example.dropletbarterapp.ui.images.ImageLoader
import com.example.dropletbarterapp.ui.images.ImageUtils
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.validators.UIMessageMan
import kotlinx.coroutines.runBlocking
import retrofit2.*

class EditDataFragment : Fragment() {

    companion object {
        fun newInstance() = EditDataFragment()

    }

    private lateinit var viewModel: EditDataViewModel
    private val uiMessageMan = UIMessageMan()
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
            if (uiMessageMan.checkIfNullsAndGetMessage(
                    "Введите имя", binding.editTextFirstName,
                    binding.errorMessageFirstName
                ) and uiMessageMan.checkIfNullsAndGetMessage(
                    "Введите фамилию", binding.editTextLastName,
                    binding.errorMessageLastName
                )
            ) {
                if (imageLoader.photo != null){
                    photo = imageLoader.photo
                }

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


        binding.editTextAddress.setOnClickListener {
            startMapsDataFragment()
        }


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
        return binding.root

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
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
        }
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

        if (data.photo != null){
            this.photo = photo
        }

        if (data.photo == null) {
            binding.buttonChangePhoto.text = "Добавить фото"
        } else {
            binding.buttonChangePhoto.text = "Изменить фото"
        }

    }

    private fun startMapsDataFragment() {
        val fragment = MapsFragment.newInstance()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(com.example.dropletbarterapp.R.id.frameLaoutEditData, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}