package com.example.dropletbarterapp.mainscreens.advertisements.screens.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dropletbarterapp.BuildConfig
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.FragmentAddAdvertisementBinding
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.models.User
import com.example.dropletbarterapp.models.uimodels.UICategory
import com.example.dropletbarterapp.validators.Toaster
import java.io.File
import java.io.IOException
import java.util.*


class AddAdvertisementFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = AddAdvertisementFragment()
    }

    private lateinit var viewModel: AddAdvertisementViewModel
    private lateinit var binding: FragmentAddAdvertisementBinding
    private val toaster = Toaster()
    private var category: Category? = null
    private var photos: MutableList<String> = mutableListOf()
    private var name: String? = null
    private var description: String? = null

    // permissions
    private val galleryRequest =
        registerForActivityResult(StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                binding.imageViewEditPhoto.setImageURI(it.data?.data)
                photos.add(it.data?.data.toString())
                binding.buttonChangePhoto.text = "Изменить фото"
            } else {
                toaster.getToast(requireContext(), "Что-то пошло не так...")
            }
        }

    private val cameraRequest =
        registerForActivityResult(StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                // TODO select photo (cache)
//                val uuid: String = UUID.randomUUID().toString()
//                val outputDir: File = getCacheDir()
//                val file: File = File.createTempFile(uuid, ".jpg", outputDir)
//                val imageUri = FileProvider.getUriForFile(
//                    Objects.requireNonNull(
//                        requireContext()
//                    ),
//                    BuildConfig.APPLICATION_ID + ".fileProvider", file
//                )
//                binding.imageViewEditPhoto.setImageURI(imageUri)
//                photos.add(it.data?.data.toString())
            } else {
                toaster.getToast(requireContext(), "Что-то пошло не так...")
            }
        }

    private val requestPermissionCameraLauncher = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            toaster.getToast(requireContext(), "Невозможно продолжить без разрешений!")
        } else {
            takePhoto()
        }
    }

    private val requestPermissionStorageLauncher = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            toaster.getToast(requireContext(), "Невозможно продолжить без разрешений!")
        } else {
            selectPhotoFromGallery()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        galleryRequest
        cameraRequest
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAdvertisementBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AddAdvertisementViewModel::class.java]
        // spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categoryArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategory.adapter = adapter
        }
        binding.spinnerCategory.onItemSelectedListener = this

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

        // adding photo
        binding.buttonChangePhoto.setOnClickListener {
            addPhoto()
        }

        // submitting ads
        binding.buttonPublish.setOnClickListener {
            name = binding.editTextAdsName.text.toString()
            description = binding.editTextAdsDescription.text.toString()
            if (name == "") {
                toaster.getToast(requireContext(), "Название объявления не может быть пустым!")
            } else if (category == null) {
                toaster.getToast(requireContext(), "Выберите категорию объявления!")
            } else if (photos.isEmpty()) {
                toaster.getToast(requireContext(), "Выберите хотя бы одно фото для объявления!")
            } else {
                //toDO set user and photo array
                val advertisement =
                    Advertisement(photos[0], name, description, true, category, User())
                // TODO save to db
                finishFragment()
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val categorySelected = p0?.getItemAtPosition(p2)
        category = if (categorySelected.toString() == "Не выбрано") {
            null
        } else {
            UICategory.findCategoryByName(categorySelected.toString())
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        category = null
    }

    @Suppress("DEPRECATION")
    private fun finishFragment() {
        requireActivity().supportFragmentManager.popBackStack()
        requireActivity().onBackPressed()
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