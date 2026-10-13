package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LoggedMealItem
import com.example.model.MealType

/**
 * Data structure for quick extra intake presets (tea, snacks, chips, biscuits, etc.)
 */
data class ExtraIntakePreset(
    val emoji: String,
    val nameEn: String,
    val nameBn: String,
    val defaultCalories: Int,
    val defaultGrams: Int,
    val defaultMealType: MealType = MealType.SNACK
)

val PopularExtraIntakePresets = listOf(
    ExtraIntakePreset("☕", "Dudh Cha / Sweet Milk Tea", "দুধ চা (চিনিসহ)", 100, 120, MealType.SNACK),
    ExtraIntakePreset("🍵", "Rong Cha / Black Tea", "রং চা (চিনি ছাড়া)", 15, 150, MealType.SNACK),
    ExtraIntakePreset("🥟", "Shingara / Samosa", "সিঙ্গাড়া / সমুচা", 160, 60, MealType.SNACK),
    ExtraIntakePreset("🥔", "Potato Chips / Crisps", "আলুর চিপস", 150, 30, MealType.SNACK),
    ExtraIntakePreset("🍪", "Biscuits / Cookies", "বিস্কুট / কুকিজ", 90, 25, MealType.SNACK),
    ExtraIntakePreset("🥜", "Chanachur / Mixed Nuts", "চানাচুর / বাদাম", 180, 40, MealType.SNACK),
    ExtraIntakePreset("🥤", "Soft Drink / Cola", "কোল্ড ড্রিংকস", 140, 250, MealType.SNACK),
    ExtraIntakePreset("☕", "Instant Coffee with Milk", "দুধ কফি", 95, 150, MealType.SNACK),
    ExtraIntakePreset("🫓", "Dalpuri / Shingara", "ডালপুরি", 175, 60, MealType.SNACK),
    ExtraIntakePreset("🍿", "Jhalmuri / Muri", "ঝালমুড়ি", 120, 40, MealType.SNACK)
)

