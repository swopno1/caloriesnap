package com.example.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings_preferences")

class UserPreferencesRepository(private val context: Context) {

    companion object {
        val KEY_DAILY_CALORIE_GOAL = intPreferencesKey("daily_calorie_goal")
        val KEY_DIETARY_PRESET = stringPreferencesKey("dietary_preset")
        val KEY_ACTIVITY_LEVEL = stringPreferencesKey("activity_level")
        val KEY_BANGLA_LANGUAGE = booleanPreferencesKey("is_bangla_language")
        const val DEFAULT_DAILY_CALORIE_GOAL = 2000
        const val MIN_CALORIE_GOAL = 500
        const val MAX_CALORIE_GOAL = 10000
    }

    val dailyCalorieGoalFlow: Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[KEY_DAILY_CALORIE_GOAL] ?: DEFAULT_DAILY_CALORIE_GOAL
        }

    val dietaryPresetFlow: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[KEY_DIETARY_PRESET] ?: "MAINTAIN"
        }

    val activityLevelFlow: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[KEY_ACTIVITY_LEVEL] ?: "MODERATE"
        }

    val isBanglaLanguageFlow: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[KEY_BANGLA_LANGUAGE] ?: false
        }

    suspend fun updateDailyCalorieGoal(goal: Int) {
        val safeGoal = goal.coerceIn(MIN_CALORIE_GOAL, MAX_CALORIE_GOAL)
        context.dataStore.edit { preferences ->
            preferences[KEY_DAILY_CALORIE_GOAL] = safeGoal
        }
    }

    suspend fun updateDietaryPreset(preset: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_DIETARY_PRESET] = preset
        }
    }

    suspend fun updateActivityLevel(level: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_ACTIVITY_LEVEL] = level
        }
    }

    suspend fun updateBanglaLanguage(isBangla: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_BANGLA_LANGUAGE] = isBangla
        }
    }

    suspend fun resetToDefaultGoal() {
        context.dataStore.edit { preferences ->
            preferences[KEY_DAILY_CALORIE_GOAL] = DEFAULT_DAILY_CALORIE_GOAL
            preferences[KEY_DIETARY_PRESET] = "MAINTAIN"
        }
    }
}
