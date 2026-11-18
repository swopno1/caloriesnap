# Google Play Console Declarations & Approval Master Reference

**App Name:** CalorieSnap: Calorie Tracker  
**Package Name:** `com.vivescriptsolutions.caloriesnap`  
**Current Release:** Version Code `2`, Version Name `2.0`  
**Developer:** ViveScript Solutions LLC  
**Support Email:** support@vivescriptsolutions.com  
**Privacy Policy URL:** https://www.vivescriptsolutions.com/en/apps/caloriesnap/privacy *(assumed from sibling apps — human must confirm the page is live)*  
**Terms of Service URL:** https://www.vivescriptsolutions.com/en/apps/caloriesnap/terms *(assumed — human must confirm)*

---

## 1. App Access
**Question:** Is access to any part of your app restricted?  
**Selection:**  
`All functionality is available without special access`  
**Reviewer Notes (if requested):**  
> *"CalorieSnap is an offline-first food calorie tracker. It requires no login credentials, username, password, subscription, or account. Reviewers can access 100% of the app immediately upon launch."*

---

## 2. Ads Declaration
**Question:** Does your app contain advertising?  
**Selection:**  
`Yes, my app contains ads`  
**Explanation for reviewers:** *"Google AdMob banner pinned to the bottom bar plus one interstitial shown before the share sheet after an explicit Share tap. Ads are gated by Google UMP consent (UMP SDK 4.0.0) — EU users who decline see no ads; US opt-out users receive restricted processing. Debug builds use Google demo units; release builds use production units ca-app-pub-5222053984568989/8421301444 (banner) and ca-app-pub-5222053984568989/7282470439 (interstitial). A Settings entry point ('Ad Privacy Options') lets users revisit consent choices."*

---

## 3. Advertising ID
**Question:** Does your app use advertising ID?  
**Selection:** `No`  
**Purpose:** N/A — the manifest explicitly removes `com.google.android.gms.permission.AD_ID` (plus AdServices permissions) via `tools:node=remove`. The AdMob SDK therefore cannot read the Advertising ID; ads serve without it.

---

## 4. Content Rating Questionnaire (IARC)
- **Category:** Health & Fitness / Food & Drink (utility, calorie estimator)
- **Does the app share user-provided personal info with third parties?** `No` first-party (AdMob declared separately under Data Safety; share sheet sends text only to the app the user explicitly picks)
- **Does the app share physical location with other users?** `No`
- **Does the app allow users to purchase digital goods?** `No`
- **Medical / health claims:** The app shows approximate nutritional estimates with a persistent medical disclaimer (in-app About dialog + store listing) stating it is not medical or clinical diet advice.
- **Recommended Rating:** `Everyone`. Human must answer the questionnaire honestly; no objectionable content exists in the app.

---

## 5. Target Audience & Content
- **Target Age Group:** `18 and older` preferred (general adult utility); `13 and older` acceptable. Do NOT target young children.
- **Could the store listing unintentionally appeal to children?** `No`
- **Designed for Families:** `No` (opt out — contains ads)

---

## 6. Special Category Declarations
- **COVID-19 Contact Tracing or Status App:** `No`
- **Government App:** `No`
- **Financial Services / Crypto / Loans:** `No`
- **News App:** `No`
- **Health / Medical / Health Connect App:** `No` (estimates only, no Health Connect integration, no medical advice — disclaimer present)

---

## 7. Data Safety Form

### Overview
- **Data Collection:** `Yes` (Google Mobile Ads SDK: Device IDs + Diagnostics + App activity for advertising).
- **Data Sharing:** `Yes` (with Google for ad serving/reporting; no first-party sharing — meal logs and preferences never leave the device).
- **Data Encryption in Transit:** `Yes` (AdMob SDK uses HTTPS).
- **Data Deletion:** `Yes` (in-app: Settings → "Clear Today's Consumed Log"; uninstalling removes all on-device data; no accounts exist).

