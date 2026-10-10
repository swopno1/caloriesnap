# CalorieSnap

**CalorieSnap** is a fast, lightweight Android application designed to help users quickly estimate the calorie content of everyday foods and share results instantly.

Built with modern Android engineering standards by **ViveScript Solutions LLC**.

---

## Overview

- **App Name:** CalorieSnap
- **Package ID:** `com.vivescriptsolutions.caloriesnap`
- **Publisher:** ViveScript Solutions LLC (https://www.vivescriptsolutions.com/)
- **Target Market:** Global, with comprehensive culinary coverage for Bangladesh, India, Pakistan, and South Asia.
- **Primary Languages:** English & বাংলা (Bangla)
- **Architecture:** MVVM with Jetpack Compose & StateFlow
- **License:** MIT License (Source Code) + Proprietary Branding Notice

---

## Key Features

1. **Comprehensive Offline Food Database:**
   - Pre-loaded with staple Bangladeshi & South Asian dishes (Sada Bhat, Atta Roti, Paratha, Chicken Biryani, Kacchi Biryani, Bhuna Khichuri, Plain Polao, Ilish Macher Jhol, Rui Kalia, Dim Bhuna, Boiled Eggs, Egg Omelette, Aloo Bhaji, Aloo Bhorta, Singara, Somosa, Fuchka, Chotpoti, Jhal Muri, Roshogolla, Mishti Doi, Dudh Cha, Borhani) as well as everyday fruits, vegetables, drinks, and global favorites (Pizza, Burgers, Chowmein, Shawarma).
2. **Instant Calorie & Macro Recalculation:**
   - Pre-configured standard serving sizes (cups, plates, pieces, slices).
   - Quantity stepper multiplier (0.5x to 6x).
   - Custom grams mode for exact weight entries with input boundary validation.
   - Real-time estimates for Calories (kcal), Carbohydrates (g), Protein (g), and Fat (g).
3. **One-Tap Social Sharing:**
   - Generates a formatted summary ready to share directly to WhatsApp, Facebook, Instagram Stories, TikTok, or SMS with one tap.
4. **Dual Language Support:**
   - Seamless instant toggle between English and বাংলা (Bangla).
5. **Fast & Privacy-Conscious:**
   - Zero login or account creation required.
   - Core food database works completely offline on device.
   - No personal names, passwords, or emails collected.
6. **Integrated Google AdMob:**
   - Banner 1: Non-intrusive banner ad placed in bottom bar.
   - Interstitial 1: Displayed on natural transitions during social share actions.

---

## Technology Stack

- **Language:** Kotlin 2.2.10
- **UI Toolkit:** Jetpack Compose (Material 3)
- **Design System:** Custom Emerald & Mint fresh nutritional theme with dark theme support
- **State Management:** AndroidX ViewModel & Kotlin Coroutines StateFlow
- **Monetization:** Google Mobile Ads (AdMob) SDK 23.6.0
- **Build System:** Gradle Kotlin DSL (`.gradle.kts`) with Version Catalog (`libs.versions.toml`)
- **Compatibility:** Min SDK 24 (Android 7.0) | Target SDK 36 (Android 15+)

---

## Requirements

- Android Studio Ladybug / Koala / Meerkat or later
- JDK 17 or JDK 21
- Android SDK 36

---

## Installation & Build Instructions

### Clone & Open
```bash
git clone https://github.com/vivescriptsolutions/caloriesnap.git
cd caloriesnap
```

### Build Debug APK
```bash
gradle :app:assembleDebug
```

### Build Production Release AAB
```bash
gradle :app:buildReleasePreBundle
# Packaged into /production_build/caloriesnap-release.aab
```

### Run Unit Tests
```bash
gradle :app:testDebugUnitTest
```

---

## Privacy & Data Safety

- **Data Collection:** CalorieSnap directly collects 0 bytes of personal user data.
- **Advertising Identifiers:** Google Mobile Ads SDK may process standard device identifiers and network telemetry for ad delivery and fraud detection under Google's Privacy Policy.
- **Network Permissions:** `INTERNET` and `ACCESS_NETWORK_STATE` for AdMob ad requests.

---

## Licensing & Copyright

**© 2026 ViveScript Solutions LLC.**

The source code is licensed under the **MIT License**. See the `LICENSE` file for details.

*Proprietary Branding Notice:* The app name "CalorieSnap", logo, application icons, screenshots, trademarks, and associated branding are proprietary property of **ViveScript Solutions LLC** and are not licensed under the MIT License.
