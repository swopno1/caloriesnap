package com.example.data.repository

import com.example.data.FoodDatabase
import com.example.data.local.FoodDao
import com.example.data.local.FoodEntity
import com.example.model.FoodCategory
import com.example.model.FoodItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository mediating access to Room local storage and providing reactive
 * data streams to ViewModels and UI.
 */
class FoodRepository(
    private val foodDao: FoodDao
) {

    val allFoods: Flow<List<FoodItem>> = foodDao.getAllFoods().map { entities ->
        entities.map { it.toDomainModel() }
    }

    fun getFoodsByCategory(category: FoodCategory): Flow<List<FoodItem>> {
        return if (category == FoodCategory.ALL) {
            allFoods
        } else {
            foodDao.getFoodsByCategory(category.name).map { entities ->
                entities.map { it.toDomainModel() }
            }
        }
    }

    fun searchFoods(query: String): Flow<List<FoodItem>> {
        val trimmed = query.trim()
        return if (trimmed.isEmpty()) {
            allFoods
        } else {
            foodDao.searchFoods(trimmed).map { entities ->
                entities.map { it.toDomainModel() }
            }
        }
    }

    suspend fun getFoodById(id: String): FoodItem? {
        return foodDao.getFoodById(id)?.toDomainModel()
    }

    suspend fun ensureDatabaseSeeded() {
        if (foodDao.getFoodCount() == 0) {
            val entities = FoodDatabase.foods.map { FoodEntity.fromDomainModel(it) }
            foodDao.insertAll(entities)
        }
    }

    suspend fun insertOrUpdateFood(food: FoodItem) {
        foodDao.insertFood(FoodEntity.fromDomainModel(food))
    }

    suspend fun deleteFoodById(id: String) {
        foodDao.deleteFoodById(id)
    }
}
