package com.example.model

import java.util.UUID

enum class MealType(
    val titleEn: String,
    val titleBn: String,
    val iconEmoji: String,
    val colorHex: Long
) {
    BREAKFAST("Breakfast", "সকালের নাস্তা", "🌅", 0xFFFB8C00),
    LUNCH("Lunch", "দুপুরের খাবার", "☀️", 0xFF1E88E5),
    DINNER("Dinner", "রাতের খাবার", "🌙", 0xFF6D28D9),
    SNACK("Snack", "নাস্তা", "🍎", 0xFF0D9488)
}

data class LoggedMealItem(
    val id: String = UUID.randomUUID().toString(),
    val foodNameEn: String,
    val foodNameBn: String,
    val calories: Int,
    val grams: Int,
    val carbsGrams: Double = 0.0,
    val proteinGrams: Double = 0.0,
    val fatGrams: Double = 0.0,
    val mealType: MealType = MealType.LUNCH,
    val timestamp: Long = System.currentTimeMillis()
)
