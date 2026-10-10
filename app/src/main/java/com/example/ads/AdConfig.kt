package com.example.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

/**
 * Centralized Google Mobile Ads (AdMob) configuration and manager for CalorieSnap.
 *
 * User provided AdMob IDs:
 * - App ID: ca-app-pub-5222053984568989~8281700642
 * - Banner 1 Ad Unit ID: ca-app-pub-5222053984568989/8421301444
 * - Interstitial 1 Ad Unit ID: ca-app-pub-5222053984568989/7282470439
 */
object AdConfig {
    const val TAG = "CalorieSnapAds"

    const val ADMOB_APP_ID = "ca-app-pub-5222053984568989~8281700642"
    const val BANNER_AD_UNIT_ID = "ca-app-pub-5222053984568989/8421301444"
    const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-5222053984568989/7282470439"

    var isInitialized = false

    fun initialize(context: Context) {
        if (!isInitialized) {
            try {
                MobileAds.initialize(context) { initializationStatus ->
                    isInitialized = true
                    Log.d(TAG, "AdMob MobileAds initialized: $initializationStatus")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error initializing MobileAds", e)
            }
        }
    }
}

/**
 * Manager to preload and display Interstitial ads complying with AdMob UX policies.
 * Interstitial ads are shown at natural transition points (such as tapping Share or browsing).
 */
class InterstitialAdManager(private val context: Context) {
    private var interstitialAd: InterstitialAd? = null
    private var isLoading = false

    fun loadAd() {
        if (interstitialAd != null || isLoading) return
        isLoading = true

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            AdConfig.INTERSTITIAL_AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isLoading = false
                    Log.d(AdConfig.TAG, "Interstitial ad loaded successfully.")

                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            interstitialAd = null
                            loadAd() // Preload next interstitial
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            interstitialAd = null
                            isLoading = false
                            Log.e(AdConfig.TAG, "Interstitial failed to show: ${adError.message}")
                            loadAd()
                        }
                    }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    interstitialAd = null
                    isLoading = false
                    Log.w(AdConfig.TAG, "Interstitial failed to load: ${loadAdError.message}")
                }
            }
        )
    }

    fun showAd(activity: Activity, onAdDismissedOrSkipped: () -> Unit) {
        val currentAd = interstitialAd
        if (currentAd != null) {
            currentAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    loadAd()
                    onAdDismissedOrSkipped()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    interstitialAd = null
                    isLoading = false
                    Log.e(AdConfig.TAG, "Failed to show interstitial: ${adError.message}")
                    loadAd()
                    onAdDismissedOrSkipped()
                }
            }
            currentAd.show(activity)
        } else {
            // If ad is not ready, proceed immediately without blocking user experience
            onAdDismissedOrSkipped()
            loadAd()
        }
    }
}

/**
 * Jetpack Compose wrapper for Google AdMob Banner Ad View.
 */
@Composable
fun AdMobBanner(
    modifier: Modifier = Modifier,
    adUnitId: String = AdConfig.BANNER_AD_UNIT_ID
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        factory = { ctx ->
            AdView(ctx).apply {
                setAdSize(AdSize.BANNER)
                this.adUnitId = adUnitId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}
