package com.example.model

enum class FoodCategory(val displayNameEn: String, val displayNameBn: String, val iconEmoji: String) {
    ALL("All", "সব", "🍽️"),
    BANGLA_STAPLE("Staples & Rice", "ভাত ও রুটি", "🍚"),
    CURRY_FISH_MEAT("Curry, Fish & Meat", "তরকারি, মাছ ও মাংস", "🍗"),
    DAL_LENTILS("Dal & Lentils", "ডাল", "🥣"),
    VEGETABLES("Vegetables & Bhaji", "শাকসবজি ও ভাজি", "🥦"),
    SNACKS_STREET("Snacks & Street Food", "নাস্তা ও স্ট্রিট ফুড", "🥟"),
    SWEETS_DESSERT("Sweets & Desserts", "মিষ্টি ও ডেজার্ট", "🍮"),
    FRUITS("Fresh Fruits", "তাজা ফলমূল", "🍎"),
    DRINKS("Drinks & Beverages", "পানীয়", "🍵"),
    GLOBAL_FAST_FOOD("Global & Fast Food", "গ্লোবাল ও ফাস্টফুড", "🍕")
}

data class ServingOption(
    val labelEn: String,
    val labelBn: String,
    val gramsOrMl: Int,
    val calories: Int,
    val isDefault: Boolean = false
)

data class FoodItem(
    val id: String,
    val nameEn: String,
    val nameBn: String,
    val category: FoodCategory,
    val defaultCaloriesPer100g: Int,
    val servingOptions: List<ServingOption>,
    val emoji: String,
    val descriptionEn: String,
    val descriptionBn: String,
    val carbsGramsPer100g: Double = 0.0,
    val proteinGramsPer100g: Double = 0.0,
    val fatGramsPer100g: Double = 0.0
)
