# Changelog

All notable changes to the **CalorieSnap** project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [2.0.0] - 2026-11-10

### Added
- **UMP Consent Gating:** Google User Messaging Platform (UMP SDK 4.0.0) consent flow gates all ads; EU/UK users who decline never initialize the Mobile Ads SDK. Covers both European regulations and US state regulations AdMob messages.
- **Ad Privacy Options entry point:** Settings exposes the UMP privacy-options form (EN/BN) for revisiting consent choices.
- **Per-Variant Ad Units:** Debug builds use Google demo units; release builds use production units (`.../8421301444` banner, `.../7282470439` interstitial). Verified demo-only in debug dex.
- **Settings Screen:** Daily goal editor (500–10,000 kcal), dietary presets, reset-to-default, daily-log management.
- **Manual Intake Logging & Edit Support:** Log foods to meal types, edit/remove/clear entries, extra-intake dialog.
- **Play compliance doc set:** `GOOGLE_PLAY_DECLARATIONS.md`, `EU_TRADE_POLICY_COMPLIANCE.md`, root `caloriesnap_PlayStore_Details.md`, `release-notes-en.txt`, `release-notes-bn.txt`.
- **Share-to-install loop:** every share summary now ends with a Play Store install link.

### Changed
- **KSP Alignment:** KSP plugin `2.3.5` → `2.2.10-2.0.2` to match Kotlin 2.2.10 (fixes version-skew build failure); added `android.disallowKotlinSourceSets=false` for AGP built-in Kotlin compatibility.
- **Fragment Pin:** `androidx.fragment:fragment` constrained to 1.9.1 (was transitive 1.1.0 via play-services-basement, flagged by Play Console as outdated).
- **Dependency Pruning:** Removed 26 unused catalog entries (navigation, camera, retrofit, moshi, okhttp, coil, firebase, location, accompanist, credentials).
- **Room Safety:** Removed `fallbackToDestructiveMigration()` — future schema changes now fail loudly in development instead of silently wiping user meal logs. (v1.0 already shipped DB v2, so the 1.0→2.0 upgrade runs no migration.)
- **Full BN Coverage:** About dialog, all content descriptions, grams placeholder/unit, and DataStore sync chip now follow the language toggle.
- **Touch Targets:** Custom-grams toggle raised to 48dp minimum.
- **Listing Honesty:** Short description rewritten to 72 chars (previous text was 81, over the limit); "100% offline" claims corrected to offline-first with ads disclosure.
- **Store Graphics:** Upload artifacts flattened to no-alpha; mislabeled JPEG foreground renamed to `.jpg`.

### Fixed
- **Home-screen order:** food search now leads, followed by estimate, portion, and tracking (was: tracking first, search buried at the bottom).
- **Launch crash:** replaced `FlowRow` suggestion pills with a horizontal `LazyRow` — the `FlowRow` overload call crashed at runtime (`NoSuchMethodError`) once the search section composed.
- **Truncation fixes:** 2-line hero food title; goal chip moved to a full-width row; meal-log buttons stack emoji over label so nothing clips.
- **Jargon removal:** user-visible "Room DB" / "DataStore" strings replaced with plain language (EN/BN).
- Play Console outdated-SDK warning for `androidx.fragment:fragment:1.1.0`.
- Privacy policy now documents Room/DataStore on-device storage and the in-app data-deletion path (previously claimed RAM-only).
- metadata.json phantom `MAJOR_CAPABILITY_SERVER_SIDE_GEMINI_API` removed.

---

## [1.0.0] - 2026-10-09

### Added
- **Core Food Calorie Calculator:** Fast, single-screen utility to estimate calories for everyday foods.
- **Rich Bangladeshi & South Asian Food Database:** Bundled offline catalog featuring staple dishes (Rice, Atta Roti, Paratha, Biryani, Dal, Khichuri, Fish curry, Beef bhuna, Eggs, Singara, Somosa, Fuchka, Chotpoti, Jhal Muri, Roshogolla, Mishti Doi, Milk tea, Borhani), fresh fruits, vegetables, drinks, and global items.
- **Portion Size Recalculation:** Standard preset portions, 0.5x to 6x quantity multipliers, and custom gram/ml weight entry with automatic recalculation of calories and macronutrients (Carbs, Protein, Fat).
- **One-Tap Instant Social Sharing:** Direct formatting and sharing of calorie estimates to WhatsApp, Instagram Stories, TikTok, Facebook, and SMS.
- **Dual Language Support:** Instant English and বাংলা (Bengali) bilingual interface and food name localization.
- **Offline-First Architecture:** Complete offline functionality with zero sign-in or remote tracking required.
- **AdMob Ready:** Standard architecture with centralized `AdConfig` containing official Google test IDs and feature toggle.
- **Google Play Compliance:** Adaptive launcher icons, Material 3 dynamic styling, Edge-to-edge support, complete privacy policy, and Play Store metadata.