/**
 * Dialog to easily edit an existing daily meal item's name, portion, calories,
 * and meal category so it updates actual calorie calculations immediately.
 */
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditMealItemDialog(
    item: LoggedMealItem,
    isBn: Boolean,
    onDismiss: () -> Unit,
    onSave: (LoggedMealItem) -> Unit
) {
    var nameText by remember { mutableStateOf(if (isBn) item.foodNameBn else item.foodNameEn) }
    var caloriesText by remember { mutableStateOf(item.calories.toString()) }
    var gramsText by remember { mutableStateOf(item.grams.toString()) }
    var selectedMealType by remember { mutableStateOf(item.mealType) }

    fun adjustCalories(delta: Int) {
        val current = caloriesText.toIntOrNull() ?: item.calories
        val updated = (current + delta).coerceAtLeast(0)
        caloriesText = updated.toString()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = if (isBn) "খাবার এন্ট্রি সম্পাদনা করুন" else "Edit Meal Entry",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = if (isBn)
                        "ক্যালোরি বা পরিমাপ পরিবর্তন করুন যাতে দৈনিক ক্যালোরি গণনা সঠিক থাকে:"
                    else
                        "Adjust calories or serving size to reflect actual daily intake:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Food Name Field
                OutlinedTextField(
                    value = nameText,
                    onValueChange = { nameText = it },
                    label = { Text(if (isBn) "খাবারের নাম" else "Food Item Name") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("edit_meal_name_input")
                )

                // Calorie Input with Quick Stepper Buttons
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedTextField(
                        value = caloriesText,
                        onValueChange = { input ->
                            caloriesText = input.filter { it.isDigit() }.take(5)
                        },
                        label = { Text(if (isBn) "ক্যালোরি (kcal)" else "Calories (kcal)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("edit_meal_calories_input")
                    )

                    // Quick Stepper Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(-50, -25, 25, 50).forEach { delta ->
                            Surface(
                                onClick = { adjustCalories(delta) },
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier.padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (delta > 0) "+$delta" else "$delta",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (delta > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }

                // Grams / Portion Field
                OutlinedTextField(
                    value = gramsText,
                    onValueChange = { input ->
                        gramsText = input.filter { it.isDigit() }.take(5)
                    },
                    label = { Text(if (isBn) "পরিমাণ / গ্রাম (g)" else "Portion Size (grams)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("edit_meal_grams_input")
                )

                // Meal Category Selector Chips
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = if (isBn) "খাবারের সময় (Meal Type):" else "Meal Category:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        MealType.values().forEach { mealType ->
                            FilterChip(
                                selected = selectedMealType == mealType,
                                onClick = { selectedMealType = mealType },
                                label = {
                                    Text("${mealType.iconEmoji} ${if (isBn) mealType.titleBn else mealType.titleEn}")
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val parsedCal = caloriesText.toIntOrNull() ?: item.calories
                    val parsedGrams = gramsText.toIntOrNull() ?: item.grams
                    val updated = item.copy(
                        foodNameEn = nameText.ifBlank { item.foodNameEn },
                        foodNameBn = nameText.ifBlank { item.foodNameBn },
                        calories = parsedCal,
                        grams = parsedGrams,
                        carbsGrams = ((parsedCal * 0.50) / 4.0),
                        proteinGrams = ((parsedCal * 0.20) / 4.0),
                        fatGrams = ((parsedCal * 0.30) / 9.0),
                        mealType = selectedMealType
                    )
                    onSave(updated)
                },
                modifier = Modifier.testTag("save_edited_meal_button")
            ) {
                Text(if (isBn) "পরিবর্তন সংরক্ষণ করুন" else "Save Changes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(if (isBn) "বাতিল" else "Cancel")
            }
        }
    )
}

/**
 * Dialog to quickly log extra daily food intakes such as tea, coffee, snacks, chips, biscuits, etc.
 * Features 1-tap quick presets and custom entries.
 */
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun QuickExtraIntakeDialog(
    isBn: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (nameEn: String, nameBn: String, calories: Int, grams: Int, mealType: MealType) -> Unit
) {
    var selectedPreset by remember { mutableStateOf<ExtraIntakePreset?>(PopularExtraIntakePresets.first()) }
    var customName by remember { mutableStateOf(PopularExtraIntakePresets.first().nameEn) }
    var customCalories by remember { mutableStateOf(PopularExtraIntakePresets.first().defaultCalories.toString()) }
    var customGrams by remember { mutableStateOf(PopularExtraIntakePresets.first().defaultGrams.toString()) }
    var selectedMealType by remember { mutableStateOf(MealType.SNACK) }

    fun applyPreset(preset: ExtraIntakePreset) {
        selectedPreset = preset
        customName = if (isBn) preset.nameBn else preset.nameEn
        customCalories = preset.defaultCalories.toString()
        customGrams = preset.defaultGrams.toString()
        selectedMealType = preset.defaultMealType
    }

    fun adjustCalories(delta: Int) {
        val current = customCalories.toIntOrNull() ?: 100
        val updated = (current + delta).coerceAtLeast(0)
        customCalories = updated.toString()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Fastfood,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = if (isBn) "অতিরিক্ত খাবার / নাস্তা যোগ করুন" else "Add Extra Intake (Tea, Snacks, Chips)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = if (isBn)
                        "চা, চিপস, সিঙ্গাড়া বা যেকোনো অতিরিক্ত নাস্তা সহজে যোগ করতে নিচের অপশন পছন্দ করুন:"
                    else
                        "Select a quick preset or type custom tea, snacks, biscuits, or chips:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Quick Presets FlowRow
                Text(
                    text = if (isBn) "জনপ্রিয় অপশনসমূহ (১-ট্যাপ):" else "Popular Quick Presets:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    PopularExtraIntakePresets.forEach { preset ->
                        val isSelected = selectedPreset?.nameEn == preset.nameEn
                        FilterChip(
                            selected = isSelected,
                            onClick = { applyPreset(preset) },
                            label = {
                                Text(
                                    text = "${preset.emoji} ${if (isBn) preset.nameBn else preset.nameEn} (${preset.defaultCalories} kcal)",
                                    fontSize = 11.sp
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                // Name input
                OutlinedTextField(
                    value = customName,
                    onValueChange = {
                        customName = it
                        selectedPreset = null
                    },
                    label = { Text(if (isBn) "খাবার / নাস্তার নাম" else "Item Name") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("extra_intake_name_input")
                )

                // Calories input with steppers
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedTextField(
                        value = customCalories,
                        onValueChange = { input ->
                            customCalories = input.filter { it.isDigit() }.take(5)
                        },
                        label = { Text(if (isBn) "ক্যালোরি (kcal)" else "Calories (kcal)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("extra_intake_calories_input")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(-50, -20, 20, 50).forEach { delta ->
                            Surface(
                                onClick = { adjustCalories(delta) },
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier.padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (delta > 0) "+$delta" else "$delta",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (delta > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }

                // Serving / Grams input
                OutlinedTextField(
                    value = customGrams,
                    onValueChange = { input ->
                        customGrams = input.filter { it.isDigit() }.take(5)
                    },
                    label = { Text(if (isBn) "আনুমানিক পরিমাণ / গ্রাম (g)" else "Portion Size (g / ml)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("extra_intake_grams_input")
                )

                // Meal category
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = if (isBn) "খাবারের ক্যাটাগরি:" else "Log As Meal:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        MealType.values().forEach { mealType ->
                            FilterChip(
                                selected = selectedMealType == mealType,
                                onClick = { selectedMealType = mealType },
                                label = {
                                    Text("${mealType.iconEmoji} ${if (isBn) mealType.titleBn else mealType.titleEn}")
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val parsedCal = customCalories.toIntOrNull() ?: 100
                    val parsedGrams = customGrams.toIntOrNull() ?: 50
                    val nameEn = customName.ifBlank { selectedPreset?.nameEn ?: "Snack" }
                    val nameBn = selectedPreset?.nameBn ?: nameEn
                    onConfirm(nameEn, nameBn, parsedCal, parsedGrams, selectedMealType)
                },
                modifier = Modifier.testTag("confirm_extra_intake_button")
            ) {
                Text(if (isBn) "লগে যোগ করুন" else "Add to Daily Log")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(if (isBn) "বাতিল" else "Cancel")
            }
        }
    )
}
