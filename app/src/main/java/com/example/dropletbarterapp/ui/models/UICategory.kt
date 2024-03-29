package com.example.dropletbarterapp.ui.models

import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.models.Category

class UICategory(
    val category: Category,
    val name: String,
    val color: String,
    val photo: Int
) {
    companion object {
        fun findUICategoryForCategoryName(category: Category): UICategory {
            when (category) {
                Category.CLOTHES -> return UICategory(
                    Category.CLOTHES,
                    "Одежда",
                    "#FFFFFF",
                    R.drawable.empty_profile_image
                )
                Category.ELECTRONICS
                -> return UICategory(
                    Category.ELECTRONICS,
                    "Электроника",
                    "#FFFFFF",
                    R.drawable.empty_profile_image
                )
                Category.HEALTHCARE
                -> return UICategory(
                    Category.HEALTHCARE,
                    "Здоровье",
                    "#FFFFFF",
                    R.drawable.empty_profile_image
                )
                Category.FOR_HOME
                -> return UICategory(
                    Category.FOR_HOME,
                    "Для дома",
                    "#FFFFFF",
                    R.drawable.empty_profile_image
                )
                Category.FOR_PETS
                -> return UICategory(
                    Category.FOR_PETS,
                    "Для животных",
                    "#FFFFFF",
                    R.drawable.empty_profile_image
                )
                Category.ACCESSORIES
                -> return UICategory(
                    Category.ACCESSORIES,
                    "Аксессуары",
                    "#FFFFFF",
                    R.drawable.empty_profile_image
                )
                Category.SERVICES
                -> return UICategory(
                    Category.SERVICES,
                    "Услуги",
                    "#FFFFFF",
                    R.drawable.empty_profile_image
                )
                Category.SHARED_USAGE
                -> return UICategory(
                    Category.SHARED_USAGE,
                    "Совместное использование",
                    "#FFFFFF",
                    R.drawable.empty_profile_image
                )
                Category.OTHER
                -> return UICategory(
                    Category.OTHER,
                    "Другое",
                    "#FFFFFF",
                    R.drawable.empty_profile_image
                )
            }
        }

        fun findCategoryByName(name: String): Category {
            return when (name) {
                "Одежда" -> Category.CLOTHES
                "Электроника" -> Category.ELECTRONICS
                "Здоровье" -> Category.HEALTHCARE
                "Для дома" -> Category.FOR_HOME
                "Для животных" -> Category.FOR_PETS
                "Аксессуары" -> Category.ACCESSORIES
                "Услуги" -> Category.SERVICES
                "Совместное использование" -> Category.SHARED_USAGE
                "Другое" -> Category.OTHER
                else -> Category.CLOTHES
            }
        }

        fun getPosByCategory(category: Category): Int {
            return when (category) {
                Category.SHARED_USAGE -> 1
                Category.CLOTHES -> 2
                Category.ELECTRONICS -> 3
                Category.HEALTHCARE -> 4
                Category.FOR_HOME -> 5
                Category.FOR_PETS -> 6
                Category.ACCESSORIES -> 7
                Category.SERVICES -> 8
                Category.OTHER -> 9
            }
        }

        fun getCategoryByPos(pos: Int): Category {
            when (pos) {
                1 -> Category.SHARED_USAGE
                2 -> Category.CLOTHES
                3 -> Category.ELECTRONICS
                4 -> Category.HEALTHCARE
                5 -> Category.FOR_HOME
                6 -> Category.FOR_PETS
                7 -> Category.ACCESSORIES
                8 -> Category.SERVICES
                9 -> Category.OTHER
            }
            throw java.lang.IllegalArgumentException("No category for such position!")
        }
    }
}