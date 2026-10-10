# Design Assets Specification — CalorieSnap

**Publisher:** ViveScript Solutions LLC  
**Application ID:** `com.vivescriptsolutions.caloriesnap`  

---

## 1. Brand Visual Identity

### Colors
- **Primary Brand (Emerald):** `#10B981` (RGB: 16, 185, 129)
- **Deep Primary:** `#006D44`
- **Surface / Mint Container:** `#D1E8D5` / `#8FF7BD`
- **Warm Accent (Amber/Flame):** `#D97706` / `#F59E0B`
- **Background Light:** `#F7FBF6`
- **Background Dark:** `#101512`

### Typography
- Material 3 Typography system with scalable font sizes (`sp`).
- Clean readable headings and bold numerical calorie readouts for high glanceability.

---

## 2. Google Play Store Graphics in `/play_store_assets`

The temporary store assets directory `/play_store_assets` contains:

```
/play_store_assets/
├── app-icon/
│   ├── app-icon-512x512.png        (512x512 32-bit PNG, Google Play High-Res Icon)
│   ├── app-icon-foreground.png    (Adaptive foreground layer)
│   └── app-icon-background.png    (Adaptive background layer #10B981)
├── feature-graphic/
│   └── feature-graphic-1024x500.png (1024x500 Google Play Feature Graphic)
├── phone/
│   ├── phone-01-calculator-hero.png
│   ├── phone-02-bangla-foods.png
│   ├── phone-03-portion-adjuster.png
│   └── phone-04-social-share.png
├── tablet/
│   ├── tablet-01-landscape.png
│   └── tablet-02-portrait.png
└── README.md
```

---

## 3. Play Store Specifications
- **App Icon:** 512 x 512 px, 32-bit PNG, max 1024 KB.
- **Feature Graphic:** 1024 x 500 px, JPG or 24-bit PNG (no alpha), max 15MB.
- **Phone Screenshots:** Minimum 2 screenshots, 16:9 or 18:9 aspect ratio, 1080 x 2400 px or 1080 x 1920 px.
- **Tablet Screenshots:** 7-inch & 10-inch screenshots showing responsive layout.
