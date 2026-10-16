# Veryfi Lens Android — Long Receipts — settings

Source of truth: Veryfi Lens Android SDK **2.7.0.6**, commit `7f33726c`. Names below were read from that tree. Items not fully confirmed are marked **Unconfirmed**. Do not copy repository passwords, keystores, or Demo-only types into the host app.


Always construct a fresh `VeryfiLensSettings()`. Android has no `VeryfiLens*Settings` subclasses. Pass that instance to `configure`. The SDK mutates it.

```kotlin
import com.veryfi.lens.helpers.DocumentType
import com.veryfi.lens.helpers.VeryfiLensSettings

fun longReceiptsSettings() = VeryfiLensSettings().apply {
    ignoreRemoteSettings = true            // default false; dashboard lens_settings overwrite this object during configure
    backupDocsToGallery = false            // default true; copies every scan into the device gallery
    moreMenuIsOn = false                   // default true; stored user prefs then replace host capture flags
    moreSettingsMenuIsOn = false           // default true
    autoCaptureOtherIsOn = false           // default true; OTHER auto-captures even when autoCaptureIsOn is false
    showNoDocumentDetectedWarning = false  // default true
    dictateIsOn = false                    // default true; obsolete voice capture, not a partner feature
    locationServicesIsOn = false           // default false; true raises a runtime location request
    glareDetectionIsOn = false             // default true; the glare gate is a weak algorithmic check

    dataExtractionEngine = VeryfiLensSettings.ExtractionEngine.VeryfiCloudAPI
    documentTypes = arrayListOf(DocumentType.LONG_RECEIPT)
    showDocumentTypes = false
    autoCaptureIsOn = false              // default false; also forced false for LONG_RECEIPT when stored prefs load
    stitchIsOn = true                    // default true
    multiplePagesCaptureIsOn = true      // default true
    returnStitchedPDF = true             // default false
    showGuideCounter = 2                 // default 0; long-receipt guide, at most this many times
    galleryIsOn = true                   // default true
    autoRotateIsOn = true                // default false
    originalLongImageMaxSizeInMB = 0f    // default 0: fall back to originalImageMaxSizeInMB (2.5f)
}

```

## Defaults versus this recipe

Android initializers below are the property defaults in `VeryfiLensSettings`.

| Property | Android default | This recipe | Why |
|----------|-----------------|-------------|-----|
| `ignoreRemoteSettings` | false | true | Dashboard `lens_settings` would overwrite this object during configure |
| `backupDocsToGallery` | true | false | Default copies every scan into the device gallery |
| `moreMenuIsOn` | true | false | Stored user prefs would replace host capture flags on later configure calls |
| `moreSettingsMenuIsOn` | true | false | Settings sheet inside More |
| `autoCaptureOtherIsOn` | true | false | `OTHER` auto-captures even when `autoCaptureIsOn` is false |
| `showNoDocumentDetectedWarning` | true | false | Skip the no-document alert |
| `dictateIsOn` | true | false | Obsolete voice control; default is on |
| `glareDetectionIsOn` | true | false | Android default glare gate is a weak check |
| `locationServicesIsOn` | false | false | Leave off unless the host needs lat/lng on the package |

| `documentTypes` | null | `LONG_RECEIPT` | Required. Add `RECEIPT` only with `showDocumentTypes = true` |
| `autoCaptureIsOn` | false | false | Kept off. Forced off when the first type is `LONG_RECEIPT` and stored prefs load |
| `returnStitchedPDF` | false | true | `img_stitched_pdf_path` |
| `showGuideCounter` | 0 | 2 | Long-receipt guide animation |
| `autoRotateIsOn` | false | true | Rotate the captured strip |
| `originalLongImageMaxSizeInMB` | 0 | 0 | 0 means use `originalImageMaxSizeInMB` |


`brandImage` is a drawable resource id shown in the stitching UI (default null). Set it only when the host has a base-module drawable.

`VeryfiInApp` does not extract long receipts. Keep `VeryfiCloudAPI`, or use `ExtractionEngine.None` for a customer-owned Process a Document call (`autoDeleteLocalResourcesAfterProcessing = false` for `data.local_package_path`).

Paid local fields (`Vendor`, `Date`, `Time`, `Total`, `Tax`) are requested for `LONG_RECEIPT` inside `getRequiredModels`, but this flavor compiles `fieldDetectionStub`. See [clarifications.md](clarifications.md).


## Suggestions

- Add `DocumentType.RECEIPT` and `showDocumentTypes = true` only when both lengths are in scope.
- Set `autoDeleteLocalResourcesAfterProcessing = false` if the host displays the stitched PDF after the callback.
- Measure installed size before switching to `com.veryfi.lens:veryfi-lens-remote-models-sdk:2.7.0.6`. That artifact allows `LONG_RECEIPT` and downloads heavy models after install.

