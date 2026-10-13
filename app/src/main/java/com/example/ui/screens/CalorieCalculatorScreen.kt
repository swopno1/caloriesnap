package com.example.ui.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdMobBanner
import com.example.ads.InterstitialAdManager
import com.example.ui.components.AboutInfoDialog
import com.example.ui.components.CalorieHeroCard
import com.example.ui.components.DailyCalorieProgressChart
import com.example.ui.components.PortionAdjusterSection
import com.example.ui.components.QuickExtraIntakeDialog
import com.example.ui.components.SearchableFoodListComponent
import com.example.viewmodel.CalorieCalculatorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalorieCalculatorScreen(
    viewModel: CalorieCalculatorViewModel,
    interstitialAdManager: InterstitialAdManager? = null,
    onOpenSettings: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showAboutDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { androidx.compose.material3.SnackbarHostState() }
    val coroutineScope = androidx.compose.runtime.rememberCoroutineScope()

    fun showFeedback(msg: String) {
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(message = msg, duration = androidx.compose.material3.SnackbarDuration.Short)
        }
    }

    fun shareResult(shareText: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(
            sendIntent,
            if (uiState.isBanglaLanguage) "শেয়ার করুন" else "Share via"
        )
        context.startActivity(shareIntent)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { androidx.compose.material3.SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🥗 ", fontSize = 24.sp)
                        Column {
                            Text(
                                text = "CalorieSnap",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = if (uiState.isBanglaLanguage) "ক্যালোরি ক্যালকুলেটর ও ট্র্যাকার" else "Calorie Estimator & Tracker",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    // Settings Button
                    IconButton(
                        onClick = onOpenSettings,
                        modifier = Modifier.testTag("open_settings_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = if (uiState.isBanglaLanguage) "সেটিংস" else "Settings",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    // Language Switch Button
                    TextButton(
                        onClick = { viewModel.toggleLanguage() },
                        modifier = Modifier.testTag("toggle_language_button")
                    ) {
                        Text(
                            text = if (uiState.isBanglaLanguage) "ENG" else "বাংলা",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    // About / Info Button
                    IconButton(
                        onClick = { showAboutDialog = true },
                        modifier = Modifier.testTag("about_app_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About CalorieSnap",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            )
        },
        bottomBar = {
            // Google AdMob Banner Ad at Bottom
            Surface(
                tonalElevation = 2.dp,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AdMobBanner()
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 680.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. Refined Daily Calorie Goal & Today's Intake Hub
                item {
                    DailyCalorieProgressChart(
                        uiState = uiState,
                        onSetDailyGoal = {
                            viewModel.setDailyGoal(it)
                            showFeedback(if (uiState.isBanglaLanguage) "লক্ষ্যমাত্রা সংরক্ষিত: $it kcal" else "Goal saved: $it kcal")
                        },
                        onLogCurrentFood = { mealType ->
                            val foodName = if (uiState.isBanglaLanguage) uiState.selectedFood?.nameBn else uiState.selectedFood?.nameEn
                            viewModel.logCurrentFood(mealType)
                            val mealTitle = if (uiState.isBanglaLanguage) mealType.titleBn else mealType.titleEn
                            showFeedback(
                                if (uiState.isBanglaLanguage) "‘$foodName’ $mealTitle-এ যোগ করা হয়েছে!"
                                else "Logged '$foodName' to $mealTitle!"
                            )
                        },
                        onRemoveLoggedItem = { id ->
                            viewModel.removeLoggedItem(id)
                            showFeedback(if (uiState.isBanglaLanguage) "খাবারটি তালিকা থেকে সরানো হয়েছে" else "Item removed from log")
                        },
                        onClearDailyLog = {
                            viewModel.clearDailyLog()
                            showFeedback(if (uiState.isBanglaLanguage) "আজকের তালিকা রিসেট করা হয়েছে" else "Daily log reset")
                        },
                        onOpenSettings = onOpenSettings,
                        onUpdateLoggedItem = { item ->
                            viewModel.updateLoggedItem(item)
                            val name = if (uiState.isBanglaLanguage) item.foodNameBn else item.foodNameEn
                            showFeedback(if (uiState.isBanglaLanguage) "‘$name’ আপডেট করা হয়েছে!" else "Updated '$name'!")
                        },
                        onAddExtraIntake = { nameEn, nameBn, cal, grams, mealType ->
                            viewModel.logExtraIntake(nameEn, nameBn, cal, grams, mealType)
                            val name = if (uiState.isBanglaLanguage) nameBn else nameEn
                            showFeedback(if (uiState.isBanglaLanguage) "‘$name’ ($cal kcal) যোগ করা হয়েছে!" else "Added '$name' ($cal kcal)!")
                        }
                    )
                }

                // 2. Active Food Calorie Calculator & Macro Result Display Card
                item {
                    CalorieHeroCard(
                        uiState = uiState,
                        onShareClick = {
                            val textToShare = viewModel.generateShareSummary()
                            if (context is Activity && interstitialAdManager != null) {
                                interstitialAdManager.showAd(context) {
                                    shareResult(textToShare)
                                }
                            } else {
                                shareResult(textToShare)
                            }
                        }
                    )
                }

                // 3. Portion / Serving Adjuster Section
                val currentFood = uiState.selectedFood
                if (currentFood != null) {
                    item {
                        PortionAdjusterSection(
                            food = currentFood,
                            selectedOptionIndex = uiState.selectedOptionIndex,
                            quantityMultiplier = uiState.quantityMultiplier,
                            isCustomMode = uiState.isCustomGramMode,
                            customGramsInput = uiState.customGrams,
                            isBangla = uiState.isBanglaLanguage,
                            onOptionSelected = { viewModel.selectServingOption(it) },
                            onMultiplierChange = { viewModel.setQuantityMultiplier(it) },
                            onCustomModeToggle = { viewModel.setCustomGramMode(it) },
                            onCustomGramsChange = { viewModel.updateCustomGrams(it) }
                        )
                    }
                }

                // 4. Searchable List Component (Queries Room Database)
                item {
                    SearchableFoodListComponent(
                        searchQuery = uiState.searchQuery,
                        onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
                        selectedCategory = uiState.selectedCategory,
                        onCategorySelected = { viewModel.onCategorySelect(it) },
                        foodItems = uiState.filteredFoods,
                        selectedFood = uiState.selectedFood,
                        onFoodSelected = { viewModel.selectFood(it) },
                        isBangla = uiState.isBanglaLanguage,
                        totalIndexedCount = uiState.totalIndexedFoodsCount,
                        isRoomDbReady = uiState.isRoomDbReady
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

    // Dialogs
    if (showAboutDialog) {
        AboutInfoDialog(
            onDismiss = { showAboutDialog = false },
            isBangla = uiState.isBanglaLanguage
        )
    }
}
