package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.FoodCategory
import com.example.model.FoodItem
import com.example.model.ServingOption

/**
 * Room Database Entity representing a food item stored locally on device.
 * Enables fast offline food lookups, calorie estimation, and macro calculation
 * for Bangladeshi and South Asian dishes.
 */
@Entity(tableName = "food_items")
data class FoodEntity(
    @PrimaryKey
    val id: String,
    val nameEn: String,
    val nameBn: String,
    val category: String, // Stored as FoodCategory.name
    val defaultCaloriesPer100g: Int,
    val emoji: String,
    val descriptionEn: String,
    val descriptionBn: String,
    val carbsGramsPer100g: Double = 0.0,
    val proteinGramsPer100g: Double = 0.0,
    val fatGramsPer100g: Double = 0.0,
    val servingOptionsJson: String = "" // Serialized ServingOption list
) {
    fun toDomainModel(): FoodItem {
        val cat = try {
            FoodCategory.valueOf(category)
        } catch (_: Exception) {
            FoodCategory.ALL
        }
        val servings = ServingConverters.deserializeServingOptions(servingOptionsJson)
        return FoodItem(
            id = id,
            nameEn = nameEn,
            nameBn = nameBn,
            category = cat,
            defaultCaloriesPer100g = defaultCaloriesPer100g,
            emoji = emoji,
            descriptionEn = descriptionEn,
            descriptionBn = descriptionBn,
            carbsGramsPer100g = carbsGramsPer100g,
            proteinGramsPer100g = proteinGramsPer100g,
            fatGramsPer100g = fatGramsPer100g,
            servingOptions = servings
        )
    }

    companion object {
        fun fromDomainModel(item: FoodItem): FoodEntity {
            return FoodEntity(
                id = item.id,
                nameEn = item.nameEn,
                nameBn = item.nameBn,
                category = item.category.name,
                defaultCaloriesPer100g = item.defaultCaloriesPer100g,
                emoji = item.emoji,
                descriptionEn = item.descriptionEn,
                descriptionBn = item.descriptionBn,
                carbsGramsPer100g = item.carbsGramsPer100g,
                proteinGramsPer100g = item.proteinGramsPer100g,
                fatGramsPer100g = item.fatGramsPer100g,
                servingOptionsJson = ServingConverters.serializeServingOptions(item.servingOptions)
            )
        }
    }
}
