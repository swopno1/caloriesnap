package com.example.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.model.LoggedMealItem
import com.example.model.MealType
import java.util.UUID

/**
 * Room Database Entity representing an eaten meal entry logged by the user.
 * Stores calorie, macronutrient, portion, meal type, and timestamp values
 * to enable tracking daily intake and historical calorie trends over time.
 */
@Entity(
    tableName = "meal_logs",
    indices = [
        Index(value = ["timestamp"]),
        Index(value = ["mealType"])
    ]
)
data class MealLogEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val foodNameEn: String,
    val foodNameBn: String,
    val calories: Int,
    val grams: Int,
    val carbsGrams: Double = 0.0,
    val proteinGrams: Double = 0.0,
    val fatGrams: Double = 0.0,
    val mealType: String = MealType.LUNCH.name,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun toDomainModel(): LoggedMealItem {
        val parsedType = try {
            MealType.valueOf(mealType)
        } catch (_: Exception) {
            MealType.LUNCH
        }
        return LoggedMealItem(
            id = id,
            foodNameEn = foodNameEn,
            foodNameBn = foodNameBn,
            calories = calories,
            grams = grams,
            carbsGrams = carbsGrams,
            proteinGrams = proteinGrams,
            fatGrams = fatGrams,
            mealType = parsedType,
            timestamp = timestamp
        )
    }

    companion object {
        fun fromDomainModel(item: LoggedMealItem): MealLogEntity {
            return MealLogEntity(
                id = item.id,
                foodNameEn = item.foodNameEn,
                foodNameBn = item.foodNameBn,
                calories = item.calories,
                grams = item.grams,
                carbsGrams = item.carbsGrams,
                proteinGrams = item.proteinGrams,
                fatGrams = item.fatGrams,
                mealType = item.mealType.name,
                timestamp = item.timestamp
            )
        }
    }
}
