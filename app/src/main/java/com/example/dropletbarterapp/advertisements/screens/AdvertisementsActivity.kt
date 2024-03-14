package com.example.dropletbarterapp.advertisements.screens

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.ActivityAdvertisementsBinding
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.utils.Navigation

class AdvertisementsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdvertisementsBinding
    private lateinit var adapter: AdvertisementsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdvertisementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set navigation
        Navigation.setNavigation(this, R.id.advertisements)

        adapter = AdvertisementsAdapter()
        adapter.advertisements = emptyList()

        checkVisibility()
        binding.recyclerView.adapter = adapter

        // change lists
        setButtonsChange()

    }

    private fun setButtonsChange() {
        binding.buttonMyAdvertisements.setOnClickListener {
            adapter.advertisements = listOf(
                Advertisement(
                    null, "Моя книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Моя книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Моя книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Книга", "Новая книжка", true, Category.OTHER, null

                )
            )
            checkVisibility()
            binding.buttonEmptyAction.text = "Добавьте объявление"
        }

        binding.buttonFavourites.setOnClickListener {
            adapter.advertisements = listOf(
                Advertisement(
                    null, "Любимая Книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Книга", "Новая книжка", true, Category.OTHER, null

                )
            )
            checkVisibility()
            binding.buttonEmptyAction.text = "Поиск"
        }

        binding.buttonSharedUsage.setOnClickListener {
            adapter.advertisements = listOf(
                Advertisement(
                    null, "Общая книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Книга", "Новая книжка", true, Category.OTHER, null

                ), Advertisement(
                    null, "Книга", "Новая книжка", true, Category.OTHER, null

                )
            )
            checkVisibility()
            binding.buttonEmptyAction.text = "Поиск"
        }
    }

    private fun checkVisibility() {
        if (adapter.advertisements.isEmpty()) {
            binding.buttonEmptyAction.visibility = View.VISIBLE
        } else {
            binding.buttonEmptyAction.visibility = View.INVISIBLE
        }
    }

}