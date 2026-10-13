package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for local food items in Room.
 * Enables reactive Flow updates and suspend functions for background operations.
 */
@Dao
interface FoodDao {

    @Query("SELECT * FROM food_items ORDER BY nameEn ASC")
    fun getAllFoods(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM food_items WHERE id = :id LIMIT 1")
    suspend fun getFoodById(id: String): FoodEntity?

    @Query("SELECT * FROM food_items WHERE category = :category ORDER BY nameEn ASC")
    fun getFoodsByCategory(category: String): Flow<List<FoodEntity>>

    @Query("""
        SELECT * FROM food_items 
        WHERE LOWER(nameEn) LIKE '%' || LOWER(:query) || '%' 
           OR nameBn LIKE '%' || :query || '%' 
           OR LOWER(descriptionEn) LIKE '%' || LOWER(:query) || '%'
           OR descriptionBn LIKE '%' || :query || '%'
        ORDER BY nameEn ASC
    """)
    fun searchFoods(query: String): Flow<List<FoodEntity>>

    @Query("SELECT COUNT(*) FROM food_items")
    suspend fun getFoodCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<FoodEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: FoodEntity)

    @Update
    suspend fun updateFood(food: FoodEntity)

    @Query("DELETE FROM food_items WHERE id = :id")
    suspend fun deleteFoodById(id: String)

    @Query("DELETE FROM food_items")
    suspend fun clearAll()
}
