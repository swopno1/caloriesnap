package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FoodCategory
import com.example.model.FoodItem
import com.example.model.ServingOption
import com.example.viewmodel.CalorieUiState

@Composable
fun FoodSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    isBangla: Boolean,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .testTag("food_search_input"),
        placeholder = {
            Text(
                text = if (isBangla) "খাবারের নাম দিয়ে খুঁজুন (যেমন: ভাত, বিরিয়ানি, ডিম)..."
                else "Search foods (e.g. Rice, Biryani, Roti, Apple)...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onQueryChange("")
                        focusManager.clearFocus()
                    },
                    modifier = Modifier.testTag("clear_search_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f),
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun CategoryFilterChipsRow(
    selectedCategory: FoodCategory,
    onCategorySelected: (FoodCategory) -> Unit,
    isBangla: Boolean,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(FoodCategory.values()) { category ->
            val isSelected = category == selectedCategory
            FilterChip(
                selected = isSelected,
                onClick = { onCategorySelected(category) },
                label = {
                    Text(
                        text = "${category.iconEmoji} ${if (isBangla) category.displayNameBn else category.displayNameEn}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    containerColor = MaterialTheme.colorScheme.surface,
                    labelColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.testTag("category_chip_${category.name.lowercase()}")
            )
        }
    }
}

@Composable
fun CalorieHeroCard(
    uiState: CalorieUiState,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val food = uiState.selectedFood
    val isBn = uiState.isBanglaLanguage
    val cal = uiState.currentCalculatedCalories
    val grams = uiState.currentCalculatedGrams

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("calorie_hero_card"),
        shape = RoundedCornerShape(26.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Top row: Food Name & Emoji
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = food?.emoji ?: "🍽️",
                            fontSize = 30.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isBn) food?.nameBn ?: "খাবার নির্বাচন করুন"
                            else food?.nameEn ?: "Select Food",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = if (isBn) food?.nameEn ?: "" else food?.nameBn ?: "",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Share button right on the Hero card
                    Button(
                        onClick = onShareClick,
                        modifier = Modifier
                            .testTag("share_summary_button")
                            .height(44.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(22.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share calorie estimate",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isBn) "শেয়ার" else "Share",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Giant Calorie Number Display
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = if (isBn) "আনুমানিক ক্যালোরি" else "ESTIMATED CALORIES",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "$cal",
                                style = MaterialTheme.typography.displayMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "kcal",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }

                    // Portion badge
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f),
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Text(
                            text = "~$grams g",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Macronutrients estimation pill summary
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    MacroNutrientItem(
                        label = if (isBn) "কার্বস" else "Carbs",
                        valueGrams = uiState.currentCarbs,
                        color = Color(0xFF2563EB)
                    )
                    MacroNutrientItem(
                        label = if (isBn) "প্রোটিন" else "Protein",
                        valueGrams = uiState.currentProtein,
                        color = Color(0xFF059669)
                    )
                    MacroNutrientItem(
                        label = if (isBn) "ফ্যাট" else "Fat",
                        valueGrams = uiState.currentFat,
                        color = Color(0xFFD97706)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Estimate disclaimer
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Estimate disclaimer",
                        modifier = Modifier.size(13.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isBn) "আনুমানিক মান। রান্নার তেল ও উপাদানের ওপর ক্যালোরি ভিন্ন হতে পারে।"
                        else "Approximate estimate. Actual calories vary by oil, recipe & ingredients.",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}

@Composable
private fun MacroNutrientItem(
    label: String,
    valueGrams: Double,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "${String.format("%.1f", valueGrams)}g",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

@Composable
fun PortionAdjusterSection(
    food: FoodItem,
    selectedOptionIndex: Int,
    quantityMultiplier: Double,
    isCustomMode: Boolean,
    customGramsInput: String,
    isBangla: Boolean,
    onOptionSelected: (Int) -> Unit,
    onMultiplierChange: (Double) -> Unit,
    onCustomModeToggle: (Boolean) -> Unit,
    onCustomGramsChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("portion_adjuster_section"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isBangla) "পরিবেশন আকার ও পরিমাণ" else "Portion & Serving Size",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Custom grams switch / button
                OutlinedButton(
                    onClick = { onCustomModeToggle(!isCustomMode) },
                    modifier = Modifier
                        .testTag("custom_grams_toggle_button")
                        .height(34.dp),
                    shape = RoundedCornerShape(17.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Custom grams",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isCustomMode) (if (isBangla) "নির্দিষ্ট অংশ" else "Preset")
                        else (if (isBangla) "গ্রাম লিখুন" else "Custom Grams"),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (!isCustomMode) {
                // Preset serving options chips
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    food.servingOptions.forEachIndexed { index, option ->
                        val isSelected = index == selectedOptionIndex
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOptionSelected(index) }
                                .testTag("serving_option_item_$index")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = if (isBangla) option.labelBn else option.labelEn,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                                        else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Text(
                                    text = "~${option.calories} kcal",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Quantity Multiplier Stepper (0.5x, 1x, 1.5x, 2x, 3x)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (isBangla) "পরিমাণ গুণক:" else "Quantity:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        IconButton(
                            onClick = {
                                val current = quantityMultiplier
                                val next = when {
                                    current > 2.0 -> current - 1.0
                                    current > 1.0 -> current - 0.5
                                    current > 0.5 -> 0.5
                                    else -> 0.5
                                }
                                onMultiplierChange(next)
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .testTag("decrease_quantity_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Decrease portion",
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {
                            Text(
                                text = "${quantityMultiplier}x",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }

                        IconButton(
                            onClick = {
                                val current = quantityMultiplier
                                val next = when {
                                    current < 1.0 -> 1.0
                                    current < 3.0 -> current + 0.5
                                    current < 6.0 -> current + 1.0
                                    else -> 6.0
                                }
                                onMultiplierChange(next)
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .testTag("increase_quantity_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Increase portion",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            } else {
                // Custom Grams Mode Input
                Column {
                    Text(
                        text = if (isBangla) "আপনার পছন্দের গ্রাম বা মিলি উল্লেখ করুন:"
                        else "Enter exact grams or ml:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = customGramsInput,
                            onValueChange = onCustomGramsChange,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("custom_grams_input_field"),
                            placeholder = { Text("e.g. 150") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(14.dp),
                            trailingIcon = {
                                Text(
                                    text = "grams",
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(end = 12.dp),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        // Quick preset buttons for 50g, 100g, 200g
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf("50", "100", "200").forEach { g ->
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (customGramsInput == g) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant,
                                    modifier = Modifier
                                        .clickable { onCustomGramsChange(g) }
                                ) {
                                    Text(
                                        text = "${g}g",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (customGramsInput == g) MaterialTheme.colorScheme.onPrimary
                                        else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FoodListItemCard(
    food: FoodItem,
    isSelected: Boolean,
    isBangla: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .testTag("food_item_card_${food.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
            else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 2.dp else 0.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = food.emoji, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isBangla) food.nameBn else food.nameEn,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = if (isBangla) food.descriptionBn else food.descriptionEn,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${food.defaultCaloriesPer100g}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "kcal / 100g",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
