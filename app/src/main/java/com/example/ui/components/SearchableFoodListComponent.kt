package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FoodCategory
import com.example.model.FoodItem

/**
 * A comprehensive, searchable list component in Jetpack Compose that interfaces
 * with the local Room database to filter, search, and select South Asian & Bangladeshi food items.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchableFoodListComponent(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCategory: FoodCategory,
    onCategorySelected: (FoodCategory) -> Unit,
    foodItems: List<FoodItem>,
    selectedFood: FoodItem?,
    onFoodSelected: (FoodItem) -> Unit,
    isBangla: Boolean,
    totalIndexedCount: Int,
    isRoomDbReady: Boolean,
    modifier: Modifier = Modifier
) {
    // Quick search suggestions for popular South Asian & Bengali dishes
    val quickSuggestions = listOf(
        Pair("Rice", "ভাত"),
        Pair("Biryani", "বিরিয়ানি"),
        Pair("Roti", "রুটি"),
        Pair("Daal", "ডাল"),
        Pair("Ilish", "ইলিশ মাছ"),
        Pair("Khichuri", "খিচুড়ি"),
        Pair("Sweets", "মিষ্টি")
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("searchable_food_list_component"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 1. Search Bar with Instant Clear
        FoodSearchBar(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            isBangla = isBangla
        )

        // 2. Quick Search Suggestion Pills
        AnimatedVisibility(
            visible = searchQuery.isEmpty() && selectedCategory == FoodCategory.ALL,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (isBangla) "জনপ্রিয় খাবার:" else "Popular staples:",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    quickSuggestions.forEach { (en, bn) ->
                        val label = if (isBangla) bn else en
                        AssistChip(
                            onClick = { onSearchQueryChange(en) },
                            label = { Text(text = label, style = MaterialTheme.typography.labelSmall) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("suggestion_chip_${en.lowercase()}")
                        )
                    }
                }
            }
        }

        // 3. Category Filter Chips
        CategoryFilterChipsRow(
            selectedCategory = selectedCategory,
            onCategorySelected = onCategorySelected,
            isBangla = isBangla
        )

        // 4. Room Database Offline Status & Filter Counter Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Room Database Offline indicator badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                    modifier = Modifier.testTag("room_db_status_badge")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Storage,
                            contentDescription = "Room Database",
                            modifier = Modifier.size(13.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isBangla) "অফলাইন ডাটাবেস (${foodItems.size}/${totalIndexedCount})"
                            else "Room DB Offline (${foodItems.size}/${totalIndexedCount})",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            // Clear Filter button if search or category is active
            if (searchQuery.isNotEmpty() || selectedCategory != FoodCategory.ALL) {
                TextButton(
                    onClick = {
                        onSearchQueryChange("")
                        onCategorySelected(FoodCategory.ALL)
                    },
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    modifier = Modifier.testTag("reset_filters_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Reset filters",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isBangla) "ফিল্টার মুছুন" else "Reset",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // 5. Empty State when query yields no results
        if (foodItems.isEmpty()) {
            RoomEmptyStateCard(
                query = searchQuery,
                category = selectedCategory,
                isBangla = isBangla,
                onResetClick = {
                    onSearchQueryChange("")
                    onCategorySelected(FoodCategory.ALL)
                }
            )
        } else {
            // 6. Food items cards list
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                foodItems.forEach { food ->
                    FoodListItemCard(
                        food = food,
                        isSelected = food.id == selectedFood?.id,
                        isBangla = isBangla,
                        onSelect = { onFoodSelected(food) }
                    )
                }
            }
        }
    }
}

/**
 * Empty state card shown when the Room database search yields no matching dishes.
 */
@Composable
fun RoomEmptyStateCard(
    query: String,
    category: FoodCategory,
    isBangla: Boolean,
    onResetClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .testTag("empty_search_results_card"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = if (isBangla) {
                    if (query.isNotEmpty()) "‘$query’ সম্পর্কিত কোনো খাবার পাওয়া যায়নি"
                    else "এই ক্যাটাগরিতে কোনো খাবার পাওয়া যায়নি"
                } else {
                    if (query.isNotEmpty()) "No dishes found matching '$query'"
                    else "No dishes found in category '${category.displayNameEn}'"
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = if (isBangla) "বানান পরীক্ষা করুন বা সব খাবার দেখতে ফিল্টার রিসেট করুন।"
                else "Check spelling, try South Asian staples like Rice, Daal, Roti, or reset filters.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onResetClick,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("empty_state_reset_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isBangla) "সব খাবার দেখুন" else "View All Foods",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
