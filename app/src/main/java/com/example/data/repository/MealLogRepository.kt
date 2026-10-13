package com.example.data.repository

import com.example.data.local.MealLogDao
import com.example.data.local.MealLogEntity
import com.example.model.LoggedMealItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar

/**
 * Repository mediating access to Room meal log storage, providing reactive
 * data streams for the ViewModel and UI.
 */
class MealLogRepository(
    private val mealLogDao: MealLogDao
) {

    val allMealLogs: Flow<List<LoggedMealItem>> = mealLogDao.getAllMealLogs().map { entities ->
        entities.map { it.toDomainModel() }
    }

    fun getLogsForDay(timestamp: Long): Flow<List<LoggedMealItem>> {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = calendar.timeInMillis
        val endOfDay = startOfDay + (24 * 60 * 60 * 1000) - 1

        return mealLogDao.getMealLogsForDay(startOfDay, endOfDay).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    fun getLogsBetween(startTime: Long, endTime: Long): Flow<List<LoggedMealItem>> {
        return mealLogDao.getMealLogsBetween(startTime, endTime).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    fun getRecentMealLogs(limit: Int = 20): Flow<List<LoggedMealItem>> {
        return mealLogDao.getRecentMealLogs(limit).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    suspend fun logMeal(item: LoggedMealItem) {
        mealLogDao.insertMealLog(MealLogEntity.fromDomainModel(item))
    }

    suspend fun logAllMeals(items: List<LoggedMealItem>) {
        mealLogDao.insertAllMealLogs(items.map { MealLogEntity.fromDomainModel(it) })
    }

    suspend fun removeMeal(id: String) {
        mealLogDao.deleteMealLogById(id)
    }

    suspend fun clearLogsForDay(timestamp: Long) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = calendar.timeInMillis
        val endOfDay = startOfDay + (24 * 60 * 60 * 1000) - 1

        mealLogDao.clearMealLogsBetween(startOfDay, endOfDay)
    }

    suspend fun clearAllLogs() {
        mealLogDao.clearAllMealLogs()
    }

    suspend fun getCount(): Int {
        return mealLogDao.getCountDirect()
    }
}
