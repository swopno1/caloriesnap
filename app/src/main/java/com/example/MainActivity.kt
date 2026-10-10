package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ads.AdConfig
import com.example.ads.InterstitialAdManager
import com.example.ui.screens.CalorieCalculatorScreen
import com.example.ui.theme.CalorieSnapTheme
import com.example.viewmodel.CalorieCalculatorViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CalorieCalculatorViewModel by viewModels()
    private lateinit var interstitialAdManager: InterstitialAdManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Google Mobile Ads SDK with provided App ID
        AdConfig.initialize(this)

        interstitialAdManager = InterstitialAdManager(this)
        interstitialAdManager.loadAd()

        setContent {
            CalorieSnapTheme {
                CalorieCalculatorScreen(
                    viewModel = viewModel,
                    onTriggerShareWithInterstitial = { onCompleteAction ->
                        // Show interstitial on natural transition (Share action) then execute share
                        interstitialAdManager.showAd(this@MainActivity) {
                            onCompleteAction()
                        }
                    }
                )
            }
        }
    }
}
