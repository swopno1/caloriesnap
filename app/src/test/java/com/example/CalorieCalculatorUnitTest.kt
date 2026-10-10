package com.example

import com.example.data.FoodDatabase
import com.example.model.FoodCategory
import com.example.viewmodel.CalorieCalculatorViewModel
import org.junit.Assert.assertEquals
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
    fun database_containsExpectedBanglaAndSouthAsianFoods() {
        val foods = FoodDatabase.foods
        assertTrue("Food database should have at least 25 items", foods.size >= 25)

        val foodIds = foods.map { it.id }
        assertTrue("Should contain rice", foodIds.contains("rice_white"))
        assertTrue("Should contain roti", foodIds.contains("roti_chapati"))
        assertTrue("Should contain biryani", foodIds.contains("biryani_chicken"))
        assertTrue("Should contain dal", foodIds.contains("dal_masoor_patla"))
        assertTrue("Should contain singara", foodIds.contains("singara_bangla"))
        assertTrue("Should contain hilsa", foodIds.contains("hilsa_curry"))
        assertTrue("Should contain boiled egg", foodIds.contains("egg_boiled_hen"))
    }

    @Test
    fun calorieCalculation_scalesWithQuantityMultiplier() {
        val roti = FoodDatabase.foods.first { it.id == "roti_chapati" }
        viewModel.selectFood(roti)
        viewModel.selectServingOption(0) // 1 Medium Roti (35g, 85 cal)
        viewModel.setQuantityMultiplier(1.0)

        assertEquals(85, viewModel.uiState.value.currentCalculatedCalories)

        viewModel.setQuantityMultiplier(2.0)
        assertEquals(170, viewModel.uiState.value.currentCalculatedCalories)

        viewModel.setQuantityMultiplier(0.5)
        assertEquals(43, viewModel.uiState.value.currentCalculatedCalories)
    }

    @Test
    fun customGramsMode_calculatesCaloriesAccurately() {
        val rice = FoodDatabase.foods.first { it.id == "rice_white" } // 130 cal per 100g
        viewModel.selectFood(rice)
        viewModel.setCustomGramMode(true)
        viewModel.updateCustomGrams("200")

        assertEquals(200, viewModel.uiState.value.currentCalculatedGrams)
        // 200g * 130 / 100 = 260 kcal
        assertEquals(260, viewModel.uiState.value.currentCalculatedCalories)
    }

    @Test
    fun boundaryCase_emptyOrInvalidCustomGramsInput_doesNotCrash() {
        val rice = FoodDatabase.foods.first { it.id == "rice_white" }
        viewModel.selectFood(rice)
        viewModel.setCustomGramMode(true)
        viewModel.updateCustomGrams("")
        assertEquals(0, viewModel.uiState.value.currentCalculatedGrams)
        assertEquals(0, viewModel.uiState.value.currentCalculatedCalories)

        // Extremely large number filtered to 4 digits maximum safely
        viewModel.updateCustomGrams("999999")
        assertEquals(9999, viewModel.uiState.value.currentCalculatedGrams)
    }

    @Test
    fun boundaryCase_zeroOrNegativeQuantityMultiplier_isSafelyIgnored() {
        val roti = FoodDatabase.foods.first { it.id == "roti_chapati" }
        viewModel.selectFood(roti)
        viewModel.setQuantityMultiplier(1.0)
        viewModel.setQuantityMultiplier(0.0)
        assertEquals(1.0, viewModel.uiState.value.quantityMultiplier, 0.001)

        viewModel.setQuantityMultiplier(-1.5)
        assertEquals(1.0, viewModel.uiState.value.quantityMultiplier, 0.001)
    }

    @Test
    fun macronutrients_calculateAccurately() {
        val rice = FoodDatabase.foods.first { it.id == "rice_white" } // per 100g: 28g carb, 2.7g prot, 0.3g fat
        viewModel.selectFood(rice)
        viewModel.setCustomGramMode(true)
        viewModel.updateCustomGrams("100")

        assertEquals(28.0, viewModel.uiState.value.currentCarbs, 0.01)
        assertEquals(2.7, viewModel.uiState.value.currentProtein, 0.01)
        assertEquals(0.3, viewModel.uiState.value.currentFat, 0.01)
    }

    @Test
    fun searchFiltering_findsMatchingFoodsInBothEnglishAndBangla() {
        viewModel.onSearchQueryChange("Biryani")
        val resultsEn = viewModel.uiState.value.filteredFoods
        assertTrue(resultsEn.any { it.nameEn.contains("Biryani", ignoreCase = true) })

        viewModel.onSearchQueryChange("ভাত")
        val resultsBn = viewModel.uiState.value.filteredFoods
        assertTrue(resultsBn.any { it.nameBn.contains("ভাত") })
    }

    @Test
    fun searchFiltering_emptyQuery_returnsAllFoods() {
        viewModel.onSearchQueryChange("NonExistentSuperFood12345")
        assertTrue(viewModel.uiState.value.filteredFoods.isEmpty())

        viewModel.onSearchQueryChange("")
        assertEquals(FoodDatabase.foods.size, viewModel.uiState.value.filteredFoods.size)
    }

    @Test
    fun categoryFiltering_narrowsDownResults() {
        viewModel.onCategorySelect(FoodCategory.SNACKS_STREET)
        val snackResults = viewModel.uiState.value.filteredFoods
        assertTrue(snackResults.isNotEmpty())
        assertTrue(snackResults.all { it.category == FoodCategory.SNACKS_STREET })
    }

    @Test
    fun bilingualToggle_switchesLanguageState() {
        val initialLang = viewModel.uiState.value.isBanglaLanguage
        viewModel.toggleLanguage()
        assertEquals(!initialLang, viewModel.uiState.value.isBanglaLanguage)
        viewModel.toggleLanguage()
        assertEquals(initialLang, viewModel.uiState.value.isBanglaLanguage)
    }

    @Test
    fun shareSummary_generatesCompleteReadableText() {
        val apple = FoodDatabase.foods.first { it.id == "apple_fresh" }
        viewModel.selectFood(apple)
        val summary = viewModel.generateShareSummary()

        assertNotNull(summary)
        assertTrue(summary.contains("CalorieSnap"))
        assertTrue(summary.contains("Apple"))
        assertTrue(summary.contains("kcal"))
        assertTrue(summary.contains("ViveScript Solutions LLC"))
    }
}
