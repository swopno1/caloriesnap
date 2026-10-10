package com.example.ui.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ads.AdMobBanner
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
    modifier: Modifier = Modifier,
    onTriggerShareWithInterstitial: ((() -> Unit) -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showAboutDialog by remember { mutableStateOf(false) }

    val onShareClicked: () -> Unit = {
        val performShare = {
            shareCalorieSummary(context, viewModel.generateShareSummary())
        }
        if (onTriggerShareWithInterstitial != null) {
            onTriggerShareWithInterstitial(performShare)
        } else {
            performShare()
        }
    }

    if (showAboutDialog) {
        AboutInfoDialog(
            onDismiss = { showAboutDialog = false },
            isBangla = uiState.isBanglaLanguage
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                text = "🔥",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = "CalorieSnap",
                            fontWeight = FontWeight.ExtraBold,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    // Language Switcher (EN <-> BN)
                    IconButton(
                        onClick = { viewModel.toggleLanguage() },
                        modifier = Modifier.testTag("language_toggle_button")
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = if (uiState.isBanglaLanguage) "বাংলা" else "EN",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // About & Legal Dialog
                    IconButton(
                        onClick = { showAboutDialog = true },
                        modifier = Modifier.testTag("about_app_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About CalorieSnap",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            // Google AdMob Banner Placement
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                AdMobBanner(
                    modifier = Modifier.testTag("admob_banner_view")
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("calorie_main_scrollable_list"),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search Input
            item {
                FoodSearchBar(
                    query = uiState.searchQuery,
                    onQueryChange = { viewModel.onSearchQueryChange(it) },
                    isBangla = uiState.isBanglaLanguage
                )
            }

            // Category Chips Row
            item {
                CategoryFilterChipsRow(
                    selectedCategory = uiState.selectedCategory,
                    onCategorySelected = { viewModel.onCategorySelect(it) },
                    isBangla = uiState.isBanglaLanguage
                )
            }

            // Hero Card showing active Food & Real-time Calories
            item {
                CalorieHeroCard(
                    uiState = uiState,
                    onShareClick = onShareClicked
                )
            }

            // Portion Adjuster (Portion Options, Stepper, Custom Grams)
            uiState.selectedFood?.let { food ->
                item {
                    PortionAdjusterSection(
                        food = food,
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

            // Food List Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (uiState.isBanglaLanguage) "খাবারের তালিকা (${uiState.filteredFoods.size})"
                        else "Food Database (${uiState.filteredFoods.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (uiState.isBanglaLanguage) "অফলাইন ডাটাবেজ" else "Offline Database",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Filtered Food List Items
            if (uiState.filteredFoods.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Restaurant,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = if (uiState.isBanglaLanguage) "কোনো খাবার পাওয়া যায়নি"
                                else "No food matched your search",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = if (uiState.isBanglaLanguage) "অন্য কোনো নাম বা ক্যাটাগরি বেছে নিন"
                                else "Try searching with a different keyword or category",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(
                    items = uiState.filteredFoods,
                    key = { it.id }
                ) { food ->
                    FoodListItemCard(
                        food = food,
                        isSelected = food.id == uiState.selectedFood?.id,
                        isBangla = uiState.isBanglaLanguage,
                        onSelect = { viewModel.selectFood(food) }
                    )
                }
            }

            // Bottom space for comfortable scrolling above the ad banner
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

/**
 * Android Intent system share for WhatsApp, Facebook, Instagram Stories, TikTok, Messages, etc.
 */
private fun shareCalorieSummary(context: Context, summaryText: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, summaryText)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share Calorie Estimate via")
    context.startActivity(shareIntent)
}
