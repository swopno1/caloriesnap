package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.ads.AdConfig
import com.example.ads.InterstitialAdManager
import com.example.ui.screens.CalorieCalculatorScreen
import com.example.ui.screens.UserSettingsScreen
import com.example.ui.theme.CalorieSnapTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.CalorieCalculatorViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CalorieCalculatorViewModel by viewModels()
    private lateinit var interstitialAdManager: InterstitialAdManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Create Interstitial manager (lazy loading on user action instead of aggressive launch loading)
        interstitialAdManager = InterstitialAdManager(this)

        // Initialize Google Mobile Ads SDK with App ID
        AdConfig.initialize(this)

        setContent {
            CalorieSnapTheme {
                val uiState by viewModel.uiState.collectAsState()
                when (uiState.currentScreen) {
                    AppScreen.CALCULATOR -> {
                        CalorieCalculatorScreen(
                            viewModel = viewModel,
                            interstitialAdManager = interstitialAdManager,
                            onOpenSettings = { viewModel.navigateTo(AppScreen.SETTINGS) }
                        )
                    }
                    AppScreen.SETTINGS -> {
                        UserSettingsScreen(
                            viewModel = viewModel,
                            onNavigateBack = { viewModel.navigateBack() }
                        )
                    }
                }
            }
        }
    }
}
