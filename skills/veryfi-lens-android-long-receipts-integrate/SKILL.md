---
name: veryfi-lens-android-long-receipts-integrate
description: "Integrates the Veryfi Lens Android Long Receipts SDK (com.veryfi.lens:veryfi-lens-long-receipts-sdk:2.7.0.6): Maven setup, permissions, VeryfiLens.configure, VeryfiLensSettings for LONG_RECEIPT and RECEIPT, stitching, delegate callbacks, showCamera, showGallery, showDocumentBrowser, and uploadImages. Use when adding long-receipt or receipt capture to an Android app, or debugging stitch, upload, and extraction."
---

# Veryfi Lens Android — Long Receipts

Walk a host Android app from Gradle to a working first scan. Change the fewest flags. Do not invent SDK APIs. Facts were read from the Android SDK at version `2.7.0.6` (commit `7f33726c`). Anything not confirmed there is marked **Unconfirmed**.

**Coordinate:** `com.veryfi.lens:veryfi-lens-long-receipts-sdk:2.7.0.6`  
**Gradle flavor:** `lensLongReceipts`  
**Allowed document types:** `DocumentType.LONG_RECEIPT` and `DocumentType.RECEIPT`.

Public entry point: `com.veryfi.lens.VeryfiLens` with `com.veryfi.lens.VeryfiLensDelegate`. Settings type: `com.veryfi.lens.helpers.VeryfiLensSettings`.

This folder is complete by itself. Duplicate guidance across products is intentional.

## Pick a workflow

- **Quick** — the host already asked for this product. Ask nothing else. Apply the settings in [settings.md](settings.md), wire [implementation.md](implementation.md), and state the assumptions below.
- **Guided** — something material is missing. Ask only the unknown questions (at most four, one message), then continue.

### Quick workflow

```
veryfi-lens-android-long-receipts-integrate quick:
- [ ] Confirm this artifact (install.md)
- [ ] Add the Maven repository and this one dependency
- [ ] Permissions (install.md)
- [ ] Credentials + settings from settings.md
- [ ] setDelegate, then configure { ok -> }, then showCamera() only when ok is true
- [ ] Handle the callbacks in callbacks.md
- [ ] Run the conflicts in troubleshooting.md
```

Assumptions when the host was specific about the product and silent on the rest: Lens camera, `LONG_RECEIPT` only, Cloud API, stitch on, gallery on, auto-capture left false, More menu off.

### Guided workflow

1. **Types** — long receipts only, or also standard receipts?
2. **Entry** — camera, gallery, files, or `uploadImages` for images the app already has?
3. **Extraction** — Veryfi Cloud (default) or decoupled `ExtractionEngine.None`?
4. **Length** — should the stitch UI show a brand drawable (`brandImage`)?

Defaults: camera, `LONG_RECEIPT`, Cloud API, stitch and multi-page on, gallery on.


## Rules

1. `documentTypes` is only `LONG_RECEIPT` and, if requested, `RECEIPT`.
2. When `documentTypes[0]` is `LONG_RECEIPT` and stored prefs load (`moreMenuIsOn` and a previous session), `loadSettings()` forces `autoCaptureIsOn = false`. Leave auto-capture off.
3. Stitch and multi-page stay on. `returnStitchedPDF = true` fills `img_stitched_pdf_path`.
4. `uploadImages` is this UI SDK's no-UI file path. Headless receipt detection is `com.veryfi.lens.headless:receipts-sdk` (`VeryfiLensHeadless`), which does not stitch through Lens UI and does not call Process a Document.
5. Configure, then present. See [implementation.md](implementation.md).


## Do not

- Copy a repository password, keystore, or credential from the SDK repo or the Demo app. Maven access comes from Veryfi. Keep it in `~/.gradle/gradle.properties` or CI secrets.
- Depend on a second Lens UI flavor, or on `com.veryfi.lens.core:veryfi-lens-core-sdk` (email and obsolete dictation; adds `WRITE_CONTACTS` and `RECORD_AUDIO`).
- Treat Demo types as the client contract: `SettingsHolder`, Demo analyzers, `ImageProxyUtils`, `VeryfiLensHelper`.
- Set `dictateIsOn = true` or call `VeryfiLensCore.showDictation()`.
- Set `anyDocumentType` (deprecated local regex OCR). A new template id is `anyDocumentTemplate`, and only after Veryfi support confirms it.
- Set `useLegacyModels` (default true; no partner contract).
- Quote AAR size as the installed app size. Compare the final app with the SDK linked and removed. [SDK binary sizes](https://docs.veryfi.com/lens/mobile/settings/sdk_binary_sizes/).
- Treat `allowSubmittingScreenshots` (rejects imported screenshots) and `enableScreenshots` (device screenshots of Lens screens, default true) as one switch.
- Set `autoCaptureIsOn = true` for a first type of `LONG_RECEIPT` and expect it to stick after prefs load.
- Add `CREDIT_CARD`, `CODE`, `CHECK`, `W2`, `W9`, or `ANY_DOCUMENT`.
- Promise `documentFieldsToDetect` on this artifact (`fieldDetectionStub`).


## What to hand back

1. Repository plus `implementation "com.veryfi.lens:veryfi-lens-long-receipts-sdk:2.7.0.6"`.
2. Permission notes from [install.md](install.md).
3. Delegate, `configure { ok -> }`, and one launcher.
4. `longReceiptsSettings()` with comments on non-default flags.
5. Conflicts, including auto-capture being forced off, and any **Unconfirmed** item.


## Files in this folder

- [install.md](install.md) — Maven coordinate, requirements, permissions, app size
- [implementation.md](implementation.md) — configure, delegate, and launch or camera lifecycle
- [settings.md](settings.md) — `VeryfiLensSettings` for this product, with Android defaults
- [callbacks.md](callbacks.md) — delegate payloads
- [troubleshooting.md](troubleshooting.md) — symptom, cause, fix
- [clarifications.md](clarifications.md) — Unconfirmed items, Any-Documents, fraud, field detection
- [checklist.md](checklist.md) — validation checklist before handing code to the host

