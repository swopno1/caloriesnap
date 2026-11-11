package com.example.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.BuildConfig
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform

/**
 * Google User Messaging Platform (UMP) consent flow, required before
 * initializing Mobile Ads for users in regions with consent requirements
 * (EEA/UK/Switzerland, US regulated states). Outside those regions the form
 * never appears and [onComplete] fires immediately with the current
 * [canRequestAds] value.
 *
 * Debug builds force EEA geography so the form can be verified on an
 * emulator; consent failures always fall through to [onComplete] so a
 * consent outage can never hang app startup.
 */
object ConsentManager {
    private const val TAG = "ConsentManager"

    fun gatherConsent(activity: Activity, onComplete: (canRequestAds: Boolean) -> Unit) {
        val debugSettings = if (BuildConfig.DEBUG) {
            ConsentDebugSettings.Builder(activity)
                .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                .build()
        } else {
            null
        }
        val paramsBuilder = ConsentRequestParameters.Builder()
            .setTagForUnderAgeOfConsent(false)
        if (debugSettings != null) {
            paramsBuilder.setConsentDebugSettings(debugSettings)
        }
        val consentInfo = UserMessagingPlatform.getConsentInformation(activity)
        consentInfo.requestConsentInfoUpdate(
            activity,
            paramsBuilder.build(),
            {
                if (consentInfo.isConsentFormAvailable) {
                    loadAndShowIfRequired(activity, onComplete)
                } else {
                    onComplete(consentInfo.canRequestAds())
                }
            },
            { error ->
                Log.w(TAG, "Consent info update failed: ${error.message}")
                // Debug builds use demo units only, so a consent outage must
                // not hide the ad UI under test; release stays gated.
                onComplete(BuildConfig.DEBUG || consentInfo.canRequestAds())
            }
        )
    }

    private fun loadAndShowIfRequired(activity: Activity, onComplete: (canRequestAds: Boolean) -> Unit) {
        val consentInfo = UserMessagingPlatform.getConsentInformation(activity)
        UserMessagingPlatform.loadConsentForm(
            activity,
            { form ->
                if (consentInfo.consentStatus == ConsentInformation.ConsentStatus.REQUIRED) {
                    form.show(activity) {
                        // Dismissed: re-check, a second form may be required.
                        loadAndShowIfRequired(activity, onComplete)
                    }
                } else {
                    onComplete(consentInfo.canRequestAds())
                }
            },
            { error ->
                Log.w(TAG, "Consent form load failed: ${error.message}")
                onComplete(BuildConfig.DEBUG || consentInfo.canRequestAds())
            }
        )
    }

    /**
     * Checks if a privacy options form (e.g. US State regulations opt-out or GDPR re-consent)
     * is required to be available to the user.
     */
    fun isPrivacyOptionsRequired(context: Context): Boolean {
        val consentInfo = UserMessagingPlatform.getConsentInformation(context)
        return consentInfo.privacyOptionsRequirementStatus ==
            ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED
    }

    /**
     * Opens the Privacy Options form so users can review or change their privacy choices
     * (e.g. US State regulations opt-out, GDPR consent revision).
     */
    fun showPrivacyOptions(activity: Activity, onDismiss: () -> Unit = {}) {
        UserMessagingPlatform.showPrivacyOptionsForm(activity) { formError ->
            if (formError != null) {
                Log.w(TAG, "Error displaying privacy options form: ${formError.message}")
            }
            onDismiss()
        }
    }
}
