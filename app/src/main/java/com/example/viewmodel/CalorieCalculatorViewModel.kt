package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.FoodDatabase
import com.example.data.local.CalorieSnapDatabase
import com.example.data.repository.FoodRepository
import com.example.model.FoodCategory
import com.example.model.FoodItem
import com.example.model.LoggedMealItem
import com.example.model.MealType
import com.example.model.ServingOption
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

data class SelectedPortion(
    val selectedOptionIndex: Int = 0,
    val quantityMultiplier: Double = 1.0,
    val customGramsInput: String = ""
)

data class CalorieUiState(
    val searchQuery: String = "",
    val selectedCategory: FoodCategory = FoodCategory.ALL,
    val isBanglaLanguage: Boolean = false,
    val selectedFood: FoodItem? = null,
    val selectedOptionIndex: Int = 0,
    val quantityMultiplier: Double = 1.0,
    val isCustomGramMode: Boolean = false,
    val customGrams: String = "",
    val filteredFoods: List<FoodItem> = FoodDatabase.foods,
    val totalIndexedFoodsCount: Int = FoodDatabase.foods.size,
    val isRoomDbReady: Boolean = false,
    val dailyCalorieGoal: Int = 2000,
    val dailyLoggedItems: List<LoggedMealItem> = emptyList()
) {
    val currentCalculatedCalories: Int
        get() {
            val food = selectedFood ?: return 0
            return if (isCustomGramMode) {
                val grams = customGrams.toIntOrNull() ?: 0
                ((grams * food.defaultCaloriesPer100g) / 100.0).roundToInt()
            } else {
                val option = food.servingOptions.getOrNull(selectedOptionIndex)
                    ?: food.servingOptions.firstOrNull()
                if (option != null) {
                    (option.calories * quantityMultiplier).roundToInt()
                } else {
                    food.defaultCaloriesPer100g
                }
            }
        }

    val currentCalculatedGrams: Int
        get() {
            val food = selectedFood ?: return 0
            return if (isCustomGramMode) {
                customGrams.toIntOrNull() ?: 0
            } else {
                val option = food.servingOptions.getOrNull(selectedOptionIndex)
                    ?: food.servingOptions.firstOrNull()
                if (option != null) {
                    (option.gramsOrMl * quantityMultiplier).roundToInt()
                } else {
                    100
                }
            }
        }

    val currentCarbs: Double
        get() {
            val food = selectedFood ?: return 0.0
            return ((food.carbsGramsPer100g * currentCalculatedGrams) / 100.0)
        }

    val currentProtein: Double
        get() {
            val food = selectedFood ?: return 0.0
            return ((food.proteinGramsPer100g * currentCalculatedGrams) / 100.0)
        }

    val currentFat: Double
        get() {
            val food = selectedFood ?: return 0.0
            return ((food.fatGramsPer100g * currentCalculatedGrams) / 100.0)
        }

    // Daily Progress Computations
    val totalConsumedCalories: Int
        get() = dailyLoggedItems.sumOf { it.calories }

    val remainingCalories: Int
        get() = dailyCalorieGoal - totalConsumedCalories

    val calorieProgressFraction: Float
        get() = if (dailyCalorieGoal > 0) (totalConsumedCalories.toFloat() / dailyCalorieGoal.toFloat()) else 0f

    val isGoalExceeded: Boolean
        get() = totalConsumedCalories > dailyCalorieGoal

    val totalCarbsConsumed: Double
        get() = dailyLoggedItems.sumOf { it.carbsGrams }

    val totalProteinConsumed: Double
        get() = dailyLoggedItems.sumOf { it.proteinGrams }

    val totalFatConsumed: Double
        get() = dailyLoggedItems.sumOf { it.fatGrams }

    val breakfastCalories: Int
        get() = dailyLoggedItems.filter { it.mealType == MealType.BREAKFAST }.sumOf { it.calories }

    val lunchCalories: Int
        get() = dailyLoggedItems.filter { it.mealType == MealType.LUNCH }.sumOf { it.calories }

    val dinnerCalories: Int
        get() = dailyLoggedItems.filter { it.mealType == MealType.DINNER }.sumOf { it.calories }

    val snackCalories: Int
        get() = dailyLoggedItems.filter { it.mealType == MealType.SNACK }.sumOf { it.calories }
}

