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
 * Main Room Database for CalorieSnap offline food catalog.
 * Prepopulates default common Bangladeshi, South Asian, and global food items
 * upon first creation so offline estimation works instantly out-of-the-box.
 */
@Database(
    entities = [FoodEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CalorieSnapDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao

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
