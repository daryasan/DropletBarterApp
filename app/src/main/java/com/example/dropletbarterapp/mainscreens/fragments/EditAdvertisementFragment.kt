package com.example.dropletbarterapp.mainscreens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.FragmentEditAdvertisementBinding
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.images.ImageLoader
import com.example.dropletbarterapp.ui.images.ImageUtils
import com.example.dropletbarterapp.ui.images.SquareCrop
import com.example.dropletbarterapp.ui.models.UICategory
import com.example.dropletbarterapp.validators.Toaster
import com.example.dropletbarterapp.validators.UIMessageMan
import kotlinx.coroutines.runBlocking
import java.util.*

class EditAdvertisementFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = EditAdvertisementFragment()
    }

    private val uiMessageMan = UIMessageMan()
    private lateinit var viewModel: EditAdvertisementViewModel
    private lateinit var binding: FragmentEditAdvertisementBinding
    private lateinit var advertisement: Advertisement
    private lateinit var imageLoader: ImageLoader
    private val toaster = Toaster()
    private var category: Category? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditAdvertisementBinding.inflate(layoutInflater)

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

    private fun setData() {
        ImageUtils.loadImageBitmap(
            advertisement.photo,
            requireContext(),
            binding.imageViewEditPhoto,
            SquareCrop()
        )
        imageLoader.photo = Base64.getDecoder().decode(advertisement.photo)
        binding.spinnerCategory.setSelection(advertisement.category)
        binding.editTextAdsName.setText(advertisement.name)
        binding.editTextAdsDescription.setText(advertisement.description)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[EditAdvertisementViewModel::class.java]
        imageLoader = ImageLoader(requireContext(), this, binding.imageViewEditPhoto, SquareCrop())
        val adsId = requireArguments().getLong("adsId")

        runBlocking {
            advertisement = viewModel.findAdvertisement(adsId)
        }

        setData()


        // adding photo
        binding.buttonChangePhoto.setOnClickListener {
            imageLoader.addPhoto()
            binding.buttonChangePhoto.text = "Изменить фото"
        }

        // submitting ads
        binding.buttonPublish.setOnClickListener {
            val name = binding.editTextAdsName.text.toString()
            val description = binding.editTextAdsDescription.text.toString()
            val photo = imageLoader.photo

            if (category == null) {
                toaster.getToast(requireContext(), "Выберите категорию объявления!")
            } else if (photo == null) {
                toaster.getToast(requireContext(), "Выберите фото для объявления!")
            } else if (uiMessageMan.checkIfNullsAndGetMessage(
                    "Введите название",
                    binding.editTextAdsName,
                    binding.errorAdsName
                )
            ) {
                runBlocking {
                    viewModel.editAdvertisement(
                        advertisement,
                        photo,
                        name,
                        description,
                        category!!,
                        true
                    )
                }
                finishFragment()
            }
        }

        binding.spinnerCategory.setSelection(advertisement.category)
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

}