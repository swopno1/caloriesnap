# Google Play Store Data Safety Questionnaire Guide

**Application:** CalorieSnap  
**Package:** `com.vivescriptsolutions.caloriesnap`  
**Publisher:** ViveScript Solutions LLC  
**Last Audited:** October 9, 2026  

---

### Data Collection & Sharing Declaration

| Question in Play Console | Recommended Answer | Reason |
|:---|:---|:---|
| Does your app collect or share any of the required user data types? | **Yes** | Standard device identifiers collected automatically by the Google Mobile Ads (AdMob) SDK for ad serving and fraud prevention. |
| Does your app collect location data? | **No** | Zero location permissions requested. |
| Does your app collect personal info (name, email, phone, etc.)? | **No** | No user login, registration, or form inputs. |
| Does your app collect financial info? | **No** | No in-app purchases or payments. |
| Does your app collect health or fitness data? | **No** | CalorieSnap does not store individual health vitals, user weight, or personal medical profiles. |
| Does your app collect device or other IDs? | **Yes** | Device or other identifiers (collected by Google Play Services / AdMob SDK). |
| Data purpose for Device IDs? | **Advertising or marketing, Fraud prevention and security** | Handled natively by Google Mobile Ads SDK. |
| Is all user data collected by your app encrypted in transit? | **Yes** | Google Mobile Ads SDK transmits ad requests over HTTPS/TLS. |
| Do you provide a way for users to request data deletion? | **No / N/A** | CalorieSnap maintains no user accounts or custom database. |

---

### Active AdMob Configuration
- **AdMob SDK:** Google Mobile Ads SDK `23.6.0` (`com.google.android.gms:play-services-ads:23.6.0`)
- **AdMob App ID:** `ca-app-pub-5222053984568989~8281700642`
- **Banner Ad Unit ID:** `ca-app-pub-5222053984568989/8421301444` (placed non-intrusively in bottom bar)
- **Interstitial Ad Unit ID:** `ca-app-pub-5222053984568989/7282470439` (preloaded and triggered on natural transition during sharing)
