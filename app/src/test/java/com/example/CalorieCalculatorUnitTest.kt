package com.example

import com.example.data.FoodDatabase
import com.example.model.FoodCategory
import com.example.viewmodel.CalorieCalculatorViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CalorieCalculatorUnitTest {

    private lateinit var viewModel: CalorieCalculatorViewModel

    @Before
    fun setUp() {
        viewModel = CalorieCalculatorViewModel()
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
    fun testShareSummaryContent() {
        val shareText = viewModel.generateShareSummary()
        assertTrue("Share text must mention CalorieSnap", shareText.contains("CalorieSnap"))
        assertTrue("Share text must mention Estimated Calories", shareText.contains("Estimated Calories"))
        assertTrue("Share text must include publisher ViveScript Solutions LLC", shareText.contains("ViveScript Solutions LLC"))
    }
}
