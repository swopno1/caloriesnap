package com.example.model

import java.util.UUID

enum class MealType(
    val titleEn: String,
    val titleBn: String,
    val shortTitleEn: String,
    val shortTitleBn: String,
    val iconEmoji: String,
    val colorHex: Long
) {
    BREAKFAST("Breakfast", "সকালের নাস্তা", "B'fast", "সকাল", "🌅", 0xFFFB8C00),
    LUNCH("Lunch", "দুপুরের খাবার", "Lunch", "দুপুর", "☀️", 0xFF1E88E5),
    DINNER("Dinner", "রাতের খাবার", "Dinner", "রাত", "🌙", 0xFF6D28D9),
    SNACK("Snacks", "নাস্তা", "Snack", "নাস্তা", "☕", 0xFF0D9488)
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
