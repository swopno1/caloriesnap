package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LoggedMealItem
import com.example.model.MealType
import com.example.viewmodel.CalorieUiState
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Visual data visualization component showing the user's daily calorie consumption
 * progress against their target goal, featuring:
 * 1. Animated radial progress gauge with target milestone indicators
 * 2. Multi-segment stacked distribution bar by meal (Breakfast, Lunch, Dinner, Snack)
 * 3. Daily macronutrient meters (Carbs, Protein, Fat)
 * 4. Interactive meal logger for current food item & goal customization dialog
 */
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DailyCalorieProgressChart(
    uiState: CalorieUiState,
    onSetDailyGoal: (Int) -> Unit,
    onLogCurrentFood: (MealType) -> Unit,
    onRemoveLoggedItem: (String) -> Unit,
    onClearDailyLog: () -> Unit,
    onOpenSettings: (() -> Unit)? = null,
    onUpdateLoggedItem: ((LoggedMealItem) -> Unit)? = null,
    onAddExtraIntake: ((nameEn: String, nameBn: String, calories: Int, grams: Int, mealType: MealType) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val isBn = uiState.isBanglaLanguage
    var showGoalDialog by remember { mutableStateOf(false) }
    var isHistoryExpanded by remember { mutableStateOf(true) }
    var editingMealItem by remember { mutableStateOf<LoggedMealItem?>(null) }
    var showExtraIntakeDialog by remember { mutableStateOf(false) }

    val consumed = uiState.totalConsumedCalories
    val goal = uiState.dailyCalorieGoal
    val remaining = uiState.remainingCalories
    val isOver = uiState.isGoalExceeded
    val progressFraction = uiState.calorieProgressFraction

    // Animated progress for gauge arc
    val animatedProgress by animateFloatAsState(
        targetValue = progressFraction.coerceIn(0f, 1.5f),
        animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing),
        label = "radial_calorie_progress"
    )

    // Dynamic color depending on whether user is within goal or exceeded
    val primaryGaugeColor by animateColorAsState(
        targetValue = when {
            isOver -> Color(0xFFEF4444) // Coral Red for surplus
            progressFraction >= 0.9f -> Color(0xFF10B981) // Vibrant Emerald
            progressFraction >= 0.5f -> Color(0xFF06B6D4) // Bright Cyan
            else -> Color(0xFF3B82F6) // Active Blue
        },
        animationSpec = tween(durationMillis = 500),
        label = "gauge_color"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("daily_calorie_progress_card"),
        shape = RoundedCornerShape(26.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Row: Title & Goal Setting Chip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.TrackChanges,
                            contentDescription = "Daily Goal",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    val todayFormatted = if (isBn) {
                        "আজ, " + java.text.SimpleDateFormat("d MMMM", java.util.Locale("bn")).format(java.util.Date())
                    } else {
                        "Today, " + java.text.SimpleDateFormat("EEEE, MMM d", java.util.Locale.getDefault()).format(java.util.Date())
                    }
                    Column {
                        Text(
                            text = if (isBn) "দৈনিক ক্যালোরি লক্ষ্য" else "Daily Calorie Goal",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = todayFormatted,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    StatusPill(
                        progress = progressFraction,
                        isExceeded = isOver,
                        isBn = isBn
                    )

                    // Edit Goal Button Chip
                    Surface(
                        onClick = {
                            if (onOpenSettings != null) {
                                onOpenSettings()
                            } else {
                                showGoalDialog = true
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f),
                        modifier = Modifier.testTag("edit_daily_goal_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = if (isBn) "$goal kcal" else "$goal kcal",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Goal",
                                modifier = Modifier.size(13.dp),
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }

            // Radial Progress Gauge (Canvas drawing with animated sweep arc)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .size(195.dp)
                        .testTag("daily_progress_radial_gauge")
                ) {
                    val strokeWidthPx = 18.dp.toPx()
                    val arcSize = Size(size.width - strokeWidthPx, size.height - strokeWidthPx)
                    val topLeft = Offset(strokeWidthPx / 2f, strokeWidthPx / 2f)
                    val startAngle = 135f
                    val maxSweepAngle = 270f

                    // 1. Draw Background Track Arc
                    drawArc(
                        color = Color(0xFFE2E8F0),
                        startAngle = startAngle,
                        sweepAngle = maxSweepAngle,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
                    )

                    // 2. Draw Foreground Progress Arc
                    val currentSweep = (animatedProgress * maxSweepAngle).coerceIn(0f, maxSweepAngle)
                    if (currentSweep > 0f) {
                        val brush = Brush.sweepGradient(
                            colors = if (isOver) {
                                listOf(Color(0xFFF97316), Color(0xFFEF4444), Color(0xFFDC2626))
                            } else {
                                listOf(Color(0xFF06B6D4), Color(0xFF10B981), primaryGaugeColor)
                            },
                            center = center
                        )
                        drawArc(
                            brush = brush,
                            startAngle = startAngle,
                            sweepAngle = currentSweep,
                            useCenter = false,
                            topLeft = topLeft,
                            size = arcSize,
                            style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
                        )
                    }

                    // 3. Goal Milestone Tick Marker at 100% (270 degrees sweep from startAngle)
                    val goalAngleRad = Math.toRadians((startAngle + maxSweepAngle).toDouble())
                    val radius = (size.width - strokeWidthPx) / 2f
                    val markerX = center.x + (radius * cos(goalAngleRad)).toFloat()
                    val markerY = center.y + (radius * sin(goalAngleRad)).toFloat()

                    drawCircle(
                        color = if (isOver) Color(0xFFEF4444) else Color(0xFF10B981),
                        radius = 5.dp.toPx(),
                        center = Offset(markerX, markerY)
                    )
                }

                // Centered Information inside the Arc
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "$consumed",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        letterSpacing = (-1).sp
                    )
                    Text(
                        text = if (isBn) "গৃহীত / $goal kcal" else "consumed of $goal kcal",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Remaining / Exceeded Status Badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isOver) Color(0xFFFEE2E2) else Color(0xFFECFDF5),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isOver) Color(0xFFFCA5A5) else Color(0xFFA7F3D0)
                        )
                    ) {
                        Text(
                            text = when {
                                isOver -> if (isBn) "⚠️ লক্ষ্যমাত্রার চেয়ে ${-remaining} kcal বেশি" else "⚠️ ${-remaining} kcal over goal"
                                remaining == 0 -> if (isBn) "🎉 লক্ষ্যমাত্রা পূর্ণ!" else "🎉 Goal reached!"
                                else -> if (isBn) "বাকি: $remaining kcal" else "$remaining kcal remaining"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (isOver) Color(0xFFB91C1C) else Color(0xFF047857),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    // Percentage Badge
                    val percentage = (progressFraction * 100).toInt()
                    Text(
                        text = "$percentage%",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = primaryGaugeColor,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Stacked Meal Distribution Bar (Recharts / D3 inspired horizontal breakdown)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isBn) "বেলার খাবারের বণ্টন" else "Meal Distribution Breakdown",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${uiState.dailyLoggedItems.size} ${if (isBn) "টি পদ" else "items logged"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Horizontal Segmented Bar
                MealDistributionBar(
                    breakfastCal = uiState.breakfastCalories,
                    lunchCal = uiState.lunchCalories,
                    dinnerCal = uiState.dinnerCalories,
                    snackCal = uiState.snackCalories,
                    goalCalories = goal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                )

                // Compact Meal Subtotals Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    CompactMealSubtotalTile(
                        emoji = MealType.BREAKFAST.iconEmoji,
                        label = if (isBn) MealType.BREAKFAST.shortTitleBn else MealType.BREAKFAST.shortTitleEn,
                        calories = uiState.breakfastCalories,
                        color = Color(MealType.BREAKFAST.colorHex),
                        modifier = Modifier.weight(1f)
                    )
                    CompactMealSubtotalTile(
                        emoji = MealType.LUNCH.iconEmoji,
                        label = if (isBn) MealType.LUNCH.shortTitleBn else MealType.LUNCH.shortTitleEn,
                        calories = uiState.lunchCalories,
                        color = Color(MealType.LUNCH.colorHex),
                        modifier = Modifier.weight(1f)
                    )
                    CompactMealSubtotalTile(
                        emoji = MealType.DINNER.iconEmoji,
                        label = if (isBn) MealType.DINNER.shortTitleBn else MealType.DINNER.shortTitleEn,
                        calories = uiState.dinnerCalories,
                        color = Color(MealType.DINNER.colorHex),
                        modifier = Modifier.weight(1f)
                    )
                    CompactMealSubtotalTile(
                        emoji = MealType.SNACK.iconEmoji,
                        label = if (isBn) MealType.SNACK.shortTitleBn else MealType.SNACK.shortTitleEn,
                        calories = uiState.snackCalories,
                        color = Color(MealType.SNACK.colorHex),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Daily Macronutrient Consumption Meters
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MacroProgressCard(
                    title = if (isBn) "কার্বস" else "Carbs",
                    grams = uiState.totalCarbsConsumed,
                    targetGrams = 250.0,
                    color = Color(0xFF3B82F6),
                    iconEmoji = "🌾",
                    modifier = Modifier.weight(1f)
                )
                MacroProgressCard(
                    title = if (isBn) "প্রোটিন" else "Protein",
                    grams = uiState.totalProteinConsumed,
                    targetGrams = 75.0,
                    color = Color(0xFF8B5CF6),
                    iconEmoji = "🥩",
                    modifier = Modifier.weight(1f)
                )
                MacroProgressCard(
                    title = if (isBn) "ফ্যাট" else "Fat",
                    grams = uiState.totalFatConsumed,
                    targetGrams = 60.0,
                    color = Color(0xFFF59E0B),
                    iconEmoji = "🥑",
                    modifier = Modifier.weight(1f)
                )
            }

            // Action: Log Currently Calculated Dish into Daily Intake
            val selectedFood = uiState.selectedFood
            if (selectedFood != null && uiState.currentCalculatedCalories > 0) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = if (isBn) "➕ আজকের অগ্রগতিতে যোগ করুন:" else "➕ Log Calculation to Daily Goal:",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "${if (isBn) selectedFood.nameBn else selectedFood.nameEn} (~${uiState.currentCalculatedCalories} kcal)",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        // Meal Type Selection Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            MealType.values().forEach { mealType ->
                                Button(
                                    onClick = { onLogCurrentFood(mealType) },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(mealType.colorHex)
                                    ),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("log_to_${mealType.name.lowercase()}_button")
                                ) {
                                    Text(
                                        text = "${mealType.iconEmoji} ${if (isBn) mealType.shortTitleBn else mealType.shortTitleEn}",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Logged Meals History Drawer with Easy Edit & Quick Extra Intake
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isHistoryExpanded = !isHistoryExpanded },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = if (isBn) "আজকের খাবারের তালিকা (${uiState.dailyLoggedItems.size})"
                                else "Today's Logged Meals (${uiState.dailyLoggedItems.size})",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (uiState.dailyLoggedItems.isNotEmpty() && isHistoryExpanded) {
                                TextButton(
                                    onClick = onClearDailyLog,
                                    contentPadding = PaddingValues(horizontal = 6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.RestartAlt,
                                        contentDescription = "Clear",
                                        modifier = Modifier.size(16.dp),
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (isBn) "রিসেট" else "Reset",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                            Icon(
                                imageVector = if (isHistoryExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Toggle History",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Button to quickly add extra intake: Tea, snacks, chips, etc.
                    Button(
                        onClick = { showExtraIntakeDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("add_extra_intake_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isBn) "+ অতিরিক্ত নাস্তা বা চা যোগ করুন (চা, চিপস, বিস্কুট)"
                            else "+ Add Extra Intake (Tea, Snacks, Chips, etc.)",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                    AnimatedVisibility(
                        visible = isHistoryExpanded || uiState.dailyLoggedItems.isEmpty(),
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        if (uiState.dailyLoggedItems.isEmpty()) {
                            Text(
                                text = if (isBn) "আজকে এখনও কোনো খাবার যোগ করা হয়নি।" else "No meals logged yet today.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        } else {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                uiState.dailyLoggedItems.forEach { item ->
                                    LoggedMealRow(
                                        item = item,
                                        isBn = isBn,
                                        onEdit = { editingMealItem = item },
                                        onDelete = { onRemoveLoggedItem(item.id) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Edit Daily Meal Item Dialog
    editingMealItem?.let { itemToEdit ->
        EditMealItemDialog(
            item = itemToEdit,
            isBn = isBn,
            onDismiss = { editingMealItem = null },
            onSave = { updated ->
                onUpdateLoggedItem?.invoke(updated)
                editingMealItem = null
            }
        )
    }

    // Quick Extra Intake Dialog (Tea, Snacks, Chips, etc.)
    if (showExtraIntakeDialog) {
        QuickExtraIntakeDialog(
            isBn = isBn,
            onDismiss = { showExtraIntakeDialog = false },
            onConfirm = { nameEn, nameBn, cal, grams, mealType ->
                onAddExtraIntake?.invoke(nameEn, nameBn, cal, grams, mealType)
                showExtraIntakeDialog = false
            }
        )
    }

    // Daily Calorie Goal Editing Dialog
    if (showGoalDialog) {
        GoalSetterDialog(
            currentGoal = goal,
            isBn = isBn,
            onDismiss = { showGoalDialog = false },
            onSave = {
                onSetDailyGoal(it)
                showGoalDialog = false
            }
        )
    }
}

/**
 * Segmented horizontal distribution bar illustrating proportional calorie intake by meal.
 */
@Composable
private fun MealDistributionBar(
    breakfastCal: Int,
    lunchCal: Int,
    dinnerCal: Int,
    snackCal: Int,
    goalCalories: Int,
    modifier: Modifier = Modifier
) {
    val totalLogged = (breakfastCal + lunchCal + dinnerCal + snackCal).coerceAtLeast(1)
    val maxRef = if (totalLogged > goalCalories) totalLogged.toFloat() else goalCalories.toFloat()

    val bFraction = (breakfastCal / maxRef).coerceIn(0f, 1f)
    val lFraction = (lunchCal / maxRef).coerceIn(0f, 1f)
    val dFraction = (dinnerCal / maxRef).coerceIn(0f, 1f)
    val sFraction = (snackCal / maxRef).coerceIn(0f, 1f)

    Canvas(modifier = modifier.clip(RoundedCornerShape(8.dp))) {
        val width = size.width
        val height = size.height

        // 1. Neutral background
        drawRect(color = Color(0xFFE2E8F0), size = size)

        var currentX = 0f

        // Breakfast segment
        if (bFraction > 0f) {
            val segWidth = width * bFraction
            drawRect(
                color = Color(MealType.BREAKFAST.colorHex),
                topLeft = Offset(currentX, 0f),
                size = Size(segWidth, height)
            )
            currentX += segWidth
        }

        // Lunch segment
        if (lFraction > 0f) {
            val segWidth = width * lFraction
            drawRect(
                color = Color(MealType.LUNCH.colorHex),
                topLeft = Offset(currentX, 0f),
                size = Size(segWidth, height)
            )
            currentX += segWidth
        }

        // Dinner segment
        if (dFraction > 0f) {
            val segWidth = width * dFraction
            drawRect(
                color = Color(MealType.DINNER.colorHex),
                topLeft = Offset(currentX, 0f),
                size = Size(segWidth, height)
            )
            currentX += segWidth
        }

        // Snack segment
        if (sFraction > 0f) {
            val segWidth = width * sFraction
            drawRect(
                color = Color(MealType.SNACK.colorHex),
                topLeft = Offset(currentX, 0f),
                size = Size(segWidth, height)
            )
            currentX += segWidth
        }
    }
}

@Composable
private fun MealLegendPill(
    emoji: String,
    label: String,
    calories: Int,
    color: Color
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = color.copy(alpha = 0.12f),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.35f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Text(
                text = "$emoji $label: ${calories} kcal",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun MacroProgressCard(
    title: String,
    grams: Double,
    targetGrams: Double,
    color: Color,
    iconEmoji: String,
    modifier: Modifier = Modifier
) {
    val progress = (grams / targetGrams).coerceIn(0.0, 1.0).toFloat()

    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = color.copy(alpha = 0.08f)
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = iconEmoji, fontSize = 14.sp)
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }
            Text(
                text = "${String.format("%.1f", grams)}g",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = color,
                trackColor = Color(0xFFE2E8F0)
            )
        }
    }
}

@Composable
private fun LoggedMealRow(
    item: LoggedMealItem,
    isBn: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        onClick = onEdit,
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
        ),
        modifier = Modifier.testTag("logged_meal_row_${item.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.mealType.iconEmoji,
                    fontSize = 18.sp
                )
                Column {
                    Text(
                        text = if (isBn) item.foodNameBn else item.foodNameEn,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${item.grams}g • ${item.calories} kcal",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier
                        .size(28.dp)
                        .testTag("edit_meal_button_${item.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Meal",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(28.dp)
                        .testTag("delete_meal_button_${item.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Remove Meal",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun GoalSetterDialog(
    currentGoal: Int,
    isBn: Boolean,
    onDismiss: () -> Unit,
    onSave: (Int) -> Unit
) {
    var goalText by remember { mutableStateOf(currentGoal.toString()) }
    val presets = listOf(1500, 1800, 2000, 2200, 2500)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (isBn) "দৈনিক ক্যালোরি লক্ষ্য নির্ধারণ" else "Set Daily Calorie Target",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(
                    text = if (isBn) "আপনার কাঙ্ক্ষিত দৈনিক ক্যালোরি মাত্রা পছন্দ করুন বা লিখুন:"
                    else "Choose a recommended goal preset or enter custom daily calories:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Presets FlowRow
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    presets.forEach { preset ->
                        FilterChip(
                            selected = goalText == preset.toString(),
                            onClick = { goalText = preset.toString() },
                            label = { Text("$preset kcal") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }

                OutlinedTextField(
                    value = goalText,
                    onValueChange = { input ->
                        val digits = input.filter { it.isDigit() }.take(5)
                        goalText = digits
                    },
                    label = { Text(if (isBn) "ক্যালোরি লক্ষ্য (kcal)" else "Calorie Goal (kcal)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("custom_goal_input")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val parsed = goalText.toIntOrNull() ?: currentGoal
                    onSave(parsed)
                },
                modifier = Modifier.testTag("save_goal_button")
            ) {
                Text(if (isBn) "সংরক্ষণ করুন" else "Save Target")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(if (isBn) "বাতিল" else "Cancel")
            }
        }
    )
}

@Composable
private fun StatusPill(
    progress: Float,
    isExceeded: Boolean,
    isBn: Boolean
) {
    val (label, bg, contentColor) = when {
        isExceeded -> Triple(
            if (isBn) "লক্ষ্য অতিক্রম" else "Over Goal",
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.onErrorContainer
        )
        progress >= 0.85f -> Triple(
            if (isBn) "লক্ষ্যের কাছাকাছি" else "Near Target",
            Color(0xFFFEF3C7),
            Color(0xFF92400E)
        )
        progress >= 0.3f -> Triple(
            if (isBn) "অন ট্র‍্যাক" else "On Track",
            Color(0xFFD1FAE5),
            Color(0xFF065F46)
        )
        else -> Triple(
            if (isBn) "শুরুর পর্যায়" else "Started",
            MaterialTheme.colorScheme.secondaryContainer,
            MaterialTheme.colorScheme.onSecondaryContainer
        )
    }

    Surface(
        shape = RoundedCornerShape(10.dp),
        color = bg
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(contentColor)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = contentColor,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun CompactMealSubtotalTile(
    emoji: String,
    label: String,
    calories: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(text = emoji, fontSize = 14.sp)
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$calories kcal",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = if (calories > 0) color else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                fontSize = 10.sp
            )
        }
    }
}
