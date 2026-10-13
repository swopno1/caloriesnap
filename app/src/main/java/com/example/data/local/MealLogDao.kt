package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for querying, inserting, and deleting
 * logged meal items in Room Database.
 * Powers daily calorie consumption tracking and historical calorie analytics.
 */
@Dao
interface MealLogDao {

    @Query("SELECT * FROM meal_logs ORDER BY timestamp DESC")
    fun getAllMealLogs(): Flow<List<MealLogEntity>>

    @Query("SELECT * FROM meal_logs WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp DESC")
    fun getMealLogsBetween(startTime: Long, endTime: Long): Flow<List<MealLogEntity>>

    @Query("SELECT * FROM meal_logs WHERE timestamp >= :startOfDay AND timestamp <= :endOfDay ORDER BY timestamp DESC")
    fun getMealLogsForDay(startOfDay: Long, endOfDay: Long): Flow<List<MealLogEntity>>

    @Query("SELECT * FROM meal_logs ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentMealLogs(limit: Int): Flow<List<MealLogEntity>>

    @Query("SELECT SUM(calories) FROM meal_logs WHERE timestamp >= :startTime AND timestamp <= :endTime")
    fun getTotalCaloriesBetween(startTime: Long, endTime: Long): Flow<Int?>

    @Query("SELECT COUNT(*) FROM meal_logs")
    fun getTotalLoggedCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM meal_logs")
    suspend fun getCountDirect(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealLog(log: MealLogEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMealLogs(logs: List<MealLogEntity>)

    @Update
    suspend fun updateMealLog(log: MealLogEntity)

    @Query("DELETE FROM meal_logs WHERE id = :id")
    suspend fun deleteMealLogById(id: String)

    @Query("DELETE FROM meal_logs WHERE timestamp >= :startTime AND timestamp <= :endTime")
    suspend fun clearMealLogsBetween(startTime: Long, endTime: Long)

    @Query("DELETE FROM meal_logs")
    suspend fun clearAllMealLogs()
}
