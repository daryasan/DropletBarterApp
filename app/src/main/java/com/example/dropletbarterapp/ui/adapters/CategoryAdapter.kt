package com.example.dropletbarterapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dropletbarterapp.databinding.CategoryItemBinding
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.ui.models.UICategory

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var onCategoryClickListener: OnCategoryClickListener? = null

    var categories: List<UICategory> = listOf(
        UICategory.findUICategoryForCategoryName(Category.SHARED_USAGE),
        UICategory.findUICategoryForCategoryName(Category.CLOTHES),
        UICategory.findUICategoryForCategoryName(Category.ELECTRONICS),
        UICategory.findUICategoryForCategoryName(Category.HEALTHCARE),
        UICategory.findUICategoryForCategoryName(Category.FOR_HOME),
        UICategory.findUICategoryForCategoryName(Category.FOR_PETS),
        UICategory.findUICategoryForCategoryName(Category.ACCESSORIES),
        UICategory.findUICategoryForCategoryName(Category.SERVICES),
        UICategory.findUICategoryForCategoryName(Category.OTHER),
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

        with(holder.binding) {
            textViewCategoryName.text = category.name
            imageViewCategory.setImageResource(category.photo)
        }

        holder.itemView.setOnClickListener {
            onCategoryClickListener?.onCategoryClick(categories[position].category)
        }

    }

    fun setOnCategoryClickListener(listener: OnCategoryClickListener) {
        onCategoryClickListener = listener
    }

    interface OnCategoryClickListener {
        fun onCategoryClick(category: Category)
    }

}