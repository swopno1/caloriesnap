package com.example

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.data.FoodDatabase
import com.example.model.FoodCategory
import com.example.model.MealType
import com.example.viewmodel.CalorieCalculatorViewModel
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class CalorieCalculatorUnitTest {

    private lateinit var viewModel: CalorieCalculatorViewModel
    private lateinit var app: Application

    @Before
    fun setUp() {
        app = ApplicationProvider.getApplicationContext<Application>()
        viewModel = CalorieCalculatorViewModel(app)
    }

    @Test
    fun testViewModelConstructorReflectionForFactory() {
        // Explicitly tests that ViewModelProvider.AndroidViewModelFactory reflection works
        val constructor = CalorieCalculatorViewModel::class.java.getConstructor(Application::class.java)
        assertNotNull("Constructor matching (Application) must exist", constructor)
        val instance = constructor.newInstance(app)
        assertNotNull("Instantiated ViewModel must not be null", instance)
    }

    @Test
    fun testFoodDatabaseIsNotEmpty() {
        assertTrue("Food database must contain foods", FoodDatabase.foods.isNotEmpty())
        assertTrue("Food database must have more than 20 items", FoodDatabase.foods.size > 20)
    }

    @Test
    fun testInitialUiState() {
        val state = viewModel.uiState.value
        assertNotNull("Initial selected food should not be null", state.selectedFood)
        assertEquals(0, state.selectedOptionIndex)
        assertEquals(1.0, state.quantityMultiplier, 0.001)
        assertFalse(state.isBanglaLanguage)
        assertEquals(2000, state.dailyCalorieGoal)
        assertTrue("Initial logged items must not be empty", state.dailyLoggedItems.isNotEmpty())
    }

    @Test
    fun testToggleLanguage() {
        assertFalse(viewModel.uiState.value.isBanglaLanguage)
        viewModel.toggleLanguage()
        assertTrue(viewModel.uiState.value.isBanglaLanguage)
        viewModel.toggleLanguage()
        assertFalse(viewModel.uiState.value.isBanglaLanguage)
    }

    @Test
    fun testSearchFiltering() {
        viewModel.onSearchQueryChange("rice")
        val state = viewModel.uiState.value
        assertTrue("Search results for 'rice' should not be empty", state.filteredFoods.isNotEmpty())
        state.filteredFoods.forEach { food ->
            val matches = food.nameEn.contains("rice", ignoreCase = true) ||
                    food.nameBn.contains("rice", ignoreCase = true) ||
                    food.descriptionEn.contains("rice", ignoreCase = true)
            assertTrue("Food ${food.nameEn} should match query", matches)
        }
    }

    @Test
    fun testEmptySearchYieldsEmptyList() {
        viewModel.onSearchQueryChange("xyznonexistentdish123")
        val state = viewModel.uiState.value
        assertTrue("Non-existent dish should yield empty list", state.filteredFoods.isEmpty())
    }

    @Test
    fun testClearSearchRestoresFoods() {
        viewModel.onSearchQueryChange("biryani")
        assertTrue(viewModel.uiState.value.filteredFoods.size < FoodDatabase.foods.size)
        viewModel.onSearchQueryChange("")
        assertEquals(FoodDatabase.foods.size, viewModel.uiState.value.filteredFoods.size)
    }

    @Test
    fun testCategoryFiltering() {
        viewModel.onCategorySelect(FoodCategory.BANGLA_STAPLE)
        val state = viewModel.uiState.value
        assertTrue("Category foods should not be empty", state.filteredFoods.isNotEmpty())
        state.filteredFoods.forEach { food ->
            assertEquals(FoodCategory.BANGLA_STAPLE, food.category)
        }
    }

    @Test
    fun testCalorieCalculationServingOption() {
        val firstFood = FoodDatabase.foods.first()
        viewModel.selectFood(firstFood)

        val firstOption = firstFood.servingOptions.first()
        viewModel.selectServingOption(0)
        viewModel.setQuantityMultiplier(2.0)

        val expectedCalories = (firstOption.calories * 2.0).toInt()
        assertEquals(expectedCalories, viewModel.uiState.value.currentCalculatedCalories)
    }

    @Test
    fun testCustomGramsCalculation() {
        val firstFood = FoodDatabase.foods.first()
        viewModel.selectFood(firstFood)

        viewModel.setCustomGramMode(true)
        viewModel.updateCustomGrams("200")

        val expectedCalories = ((200 * firstFood.defaultCaloriesPer100g) / 100.0).toInt()
        assertEquals(expectedCalories, viewModel.uiState.value.currentCalculatedCalories)
        assertEquals(200, viewModel.uiState.value.currentCalculatedGrams)
    }

    @Test
    fun testDailyCalorieGoalSetting() {
        viewModel.setDailyGoal(2250)
        assertEquals(2250, viewModel.uiState.value.dailyCalorieGoal)

        // Safety coerce limits
        viewModel.setDailyGoal(200) // below 500
        assertEquals(500, viewModel.uiState.value.dailyCalorieGoal)

        viewModel.setDailyGoal(12000) // above 10000
        assertEquals(10000, viewModel.uiState.value.dailyCalorieGoal)
    }

    @Test
    fun testLogCurrentFoodToDailyProgress() {
        val initialCount = viewModel.uiState.value.dailyLoggedItems.size
        val initialCalories = viewModel.uiState.value.totalConsumedCalories

        viewModel.selectFood(FoodDatabase.foods.first())
        val currentFoodCal = viewModel.uiState.value.currentCalculatedCalories

        viewModel.logCurrentFood(MealType.DINNER)

        val updatedState = viewModel.uiState.value
        assertEquals(initialCount + 1, updatedState.dailyLoggedItems.size)
        assertEquals(initialCalories + currentFoodCal, updatedState.totalConsumedCalories)
        assertEquals(MealType.DINNER, updatedState.dailyLoggedItems.first().mealType)
    }

    @Test
    fun testRemoveAndClearDailyLog() {
        val state = viewModel.uiState.value
        assertTrue(state.dailyLoggedItems.isNotEmpty())

        val firstItemId = state.dailyLoggedItems.first().id
        viewModel.removeLoggedItem(firstItemId)
        assertFalse(viewModel.uiState.value.dailyLoggedItems.any { it.id == firstItemId })

        viewModel.clearDailyLog()
        assertTrue(viewModel.uiState.value.dailyLoggedItems.isEmpty())
        assertEquals(0, viewModel.uiState.value.totalConsumedCalories)
        assertEquals(viewModel.uiState.value.dailyCalorieGoal, viewModel.uiState.value.remainingCalories)
    }

    @Test
    fun testCalorieProgressCalculations() {
        viewModel.clearDailyLog()
        viewModel.setDailyGoal(2000)

        assertEquals(0f, viewModel.uiState.value.calorieProgressFraction, 0.001f)
        assertFalse(viewModel.uiState.value.isGoalExceeded)

        viewModel.selectFood(FoodDatabase.foods.first())
        viewModel.logCurrentFood(MealType.LUNCH)

        val consumed = viewModel.uiState.value.totalConsumedCalories
        val expectedFraction = consumed.toFloat() / 2000f
        assertEquals(expectedFraction, viewModel.uiState.value.calorieProgressFraction, 0.001f)
        assertEquals(2000 - consumed, viewModel.uiState.value.remainingCalories)
    }

    @Test
    fun testShareSummaryContent() {
        val shareText = viewModel.generateShareSummary()
        assertTrue("Share text must mention CalorieSnap", shareText.contains("CalorieSnap"))
        assertTrue("Share text must mention Estimated Calories", shareText.contains("Estimated Calories"))
        assertTrue("Share text must include Daily Goal Progress", shareText.contains("Daily Goal Progress"))
        assertTrue("Share text must include publisher ViveScript Solutions LLC", shareText.contains("ViveScript Solutions LLC"))
    }

    @Test
    fun testScreenNavigation() {
        assertEquals(com.example.viewmodel.AppScreen.CALCULATOR, viewModel.uiState.value.currentScreen)
        viewModel.navigateTo(com.example.viewmodel.AppScreen.SETTINGS)
        assertEquals(com.example.viewmodel.AppScreen.SETTINGS, viewModel.uiState.value.currentScreen)
        viewModel.navigateBack()
        assertEquals(com.example.viewmodel.AppScreen.CALCULATOR, viewModel.uiState.value.currentScreen)
    }

    @Test
    fun testDietaryPresetSelection() {
        viewModel.setDietaryPreset("WEIGHT_LOSS", 1600)
        assertEquals(1600, viewModel.uiState.value.dailyCalorieGoal)
        assertEquals("WEIGHT_LOSS", viewModel.uiState.value.dietaryPreset)

        viewModel.setDietaryPreset("MUSCLE_GAIN", 2400)
        assertEquals(2400, viewModel.uiState.value.dailyCalorieGoal)
        assertEquals("MUSCLE_GAIN", viewModel.uiState.value.dietaryPreset)
    }

    @Test
    fun testResetDailyGoalToDefault() {
        viewModel.setDailyGoal(2800)
        assertEquals(2800, viewModel.uiState.value.dailyCalorieGoal)
        viewModel.resetDailyGoalToDefault()
        assertEquals(2000, viewModel.uiState.value.dailyCalorieGoal)
        assertEquals("MAINTAIN", viewModel.uiState.value.dietaryPreset)
    }

    @Test
    fun testDataStoreUserPreferencesRepositoryDirectly() = kotlinx.coroutines.runBlocking {
        val repo = com.example.data.preferences.UserPreferencesRepository(app)
        repo.updateDailyCalorieGoal(1850)
        val goal = repo.dailyCalorieGoalFlow.first()
        assertEquals(1850, goal)

        repo.resetToDefaultGoal()
        val defaultGoal = repo.dailyCalorieGoalFlow.first()
        assertEquals(2000, defaultGoal)
    }

    @Test
    fun testMealLogEntityConversion() {
        val original = LoggedMealItem(
            id = "test-meal-1",
            foodNameEn = "Chicken Biryani",
            foodNameBn = "চিকেন বিরিয়ানি",
            calories = 550,
            grams = 300,
            carbsGrams = 65.0,
            proteinGrams = 28.0,
            fatGrams = 18.0,
            mealType = MealType.DINNER,
            timestamp = 1700000000000L
        )

        val entity = com.example.data.local.MealLogEntity.fromDomainModel(original)
        assertEquals("test-meal-1", entity.id)
        assertEquals("Chicken Biryani", entity.foodNameEn)
        assertEquals("চিকেন বিরিয়ানি", entity.foodNameBn)
        assertEquals(550, entity.calories)
        assertEquals(300, entity.grams)
        assertEquals(65.0, entity.carbsGrams, 0.001)
        assertEquals(28.0, entity.proteinGrams, 0.001)
        assertEquals(18.0, entity.fatGrams, 0.001)
        assertEquals("DINNER", entity.mealType)
        assertEquals(1700000000000L, entity.timestamp)

        val convertedBack = entity.toDomainModel()
        assertEquals(original, convertedBack)
    }

    @Test
    fun testMealLogDaoAndRepositoryOperations() = kotlinx.coroutines.runBlocking {
        val db = com.example.data.local.CalorieSnapDatabase.getDatabase(app)
        val dao = db.mealLogDao()
        dao.clearAllMealLogs()

        val item1 = LoggedMealItem(
            id = "log-1",
            foodNameEn = "Plain Rice",
            foodNameBn = "সাদা ভাত",
            calories = 200,
            grams = 150,
            mealType = MealType.LUNCH,
            timestamp = 1000L
        )
        val item2 = LoggedMealItem(
            id = "log-2",
            foodNameEn = "Fish Curry",
            foodNameBn = "রুই মাছের ঝোল",
            calories = 250,
            grams = 180,
            mealType = MealType.DINNER,
            timestamp = 2000L
        )

        dao.insertMealLog(com.example.data.local.MealLogEntity.fromDomainModel(item1))
        dao.insertMealLog(com.example.data.local.MealLogEntity.fromDomainModel(item2))

        val allLogs = dao.getAllMealLogs().first()
        assertEquals(2, allLogs.size)
        // Descending order by timestamp
        assertEquals("log-2", allLogs[0].id)
        assertEquals("log-1", allLogs[1].id)

        // Time range filter
        val filteredLogs = dao.getMealLogsBetween(500L, 1500L).first()
        assertEquals(1, filteredLogs.size)
        assertEquals("log-1", filteredLogs[0].id)

        // Total calories between
        val totalCalories = dao.getTotalCaloriesBetween(500L, 2500L).first()
        assertEquals(450, totalCalories)

        // Delete item
        dao.deleteMealLogById("log-1")
        val remaining = dao.getAllMealLogs().first()
        assertEquals(1, remaining.size)
        assertEquals("log-2", remaining[0].id)

        // Clear all
        dao.clearAllMealLogs()
        val emptyLogs = dao.getAllMealLogs().first()
        assertTrue(emptyLogs.isEmpty())
    }
}
