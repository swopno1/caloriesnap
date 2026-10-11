package com.example.ui.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
import com.example.data.FoodDatabase
import com.example.ui.components.AboutInfoDialog
import com.example.ui.components.CalorieHeroCard
import com.example.ui.components.CategoryFilterChipsRow
import com.example.ui.components.FoodListItemCard
import com.example.ui.components.FoodSearchBar
import com.example.ui.components.PortionAdjusterSection
import com.example.viewmodel.CalorieCalculatorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalorieCalculatorScreen(
    viewModel: CalorieCalculatorViewModel,
    interstitialAdManager: InterstitialAdManager? = null,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showAboutDialog by remember { mutableStateOf(false) }

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
                                text = if (uiState.isBanglaLanguage) "ক্যালোরি ক্যালকুলেটর" else "Quick Calorie Estimator",
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
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // 1. Search Bar
                item {
                    FoodSearchBar(
                        query = uiState.searchQuery,
                        onQueryChange = { viewModel.onSearchQueryChange(it) },
                        isBangla = uiState.isBanglaLanguage
                    )
                }

                // 2. Category Filter Chips
                item {
                    CategoryFilterChipsRow(
                        selectedCategory = uiState.selectedCategory,
                        onCategorySelected = { viewModel.onCategorySelect(it) },
                        isBangla = uiState.isBanglaLanguage
                    )
                }

                // 3. Main Calorie Hero Result Display Card
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

                // 4. Portion / Serving Adjuster Section
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

                // 5. Section Header for Food Directory
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (uiState.isBanglaLanguage) "খাবারের তালিকা (${uiState.filteredFoods.size})"
                            else "Foods Directory (${uiState.filteredFoods.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = if (uiState.isBanglaLanguage) "ট্যাপ করে সিলেক্ট করুন" else "Tap to select",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // 6. Filtered Food List Items
                items(uiState.filteredFoods, key = { it.id }) { food ->
                    FoodListItemCard(
                        food = food,
                        isSelected = food.id == uiState.selectedFood?.id,
                        isBangla = uiState.isBanglaLanguage,
                        onSelect = { viewModel.selectFood(food) }
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
