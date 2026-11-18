# European Union (EU) Digital Services Act (DSA) & Trader Policy Compliance

**Application:** CalorieSnap: Calorie Tracker  
**Package Name:** `com.vivescriptsolutions.caloriesnap`  
**Developer:** ViveScript Solutions LLC  
**Last Updated:** November 2026 (EU Digital Services Act & GDPR Ready)

This guide provides exact, copy-paste ready instructions for complying with the **European Union (EU) Digital Services Act (DSA)**, **EU Trader Status Declaration**, and **GDPR Data Protection Regulations** on Google Play Console.

---

## 1. EU Digital Services Act (DSA) Trader Status Declaration

Google Play requires all developers distributing apps in the European Economic Area (EEA) to declare their **Trader Status** under Article 30 of the EU Digital Services Act.

### Where to configure in Play Console:
1. Open **Google Play Console**.
2. In the left navigation menu, go to **Policy and programs** → **Trader status** (or under Developer Account Details).
3. Select your declaration based on your operational model (Option A or Option B below).

---

### Option A: Declaring as a "Trader" (Recommended for ViveScript Solutions LLC)

Under EU law (Directive 2005/29/EC), any entity acting for purposes relating to their commercial trade, business, craft, or profession is considered a **Trader**. The app is monetized through Google AdMob advertising, so declare as a Trader:

| Google Play Console Field | Value to Enter |
| :--- | :--- |
| **Trader Status** | **Yes, I am a trader** |
| **Legal Entity / Trader Name** | `ViveScript Solutions LLC` |
| **Email Address** | `support@vivescriptsolutions.com` (verified by OTP) |
| **Phone Number** | Verified company phone number (with international dial code) |
| **Physical Address** | `1209 Orange Street, Wilmington, DE 19801, USA` |
| **Commercial Register Number / DUNS** | Your state registration number or D-U-N-S number |
| **Website URL** | `https://www.vivescriptsolutions.com/` |

> **Note for EU Consumers:** When declared as a Trader, Google Play displays your verified business name, address, email, and phone number publicly on the CalorieSnap store listing for users browsing from the European Economic Area.

---

### Option B: Declaring as a "Non-Trader" (Not applicable while ads are enabled)

Non-trader status applies only to strictly non-commercial distribution with **zero monetization** (no ads, no in-app purchases, no subscriptions, no paid downloads). Because this app serves Google AdMob ads, **Option A is the correct declaration**.

| Google Play Console Field | Value to Enter |
| :--- | :--- |
| **Trader Status** | **No, I am not a trader** |
| **Certification** | Only if all monetization is removed from the app. |

---

## 2. EU GDPR Compliance & User Data Rights

CalorieSnap follows **Privacy-by-Design**: meal logs, goals, and preferences are stored on-device only and never transmitted. GDPR processing applies only to the embedded Google Mobile Ads SDK, which is gated by Google UMP consent:

### 2.1 Principle of Data Minimization (GDPR Article 5(1)(c))
- **No Personal Identifiable Information (PII):** The app requires zero registration, no legal names, no email addresses, no phone numbers, and no passwords.
- **On-Device Only:** Meal logs, daily goals, and language preferences are processed and stored on-device only (Room + DataStore) and never transmitted.
- **Ad SDK Only:** The sole processing of identifiers is performed by Google's SDK for ad serving, and only after UMP consent clearance. The app explicitly removes the Advertising ID permission from its manifest.

### 2.2 Consent Management (GDPR Article 6(1)(a) / ePrivacy)
- **UMP Consent Gate:** On startup the app requests consent via Google User Messaging Platform. Where consent is required (EEA/UK/Switzerland) and declined or not given, the Mobile Ads SDK is never initialized and no ads load.
- **AdMob Console Prerequisite:** A GDPR consent message must be created for the app in the AdMob console (`ca-app-pub-5222053984568989~8281700642`), otherwise EEA ad serving stays disabled by design. A US state regulations message is likewise recommended.
- **In-App Revisit:** Settings exposes an "Ad Privacy Options" entry point so users can review or change choices at any time.
- **No Consent Banner of Our Own Required:** The app sets no cookies and runs no first-party tracking; the UMP form is Google's own consent surface for its SDK.

### 2.3 Right to Erasure / "Right to be Forgotten" (GDPR Article 17)
- **In-App Deletion:** Users can delete today's meal log from Settings ("Clear Today's Consumed Log").
- **Uninstall Cleans All:** Uninstalling the app removes 100% of local application records (Room DB + DataStore). No accounts or server copies exist.
- **Advertising ID Control:** Users can reset or delete their advertising ID at any time via Android **Settings → Privacy → Ads**, which is outside the app and applies device-wide.

---

## 3. EU Consumer Rights & Disclosures (Directive 2011/83/EU)

To comply with EU digital consumer protection directives:
- **Price Disclosure:** The app is 100% free to download and use; it is supported by Google AdMob banner and interstitial ads. No hidden charges, digital goods, subscriptions, or paywalls.
- **Offline Disclosure:** Listings state precisely that *tracking* works offline while ads require connectivity — never "zero internet requirement" for the whole app.
- **Health Disclaimer:** Listings and the in-app About dialog state that calorie values are approximate estimates and not medical or clinical diet advice.
- **Age Positioning:** The listing targets adults (general utility); the app is not directed at young children and does not opt into Designed for Families.

---

## 4. Play Console Review Checklist for EU Distribution

- [x] Trader status configured under **Policy and programs > Trader status** (Yes, trader — ad-monetized).
- [x] Privacy Policy accessible via public HTTPS link (`https://www.vivescriptsolutions.com/en/apps/caloriesnap/privacy` — human must confirm live).
- [x] Terms of Service accessible via public HTTPS link (`https://www.vivescriptsolutions.com/en/apps/caloriesnap/terms` — human must confirm live).
- [x] Data Safety questionnaire answered (AdMob SDK Device IDs + Diagnostics + App activity; encrypted in transit; deletion path Yes).
- [x] Ads declaration answered Yes; Advertising ID declared No (permission explicitly removed).
- [x] Target audience declared 13+/adult with no Designed-for-Families opt-in.
- [x] Permissions restricted to ads (`INTERNET` / `ACCESS_NETWORK_STATE`) — zero dangerous permissions.
- [ ] GDPR consent message created in AdMob console for the app (required before EEA monetization).
- [ ] US state regulations message created in AdMob console (recommended).
