# Android Audit Report — CalorieSnap

**Application:** CalorieSnap: Calorie Tracker
**Package ID:** `com.vivescriptsolutions.caloriesnap` (namespace `com.example`)
**Version audited:** versionCode 2, versionName 2.0
**Audit date:** 2026-11-10
**Method:** Multi-agent inspection (source, build, docs, UI surface) + verification builds/tests
**Supersedes:** `Final_Release_Audit.md` (v1.0-era, preserved for history)

---

## 1. Project condition

Single-activity MVVM Compose app in good structural shape: Room (food catalog + meal logs), DataStore preferences, StateFlow UI state, 55-item seeded food database, 24 Robolectric unit tests, testTags throughout, edge-to-edge correct, input clamping (goal 500–10,000), snackbar feedback on mutations, medical disclaimer present. Git history is short (8 commits, Oct 10–13) and the tree was clean at audit start.

## 2. Findings and fixes (this modernization)

| # | Finding | Severity | Disposition |
|---|---------|----------|-------------|
| 1 | Play Console warning: transitive `androidx.fragment:fragment:1.1.0` (via play-services-basement) flagged outdated | High | Fixed: constrained to 1.9.1, verified in release graph |
| 2 | No UMP consent integration; live ad units compiled into every build variant | High | Fixed: UMP 4.0.0 + ConsentManager gating + per-variant units (dex verified demo-only) |
| 3 | KSP 2.3.5 vs Kotlin 2.2.10 version skew (build-breaking) | High | Fixed: KSP 2.2.10-2.0.2 + `android.disallowKotlinSourceSets=false` |
| 4 | `fallbackToDestructiveMigration()` could silently wipe meal logs on future schema changes | Medium | Fixed: removed; v1.0 already shipped DB v2 so 1.0→2.0 runs no migration |
| 5 | Short description 81 chars (over the 80 limit) | Medium | Fixed: rewritten to 72 chars, verified |
| 6 | "100% offline / no internet required" claims contradicted by AdMob | Medium | Fixed: offline-first wording in listing, privacy, terms |
| 7 | Privacy policy claimed RAM-only processing; meal logs persist in Room | Medium | Fixed: documents Room/DataStore storage + deletion path |
| 8 | Icon foreground is a JPEG mislabeled `.png`; all upload artifacts had alpha | Medium | Fixed: renamed to `.jpg`; icon/graphic/screenshots flattened |
| 9 | BN gaps: About dialog lines, ~10 contentDescriptions, grams placeholder/unit, sync chip | Low | Fixed: all follow the language toggle |
| 10 | Custom-grams toggle 34dp (below 48dp guideline) | Low | Fixed: 48dp minimum |
| 11 | 26 dead catalog entries; phantom GEMINI capability in metadata.json | Low | Fixed: pruned; metadata corrected |
| 12 | All docs stale at v1.0; missing declarations/EU/listing/notes docs | Low | Fixed: full doc set created/updated |
| 13 | No `gradlew`/wrapper jar in repo; no debug keystore | Low | Fixed: wrapper committed; gitignored debug keystore generated locally |
| 14 | Home screen led with tracking dashboard; search buried; `FlowRow` runtime crash; 4 truncation spots; Room/DataStore jargon in UI | High | Fixed: search-first order; `LazyRow` pills; 2-line title; full-width goal row; stacked meal buttons; plain-language strings — all verified on-device (RMX5303) |

## 3. Intentionally not changed

- **Namespace `com.example`:** branding-inconsistent but fully functional (`applicationId` is correct). A package rename is high-risk churn with zero user benefit.
- **In-code EN/BN toggle (no `values-bn/`):** works, persisted, tested, now gap-free. Migrating 135 strings to resources risks breakage for no functional gain. Recommended as future work, not a blocker.
- **State-switch navigation (no NavHost):** adequate for a 2-screen app; BackHandler covers back navigation.
- **`google-services` / secrets plugins without backends:** benign (WARN strategy, commented template key). Removal is optional cleanup.
- **Release `minifyEnabled=false`:** preserved to avoid Room/reflective-code risk. Enabling R8 is future hardening work.
- **Debug signing:** unchanged AI-Studio-style `debugConfig`; release signing stays operator-owned per standing rule (fail-fast without secrets).

## 4. Verification results

- `assembleDebug`: BUILD SUCCESSFUL
- `testDebugUnitTest`: 26/26 PASS
- `lintDebug`: 0 errors, 55 pre-existing cosmetic warnings
- Debug dex ad-ID scan: demo units only (`ca-app-pub-3940256099942544/...`); no live unit IDs
- Release dependency graph: `androidx.fragment:fragment:1.1.0 -> 1.9.1`
- Release notes: EN 487 chars, BN 498 chars (limit 500)
- Short description 72 chars, title 28 chars (limits 80 / 30)

## 5. Residual risks / human confirmations

1. Latest uploaded Play versionCode unconfirmed — local is 2/2.0; verify Console shows ≤1 before uploading, else bump.
2. AdMob GDPR + US-state messages must be published for App ID `~8281700642`; EEA ads stay disabled by design until then.
3. Privacy/terms hosting URLs assumed from sibling-app pattern; pages must be live before review.
4. Screenshots are marketing composites, not real UI captures; consider ≥1 real capture per the listing plan.
5. IARC questionnaire must be answered honestly by a human (recommended: Everyone).
6. No on-device run performed in this session (no device attached); runtime verification is recommended before upload.
