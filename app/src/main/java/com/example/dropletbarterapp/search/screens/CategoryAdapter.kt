package com.example.dropletbarterapp.search.screens

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.CategoryItemBinding
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.models.uimodels.UICategory

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categories: List<UICategory> = listOf(
        UICategory(Category.CLOTHES, "Одежда", "#FF00000", R.drawable.empty_profile_image),
        UICategory(Category.ACCESSORIES, "Аксессуары", "#FF00000", R.drawable.empty_profile_image),
        UICategory(Category.ELECTRONICS, "Электроника", "#FF00000", R.drawable.empty_profile_image),
        UICategory(Category.FOR_HOME, "Для дома", "#FF00000", R.drawable.empty_profile_image),
        UICategory(Category.FOR_PETS, "Для животных", "#FF00000", R.drawable.empty_profile_image),
        UICategory(Category.HEALTHCARE, "Здоровье", "#FF00000", R.drawable.empty_profile_image),
        UICategory(Category.SERVICES, "Услуги", "#FF00000", R.drawable.empty_profile_image),
        UICategory(
            Category.SHARED_USAGE,
            "Совместное использование",
            "#FF00000",
            R.drawable.empty_profile_image
        ),
        UICategory(Category.OTHER, "Другое", "#FF00000", R.drawable.empty_profile_image),
    )

    class CategoryViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        //val context = holder.itemView.context

        with(holder.binding) {
            textViewCategoryName.text = category.name
        }

    }
}