@OptIn(ExperimentalCoroutinesApi::class)
class CalorieCalculatorViewModel(
    application: Application,
    private val repository: FoodRepository? = null
) : AndroidViewModel(application) {

    // Explicit constructor required by ViewModelProvider's AndroidViewModelFactory reflection
    constructor(application: Application) : this(application, null)

    private val effectiveRepository: FoodRepository = repository ?: run {
        val database = CalorieSnapDatabase.getDatabase(application)
        FoodRepository(database.foodDao())
    }

    private var allRoomFoods: List<FoodItem> = FoodDatabase.foods
    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow(FoodCategory.ALL)

    // Pre-seeded starter consumption items for initial visual representation
    private val initialSampleMeals = listOf(
        LoggedMealItem(
            foodNameEn = "Plain Roti & Dim Poach",
            foodNameBn = "লাল আটার রুটি ও ডিম পোচ",
            calories = 275,
            grams = 120,
            carbsGrams = 32.0,
            proteinGrams = 14.5,
            fatGrams = 8.5,
            mealType = MealType.BREAKFAST
        ),
        LoggedMealItem(
            foodNameEn = "White Rice & Masoor Dal",
            foodNameBn = "সাদা ভাত ও মসুর ডাল",
            calories = 490,
            grams = 300,
            carbsGrams = 92.0,
            proteinGrams = 18.0,
            fatGrams = 4.2,
            mealType = MealType.LUNCH
        )
    )

    private val _uiState = MutableStateFlow(
        CalorieUiState(
            selectedFood = FoodDatabase.foods.firstOrNull(),
            selectedOptionIndex = 0,
            quantityMultiplier = 1.0,
            filteredFoods = FoodDatabase.foods,
            totalIndexedFoodsCount = FoodDatabase.foods.size,
            isRoomDbReady = false,
            dailyCalorieGoal = 2000,
            dailyLoggedItems = initialSampleMeals
        )
    )
    val uiState: StateFlow<CalorieUiState> = _uiState.asStateFlow()

    init {
        // Ensure default South Asian dishes are seeded into Room database
        viewModelScope.launch {
            effectiveRepository.ensureDatabaseSeeded()
            _uiState.update { it.copy(isRoomDbReady = true) }
        }

        // Query Room database reactively based on search query and category filters
        viewModelScope.launch {
            combine(_searchQuery, _selectedCategory) { query, category ->
                Pair(query, category)
            }.flatMapLatest { (query, category) ->
                effectiveRepository.searchAndFilterFoods(query, category)
            }.collect { foodsFromDb ->
                if (foodsFromDb.isNotEmpty() || _searchQuery.value.isNotEmpty() || _selectedCategory.value != FoodCategory.ALL) {
                    if (_searchQuery.value.isEmpty() && _selectedCategory.value == FoodCategory.ALL) {
                        allRoomFoods = foodsFromDb
                    }
                    _uiState.update { current ->
                        val currentSelected = current.selectedFood
                        val updatedSelected = if (currentSelected == null) {
                            foodsFromDb.firstOrNull()
                        } else {
                            foodsFromDb.find { it.id == currentSelected.id } ?: currentSelected
                        }
                        current.copy(
                            selectedFood = updatedSelected,
                            filteredFoods = foodsFromDb,
                            totalIndexedFoodsCount = if (allRoomFoods.isNotEmpty()) allRoomFoods.size else foodsFromDb.size
                        )
                    }
                }
            }
        }

        // Set initial portion according to default serving
        val initialFood = FoodDatabase.foods.firstOrNull()
        if (initialFood != null) {
            selectFood(initialFood)
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        _uiState.update { current ->
            val updated = current.copy(searchQuery = query)
            updated.copy(filteredFoods = filterFoods(query, updated.selectedCategory, allRoomFoods))
        }
    }

    fun onCategorySelect(category: FoodCategory) {
        _selectedCategory.value = category
        _uiState.update { current ->
            val updated = current.copy(selectedCategory = category)
            updated.copy(filteredFoods = filterFoods(current.searchQuery, category, allRoomFoods))
        }
    }

    fun toggleLanguage() {
        _uiState.update { it.copy(isBanglaLanguage = !it.isBanglaLanguage) }
    }

    fun selectFood(food: FoodItem) {
        val defaultIdx = food.servingOptions.indexOfFirst { it.isDefault }.let {
            if (it >= 0) it else 0
        }
        val defaultOption = food.servingOptions.getOrNull(defaultIdx)
        _uiState.update {
            it.copy(
                selectedFood = food,
                selectedOptionIndex = defaultIdx,
                quantityMultiplier = 1.0,
                isCustomGramMode = false,
                customGrams = defaultOption?.gramsOrMl?.toString() ?: "100"
            )
        }
    }

    fun selectServingOption(index: Int) {
        _uiState.update { current ->
            val food = current.selectedFood
            val option = food?.servingOptions?.getOrNull(index)
            current.copy(
                selectedOptionIndex = index,
                isCustomGramMode = false,
                customGrams = option?.gramsOrMl?.toString() ?: current.customGrams
            )
        }
    }

    fun setQuantityMultiplier(multiplier: Double) {
        if (multiplier <= 0.0) return
        _uiState.update { it.copy(quantityMultiplier = multiplier, isCustomGramMode = false) }
    }

    fun setCustomGramMode(enabled: Boolean) {
        _uiState.update { current ->
            val initialGram = if (current.customGrams.isBlank()) {
                val option = current.selectedFood?.servingOptions?.getOrNull(current.selectedOptionIndex)
                option?.gramsOrMl?.toString() ?: "100"
            } else current.customGrams
            current.copy(isCustomGramMode = enabled, customGrams = initialGram)
        }
    }

    fun updateCustomGrams(gramsStr: String) {
        val filtered = gramsStr.filter { it.isDigit() }.take(4)
        _uiState.update { it.copy(customGrams = filtered, isCustomGramMode = true) }
    }

    // Daily Goal & Meal Logging Functions
    fun setDailyGoal(goal: Int) {
        val safeGoal = goal.coerceIn(500, 10000)
        _uiState.update { it.copy(dailyCalorieGoal = safeGoal) }
    }

    fun logCurrentFood(mealType: MealType = MealType.LUNCH) {
        val state = _uiState.value
        val food = state.selectedFood ?: return
        val currentCal = state.currentCalculatedCalories
        if (currentCal <= 0) return

        val newItem = LoggedMealItem(
            foodNameEn = food.nameEn,
            foodNameBn = food.nameBn,
            calories = currentCal,
            grams = state.currentCalculatedGrams,
            carbsGrams = state.currentCarbs,
            proteinGrams = state.currentProtein,
            fatGrams = state.currentFat,
            mealType = mealType
        )
        _uiState.update { current ->
            current.copy(dailyLoggedItems = listOf(newItem) + current.dailyLoggedItems)
        }
    }

    fun removeLoggedItem(id: String) {
        _uiState.update { current ->
            current.copy(dailyLoggedItems = current.dailyLoggedItems.filterNot { it.id == id })
        }
    }

    fun clearDailyLog() {
        _uiState.update { it.copy(dailyLoggedItems = emptyList()) }
    }

    private fun filterFoods(query: String, category: FoodCategory, sourceList: List<FoodItem>): List<FoodItem> {
        val trimmed = query.trim().lowercase()
        return sourceList.filter { food ->
            val matchesCategory = (category == FoodCategory.ALL || food.category == category)
            val matchesQuery = trimmed.isEmpty() ||
                    food.nameEn.lowercase().contains(trimmed) ||
                    food.nameBn.contains(trimmed) ||
                    food.descriptionEn.lowercase().contains(trimmed) ||
                    food.descriptionBn.contains(trimmed)
            matchesCategory && matchesQuery
        }
    }

    fun generateShareSummary(): String {
        val state = _uiState.value
        val food = state.selectedFood ?: return "CalorieSnap: Estimate calories in everyday foods!"
        val isBn = state.isBanglaLanguage

        val foodName = if (isBn) "${food.nameBn} (${food.nameEn})" else "${food.nameEn} / ${food.nameBn}"
        val portionLabel = if (state.isCustomGramMode) {
            "${state.currentCalculatedGrams}g custom portion"
        } else {
            val option = food.servingOptions.getOrNull(state.selectedOptionIndex)
            val baseLabel = if (isBn) option?.labelBn ?: "" else option?.labelEn ?: ""
            if (state.quantityMultiplier == 1.0) baseLabel else "${state.quantityMultiplier}x ($baseLabel)"
        }

        val cal = state.currentCalculatedCalories
        val carbs = String.format("%.1f", state.currentCarbs)
        val protein = String.format("%.1f", state.currentProtein)
        val fat = String.format("%.1f", state.currentFat)

        val dailyConsumed = state.totalConsumedCalories
        val dailyGoal = state.dailyCalorieGoal
        val progressPercent = (state.calorieProgressFraction * 100).roundToInt()

        return buildString {
            appendLine("🥗 CalorieSnap - Food Calorie Estimate")
            appendLine("━━━━━━━━━━━━━━━━━━━━━")
            appendLine("🍽️ Food: $foodName")
            appendLine("⚖️ Portion: $portionLabel (~${state.currentCalculatedGrams}g)")
            appendLine("🔥 Estimated Calories: ~$cal kcal")
            appendLine("📊 Macros: ${carbs}g Carbs | ${protein}g Protein | ${fat}g Fat")
            appendLine("🎯 Daily Goal Progress: $dailyConsumed / $dailyGoal kcal ($progressPercent%)")
            appendLine("━━━━━━━━━━━━━━━━━━━━━")
            appendLine("⚠️ Note: Calorie values are approximate estimates. Recipe, cooking oil, and portion sizes vary.")
            appendLine("✨ Estimated instantly with CalorieSnap (ViveScript Solutions LLC)")
        }
    }
}
