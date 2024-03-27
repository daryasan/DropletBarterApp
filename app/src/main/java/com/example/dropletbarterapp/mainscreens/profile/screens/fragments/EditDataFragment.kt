package com.example.dropletbarterapp.mainscreens.profile.screens.fragments

import android.Manifest
import android.R
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dropletbarterapp.BuildConfig
import com.example.dropletbarterapp.databinding.FragmentEditBinding
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.mainscreens.profile.screens.fragments.maps.YandexApi
import com.example.dropletbarterapp.utils.Utils
import com.example.dropletbarterapp.validators.Toaster
import com.yandex.mapkit.MapKitFactory
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.InputStream
import java.util.*

class EditDataFragment : Fragment() {

    companion object {
        fun newInstance() = EditDataFragment()

    }

    private lateinit var viewModel: EditDataViewModel
    private val toaster = Toaster()
    private lateinit var binding: FragmentEditBinding
    private var photo: ByteArray? = null

    // permissions
    private val galleryRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val selectedImageUri: Uri? = it.data?.data
                binding.imageViewEditPhoto.setImageURI(it.data?.data)
                selectedImageUri?.let { uri ->
                    val inputStream: InputStream? =
                        requireContext().contentResolver.openInputStream(uri)
                    inputStream?.use { stream ->
                        photo = stream.readBytes()
                    }
                }
                binding.buttonChangePhoto.text = "Изменить фото"
            } else {
                toaster.getToast(requireContext(), "Что-то пошло не так...")
            }
        }

    private val cameraRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uuid: String = UUID.randomUUID().toString()
                val outputDir: File = requireContext().cacheDir
                val file: File = File.createTempFile(uuid, ".jpg", outputDir)
                val imageUri = FileProvider.getUriForFile(
                    Objects.requireNonNull(requireContext()),
                    BuildConfig.APPLICATION_ID + ".provider", file
                )
                binding.imageViewEditPhoto.setImageURI(imageUri)
                photo = File(it.data?.data.toString()).readBytes()
            } else {
                toaster.getToast(requireContext(), "Что-то пошло не так...")
            }
        }

    private val requestPermissionCameraLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            toaster.getToast(requireContext(), "Невозможно продолжить без разрешений!")
        } else {
            takePhoto()
        }
    }

    private val requestPermissionStorageLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            toaster.getToast(requireContext(), "Невозможно продолжить без разрешений!")
        } else {
            selectPhotoFromGallery()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[EditDataViewModel::class.java]

        binding.buttonChangePhoto.setOnClickListener {
            addPhoto()
        }

        binding.buttonSave.setOnClickListener {
            if (toaster.checkNullsAndGetToast(
                    "Введите имя и фамилию, чтобы сохранить!",
                    requireContext(),
                    binding.editTextFirstName.text.toString(),
                    binding.editTextLastName.text.toString()
                )
            ) {
                runBlocking {
                    viewModel.saveEditedData(
                        binding.editTextFirstName.text.toString(),
                        binding.editTextLastName.text.toString(),
                        photo,
                        binding.editTextAddress.text.toString(),
                    )
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

        binding.editTextAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val retrofit = Retrofit.Builder().baseUrl("https://geocode-maps.yandex.ru/")
                    .addConverterFactory(GsonConverterFactory.create()).build()
                val api: YandexApi = retrofit.create(YandexApi::class.java)
                val call = api.suggestAddresses(
                    "b97b0bac-f872-49a3-a911-2699275df4db",
                    p0.toString()
                )

                call.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            val addressesList = mutableListOf<String>()
                            response.body()?.let { address ->
                                addressesList.add(address)
                            }
                            adapter.clear()
                            adapter.addAll(addressesList)
                            adapter.notifyDataSetChanged()
                        } else {
                            Log.d(
                                "call",
                                "error in api ${response.message()} ${response.body()} ${response.code()}"
                            )
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("call", "error in api ${t.message}")
                    }

                })

            }

            override fun afterTextChanged(p0: Editable?) {}

        })

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

        } catch (e: Exception) {
            toaster.getToast(requireContext(), e.message.toString())
            requireActivity().onBackPressed()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MapKitFactory.initialize(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(layoutInflater)

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
        if (data.photo != null) {
            binding.imageViewEditPhoto.setImageBitmap(Utils.decodeBitmapImageFromDB(data.photo!!))
        }

    }

    private fun selectPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryRequest.launch(intent)
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraRequest.launch(intent)
    }

    private fun addPhoto() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Выберите действие:")
        builder.setItems(arrayOf("Сделать фото", "Выбрать из галереи")) { dialog, action ->
            when (action) {
                0 -> {
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        takePhoto()
                    } else {
                        requestPermissionCameraLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
                1 -> {
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        selectPhotoFromGallery()
                    } else {
                        requestPermissionStorageLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }

                }
            }
        }
        builder.show()
    }

}