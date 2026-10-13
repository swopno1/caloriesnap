package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.CalorieCalculatorViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSettingsScreen(
    viewModel: CalorieCalculatorViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Handle Android system back press
    BackHandler {
        onNavigateBack()
    }

    val uiState by viewModel.uiState.collectAsState()
    val isBn = uiState.isBanglaLanguage
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var goalInputText by remember(uiState.dailyCalorieGoal) {
        mutableStateOf(uiState.dailyCalorieGoal.toString())
    }
    var showClearLogDialog by remember { mutableStateOf(false) }

    fun commitGoal(newGoal: Int) {
        val safe = newGoal.coerceIn(500, 10000)
        goalInputText = safe.toString()
        viewModel.setDailyGoal(safe)
        viewModel.triggerGoalSavedFeedback()
        keyboardController?.hide()
        scope.launch {
            val msg = if (isBn) "দৈনিক ক্যালোরি লক্ষ্য সফলভাবে সংরক্ষিত হয়েছে ($safe kcal)!"
            else "Daily calorie goal saved to DataStore preferences ($safe kcal)!"
            snackbarHostState.showSnackbar(msg)
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("settings_screen"),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (isBn) "ব্যবহারকারী সেটিংস" else "User Settings",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isBn) "দৈনিক ক্যালোরি ও লক্ষ্য পছন্দসমূহ" else "Daily Calorie & Goal Preferences",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("settings_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = if (isBn) "ফিরে যান" else "Navigate Back"
                        )
                    }
                },
                actions = {
                    // Reset to default button
                    IconButton(
                        onClick = {
                            viewModel.resetDailyGoalToDefault()
                            scope.launch {
                                val msg = if (isBn) "ডিফল্ট লক্ষ্য (২০০০ ক্যালোরি) পুনঃনির্ধারিত হয়েছে"
                                else "Reset to default daily goal (2000 kcal)"
                                snackbarHostState.showSnackbar(msg)
                            }
                        },
                        modifier = Modifier.testTag("reset_goal_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.RestartAlt,
                            contentDescription = if (isBn) "ডিফল্ট পুনরুদ্ধার" else "Reset to Default"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
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
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                // Success banner animated visibility
                item {
                    AnimatedVisibility(
                        visible = uiState.isGoalSavedFeedback,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("saved_feedback_card"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF10B981).copy(alpha = 0.15f)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color(0xFF10B981)
                                )
                                Text(
                                    text = if (isBn) "ডাটা স্টোর প্রেফারেন্সে সংরক্ষিত হয়েছে!"
                                    else "Preferences saved permanently to DataStore!",
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF065F46),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }

                // 1. Current Goal Hero Card
                item {
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("current_goal_hero_card"),
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
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
                                            imageVector = Icons.Default.LocalFireDepartment,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                    Text(
                                        text = if (isBn) "বর্তমান দৈনিক লক্ষ্য" else "Current Daily Goal",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.secondaryContainer
                                ) {
                                    Text(
                                        text = "DataStore Sync ✓",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            // Big number display
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        Brush.linearGradient(
                                            listOf(
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0.08f)
                                            )
                                        )
                                    )
                                    .padding(vertical = 20.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "%,d".format(uiState.dailyCalorieGoal),
                                        fontSize = 44.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = if (isBn) "ক্যালোরি / দিন (kcal)" else "Calories / day (kcal)",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                // 2. Goal Adjustment Input & Steppers
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("goal_editor_card"),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = if (isBn) "লক্ষ্য কাস্টমাইজ করুন" else "Customize Daily Goal",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            // Numerical text field
                            OutlinedTextField(
                                value = goalInputText,
                                onValueChange = { input ->
                                    val digitsOnly = input.filter { it.isDigit() }.take(5)
                                    goalInputText = digitsOnly
                                },
                                label = {
                                    Text(if (isBn) "দৈনিক ক্যালোরি মান (kcal)" else "Daily Calorie Value (kcal)")
                                },
                                placeholder = { Text("2000") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        val entered = goalInputText.toIntOrNull() ?: uiState.dailyCalorieGoal
                                        commitGoal(entered)
                                    }
                                ),
                                trailingIcon = {
                                    Text(
                                        text = "kcal",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(end = 12.dp)
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("goal_input_field")
                            )

                            // Quick Stepper Buttons Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                StepperButton(
                                    label = "-100",
                                    onClick = {
                                        val curr = goalInputText.toIntOrNull() ?: uiState.dailyCalorieGoal
                                        commitGoal(curr - 100)
                                    }
                                )
                                StepperButton(
                                    label = "-50",
                                    onClick = {
                                        val curr = goalInputText.toIntOrNull() ?: uiState.dailyCalorieGoal
                                        commitGoal(curr - 50)
                                    }
                                )
                                StepperButton(
                                    label = "+50",
                                    onClick = {
                                        val curr = goalInputText.toIntOrNull() ?: uiState.dailyCalorieGoal
                                        commitGoal(curr + 50)
                                    }
                                )
                                StepperButton(
                                    label = "+100",
                                    onClick = {
                                        val curr = goalInputText.toIntOrNull() ?: uiState.dailyCalorieGoal
                                        commitGoal(curr + 100)
                                    }
                                )
                            }

                            // Save Goal Button
                            Button(
                                onClick = {
                                    val entered = goalInputText.toIntOrNull() ?: uiState.dailyCalorieGoal
                                    commitGoal(entered)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .testTag("save_goal_button"),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Save,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isBn) "লক্ষ্য সংরক্ষণ করুন (DataStore)" else "Save Goal to Preferences",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }

                // 3. Recommended Dietary Goal Presets
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("dietary_presets_card"),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Text(
                                text = if (isBn) "প্রস্তাবিত লক্ষ্য নির্বাচন" else "Recommended Dietary Presets",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isBn) "আপনার স্বাস্থ্য লক্ষ্যের উপর ভিত্তি করে সুবিধাজনক ক্যালোরি প্রোফাইল বেছে নিন:"
                                else "Select a tailored caloric profile matched to your lifestyle & fitness goals:",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            // Preset Options
                            PresetOptionCard(
                                title = if (isBn) "ওজন হ্রাস (Calorie Deficit)" else "Weight Loss (Moderate Deficit)",
                                calories = 1600,
                                description = if (isBn) "ধীর ও সুস্থভাবে মেদ ঝরানোর জন্য উপযুক্ত" else "Ideal for steady, sustainable fat loss with desi diet",
                                icon = Icons.Default.MonitorWeight,
                                isSelected = uiState.dailyCalorieGoal == 1600,
                                testTag = "preset_weight_loss",
                                onSelect = {
                                    viewModel.setDietaryPreset("WEIGHT_LOSS", 1600)
                                    commitGoal(1600)
                                }
                            )

                            PresetOptionCard(
                                title = if (isBn) "ওজন নিয়ন্ত্রণ (Maintenance)" else "Weight Maintenance (Balanced)",
                                calories = 2000,
                                description = if (isBn) "স্বাভাবিক সুস্থ জীবনযাপনের মানসম্মত দৈনিক চাহিদা" else "Recommended standard daily energy intake for South Asian adults",
                                icon = Icons.Default.Speed,
                                isSelected = uiState.dailyCalorieGoal == 2000,
                                testTag = "preset_maintain",
                                onSelect = {
                                    viewModel.setDietaryPreset("MAINTAIN", 2000)
                                    commitGoal(2000)
                                }
                            )

                            PresetOptionCard(
                                title = if (isBn) "পেশী বৃদ্ধি (Muscle Gain)" else "Muscle Building & Strength (Surplus)",
                                calories = 2400,
                                description = if (isBn) "ব্যায়াম বা জিম করার সময় পেশী গঠনে সহায়ক" else "Nutritional support for resistance training & lean mass gain",
                                icon = Icons.Default.FitnessCenter,
                                isSelected = uiState.dailyCalorieGoal == 2400,
                                testTag = "preset_muscle_gain",
                                onSelect = {
                                    viewModel.setDietaryPreset("MUSCLE_GAIN", 2400)
                                    commitGoal(2400)
                                }
                            )

                            PresetOptionCard(
                                title = if (isBn) "ভারী পরিশ্রমী (High Activity)" else "High Athletic / Active Work",
                                calories = 2800,
                                description = if (isBn) "দৌড়ঝাঁপ বা কায়িক পরিশ্রমকারীদের জন্য উপযুক্ত" else "For intense manual labor, sports, or long cardio sessions",
                                icon = Icons.Default.LocalFireDepartment,
                                isSelected = uiState.dailyCalorieGoal == 2800,
                                testTag = "preset_active",
                                onSelect = {
                                    viewModel.setDietaryPreset("HIGH_ACTIVITY", 2800)
                                    commitGoal(2800)
                                }
                            )
                        }
                    }
                }

                // 4. Target Macronutrient Distribution for this Goal
                item {
                    val goal = uiState.dailyCalorieGoal
                    // Macro distributions (50% Carbs, 20% Protein, 30% Fat)
                    val targetCarbsGrams = ((goal * 0.50) / 4.0).roundToInt()
                    val targetProteinGrams = ((goal * 0.20) / 4.0).roundToInt()
                    val targetFatGrams = ((goal * 0.30) / 9.0).roundToInt()

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("macro_distribution_guide_card"),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Text(
                                text = if (isBn) "দৈনিক ম্যাক্রো পুষ্টি বিভাজন গাইড" else "Estimated Daily Macro Distribution",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isBn) "বর্তমান $goal ক্যালোরির উপর ভিত্তি করে সুষম পুষ্টির হিসাব:"
                                else "Balanced dietary distribution based on your $goal kcal goal:",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                MacroPillCard(
                                    modifier = Modifier.weight(1f),
                                    name = if (isBn) "শর্করা" else "Carbs",
                                    percent = "50%",
                                    grams = "${targetCarbsGrams}g",
                                    color = Color(0xFFF59E0B)
                                )
                                MacroPillCard(
                                    modifier = Modifier.weight(1f),
                                    name = if (isBn) "প্রোটিন" else "Protein",
                                    percent = "20%",
                                    grams = "${targetProteinGrams}g",
                                    color = Color(0xFF3B82F6)
                                )
                                MacroPillCard(
                                    modifier = Modifier.weight(1f),
                                    name = if (isBn) "চর্বি" else "Fat",
                                    percent = "30%",
                                    grams = "${targetFatGrams}g",
                                    color = Color(0xFF10B981)
                                )
                            }
                        }
                    }
                }

                // 5. Daily Consumption Log Management
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("log_management_card"),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = if (isBn) "দৈনিক লগ পরিচালনা" else "Daily Tracking Log",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isBn) "আজকের গ্রহণকৃত ক্যালোরি লগ পরিষ্কার করতে পারেন:"
                                else "Manage your logged food items for the day:",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            OutlinedButton(
                                onClick = { showClearLogDialog = true },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("clear_daily_log_button"),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DeleteSweep,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isBn) "আজকের সমস্ত খাবার লগ মুছুন" else "Clear Today's Consumed Log",
                                    color = MaterialTheme.colorScheme.error,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

    // Clear Daily Log Confirmation Dialog
    if (showClearLogDialog) {
        AlertDialog(
            onDismissRequest = { showClearLogDialog = false },
            title = {
                Text(
                    text = if (isBn) "লগ পরিষ্কার করতে চান?" else "Clear Daily Log?",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = if (isBn) "আজকের গ্রহণকৃত সব খাবারের রেকর্ড মুছে ফেলা হবে। আপনি কি নিশ্চিত?"
                    else "All meals logged today will be cleared for a fresh start. Are you sure?"
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearDailyLog()
                        showClearLogDialog = false
                        scope.launch {
                            val msg = if (isBn) "দৈনিক খাবার লগ পরিষ্কার করা হয়েছে" else "Daily meal log cleared!"
                            snackbarHostState.showSnackbar(msg)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(if (isBn) "হ্যাঁ, মুছুন" else "Yes, Clear")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearLogDialog = false }) {
                    Text(if (isBn) "বাতিল" else "Cancel")
                }
            }
        )
    }
}

@Composable
private fun StepperButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier
            .height(44.dp)
            .testTag("stepper_$label"),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun PresetOptionCard(
    title: String,
    calories: Int,
    description: String,
    icon: ImageVector,
    isSelected: Boolean,
    testTag: String,
    onSelect: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)

    Surface(
        onClick = onSelect,
        shape = RoundedCornerShape(16.dp),
        color = containerColor,
        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, borderColor) else null,
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surfaceVariant
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$calories kcal",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun MacroPillCard(
    name: String,
    percent: String,
    grams: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = color.copy(alpha = 0.12f),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = grams,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = percent,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