### Data Types Declaration
| Data Category | Data Type | Collected | Shared | Ephemeral | Purpose |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Device or other IDs** | Device or other IDs | Yes (AdMob SDK) | Yes (Google) | Yes | Advertising or marketing |
| **App activity** | App interactions | Yes (AdMob SDK) | Yes (Google) | Yes | Advertising / Analytics (SDK-internal) |
| **App info and performance** | Diagnostics / Crash logs | Yes (AdMob SDK) | Yes (Google) | Yes | Advertising / Analytics (SDK-internal) |
| **Personal Info** | Name / Email / Phone | **No** | No | N/A | None (no registration) |
| **Health data** | Vitals / medical | **No** | No | N/A | Meal logs stay on-device, never collected |
| **Photos & Videos / Audio** | Media | **No** | No | N/A | None (no camera/mic/storage) |
| **Location** | Any location | **No** | No | N/A | Zero location permissions |

---

## 8. Permissions Declarations

### 8.1 Internet (`INTERNET`)
- **Declaration Statement:**
```text
Used solely to load Google AdMob banner and interstitial ads and Google UMP consent messaging. Food lookup, tracking, and goals run 100% on-device and work in airplane mode; no tracking data is ever transmitted.
```

### 8.2 Network State (`ACCESS_NETWORK_STATE`)
- **Declaration Statement:**
```text
Used by the Google Mobile Ads SDK to check connectivity before requesting ads.
```

### 8.3 Removed permissions (explicitly NOT used)
- **Declaration Statement:**
```text
com.google.android.gms.permission.AD_ID and all AD_SERVICES permissions are explicitly removed from the merged manifest via tools:node=remove. The app deliberately does not read the Advertising ID.
```

---

## 9. Reviewer Testing Guidance

```text
STEP-BY-STEP REVIEWER TESTING INSTRUCTIONS:

1. Launch CalorieSnap on any test device or emulator.
2. A UMP consent form may appear; accept or dismiss it (dismissing disables ads).
3. Search foods, filter by category chips, select a food, adjust portions (presets, multiplier, custom grams).
4. Toggle the top-bar language switch (ENG / বাংলা) to verify both UI languages.
5. Log the food to a meal type, open Settings to adjust the daily goal, presets, and clear the log.
6. Tap Share on the hero card to open the Android share sheet (interstitial may show first).
7. Tap the About icon for the About dialog (version, license, privacy summary, medical disclaimer).
8. Enable airplane mode to verify tracking works fully offline (ads will not load).
```

---

## 10. Copy-Paste Appeal Templates

### Appeal for Data Safety / Privacy Inquiry:
> **Subject:** Clarification on Data Safety Declaration - `com.vivescriptsolutions.caloriesnap`  
> **Dear Google Play Review Team,**  
> CalorieSnap is an offline-first calorie tracker. It collects no first-party data: no accounts, no analytics, no crash SDK, no servers — meal logs and preferences stay in on-device Room/DataStore storage and are deletable in-app. The only collection comes from the embedded Google Mobile Ads SDK (device identifiers + diagnostics + app activity), declared accordingly, encrypted in transit, and gated by Google UMP consent so declined users never load ads. The Advertising ID permission is explicitly removed from our manifest.

### Appeal for Ads / Interstitial Inquiry:
> **Subject:** Clarification on Interstitial Ads - `com.vivescriptsolutions.caloriesnap`  
> **Dear Google Play Review Team,**  
> CalorieSnap shows an interstitial only after an explicit Share tap, immediately before the Android share sheet, and sharing always proceeds after dismissal. The persistent banner sits in a fixed bottom bar and never overlaps interactive content. Both formats are gated by UMP consent.

---

## 11. EU Digital Services Act (DSA) & Trader Status Declaration

Google Play requires all developers distributing in the European Economic Area (EEA) to declare their trader status under **Policy and programs > Trader status**:

- **Recommended Selection:** **"Yes, I am a trader"** (for ViveScript Solutions LLC)  
  - Legal Name: `ViveScript Solutions LLC`
  - Email: `support@vivescriptsolutions.com`
  - Website: `https://www.vivescriptsolutions.com/`
  - Address: `1209 Orange Street, Wilmington, DE 19801, USA`
- See full legal analysis in `EU_TRADE_POLICY_COMPLIANCE.md`.
