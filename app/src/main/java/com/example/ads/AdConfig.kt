package com.example.ads

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

/**
 * Centralized Google Mobile Ads (AdMob) configuration and manager for CalorieSnap.
 *
 * Configured AdMob IDs:
 * - App ID: ca-app-pub-5222053984568989~8281700642
 * - Banner 1 Ad Unit ID: ca-app-pub-5222053984568989/8421301444
 * - Interstitial 1 Ad Unit ID: ca-app-pub-5222053984568989/7282470439
 */
object AdConfig {
    const val TAG = "CalorieSnapAds"

    const val ADMOB_APP_ID = "ca-app-pub-5222053984568989~8281700642"
    const val BANNER_AD_UNIT_ID = "ca-app-pub-5222053984568989/8421301444"
    const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-5222053984568989/7282470439"

    /**
     * Checks if the app is executing inside an Android emulator environment.
     * In headless cloud emulator environments without physical GPU pass-through,
     * Chromium/WebView graphics probing triggers Mesa drm rendernode lookup errors.
     * We safely provide lightweight test ad UI in emulator environments.
     */
    val isEmulator: Boolean by lazy {
        Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("emulator")
                || Build.BRAND.startsWith("generic")
                || Build.DEVICE.startsWith("generic")
    }

    var isInitialized = false
        private set

    fun initialize(context: Context, onInitialized: (() -> Unit)? = null) {
        if (isEmulator) {
            Log.d(TAG, "Running on emulator environment - using lightweight test ad mode to prevent Mesa DRM rendernode query errors.")
            isInitialized = true
            onInitialized?.invoke()
            return
        }

        if (!isInitialized) {
            try {
                // Configure test device ID for emulator environments to avoid ad services & header errors
                val testDeviceIds = listOf(AdRequest.DEVICE_ID_EMULATOR)
                val configuration = RequestConfiguration.Builder()
                    .setTestDeviceIds(testDeviceIds)
                    .build()
                MobileAds.setRequestConfiguration(configuration)

                MobileAds.initialize(context) { initializationStatus ->
                    isInitialized = true
                    Log.d(TAG, "AdMob MobileAds initialized: $initializationStatus")
                    onInitialized?.invoke()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error initializing MobileAds", e)
                onInitialized?.invoke()
            }
        } else {
            onInitialized?.invoke()
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
        if (AdConfig.isEmulator) return
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
                    Log.w(AdConfig.TAG, "Interstitial ad failed to load: ${loadAdError.message}")
                }
            }
        )
    }

    fun showAd(activity: Activity, onAdDismissedOrSkipped: () -> Unit) {
        if (AdConfig.isEmulator) {
            onAdDismissedOrSkipped()
            return
        }

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
 * Displays lightweight preview in emulator environments to prevent Mesa DRM rendernode query errors.
 */
@Composable
fun AdMobBanner(
    modifier: Modifier = Modifier,
    adUnitId: String = AdConfig.BANNER_AD_UNIT_ID
) {
    if (AdConfig.isEmulator) {
        // Safe, lightweight test ad placeholder that prevents Mesa GPU DRM rendernode errors on cloud emulators
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("admob_banner_placeholder"),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "Ad",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = "Google AdMob • Test Banner (320x50)",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "CalorieSnap",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                )
            }
        }
    } else {
        AndroidView(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            factory = { ctx ->
                AdView(ctx).apply {
                    setLayerType(android.view.View.LAYER_TYPE_SOFTWARE, null)
                    setAdSize(AdSize.BANNER)
                    this.adUnitId = adUnitId
                    adListener = object : com.google.android.gms.ads.AdListener() {
                        override fun onAdLoaded() {
                            Log.d(AdConfig.TAG, "Banner ad loaded successfully")
                        }
                        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                            Log.w(AdConfig.TAG, "Banner ad failed to load: ${loadAdError.message}")
                        }
                    }
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}
