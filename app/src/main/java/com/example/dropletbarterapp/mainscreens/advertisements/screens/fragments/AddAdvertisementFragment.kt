package com.example.dropletbarterapp.mainscreens.advertisements.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.FragmentAddAdvertisementBinding
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.images.ImageLoader
import com.example.dropletbarterapp.ui.images.SquareCrop
import com.example.dropletbarterapp.ui.models.UICategory
import com.example.dropletbarterapp.validators.Toaster
import com.example.dropletbarterapp.validators.UIMessageMan
import kotlinx.coroutines.runBlocking
import java.util.*


class AddAdvertisementFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = AddAdvertisementFragment()
    }

    private lateinit var viewModel: AddAdvertisementViewModel
    private lateinit var binding: FragmentAddAdvertisementBinding
    private val toaster = Toaster()
    private var category: Category? = null
    private var photo: ByteArray? = null
    private var name: String? = null
    private var description: String? = null
    private lateinit var imageLoader: ImageLoader
    private val uiMessageMan = UIMessageMan()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAdvertisementBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AddAdvertisementViewModel::class.java]
        imageLoader = ImageLoader(requireContext(), this, binding.imageViewEditPhoto, SquareCrop())

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
            imageLoader.addPhoto()
            binding.buttonChangePhoto.text = "Изменить фото"
        }

        // submitting ads
        binding.buttonPublish.setOnClickListener {
            name = binding.editTextAdsName.text.toString()
            description = binding.editTextAdsDescription.text.toString()
            photo = imageLoader.photo

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
                    viewModel.addAdvertisement(photo!!, name!!, description!!, category!!)
                }
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
        //requireActivity().supportFragmentManager.popBackStack()
        requireActivity().onBackPressed()
    }

}