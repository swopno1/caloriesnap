package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = EmeraldPrimaryDark,
    onPrimary = EmeraldOnPrimaryDark,
    primaryContainer = EmeraldPrimaryContainerDark,
    onPrimaryContainer = EmeraldOnPrimaryContainerDark,
    secondary = MintSecondaryDark,
    onSecondary = MintOnSecondaryDark,
    secondaryContainer = MintSecondaryContainerDark,
    onSecondaryContainer = MintOnSecondaryContainerDark,
    tertiary = WarmAmberTertiaryDark,
    onTertiary = WarmAmberOnTertiaryDark,
    tertiaryContainer = WarmAmberTertiaryContainerDark,
    onTertiaryContainer = WarmAmberOnTertiaryContainerDark,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = EmeraldPrimaryLight,
    onPrimary = EmeraldOnPrimaryLight,
    primaryContainer = EmeraldPrimaryContainerLight,
    onPrimaryContainer = EmeraldOnPrimaryContainerLight,
    secondary = MintSecondaryLight,
    onSecondary = MintOnSecondaryLight,
    secondaryContainer = MintSecondaryContainerLight,
    onSecondaryContainer = MintOnSecondaryContainerLight,
    tertiary = WarmAmberTertiaryLight,
    onTertiary = WarmAmberOnTertiaryLight,
    tertiaryContainer = WarmAmberTertiaryContainerLight,
    onTertiaryContainer = WarmAmberOnTertiaryContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight
)

@Composable
fun CalorieSnapTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set to false to preserve brand emerald signature design
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
