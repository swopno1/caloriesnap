package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.FoodDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Main Room Database for CalorieSnap offline food catalog and meal tracking logs.
 * Prepopulates default common Bangladeshi, South Asian, and global food items
 * upon first creation so offline estimation works instantly out-of-the-box.
 * Stores user meal logs to track daily calorie history over time.
 */
@Database(
    entities = [
        FoodEntity::class,
        MealLogEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class CalorieSnapDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao
    abstract fun mealLogDao(): MealLogDao

    companion object {
        @Volatile
        private var INSTANCE: CalorieSnapDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope = CoroutineScope(Dispatchers.IO)): CalorieSnapDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CalorieSnapDatabase::class.java,
                    "caloriesnap_food_database.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(CalorieDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class CalorieDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    prepopulateDatabase(database.foodDao())
                }
            }
        }

        suspend fun prepopulateDatabase(dao: FoodDao) {
            val entities = FoodDatabase.foods.map { FoodEntity.fromDomainModel(it) }
            dao.insertAll(entities)
        }
    }
}
