# Validation checklist

Run this before delivering host code. Fix conflicts by removing extra flags.

- [ ] Dependency is `com.veryfi.lens:veryfi-lens-long-receipts-sdk:2.7.0.6`
- [ ] `documentTypes` is LONG_RECEIPT and optionally RECEIPT
- [ ] `autoCaptureIsOn` is false
- [ ] `stitchIsOn`, `multiplePagesCaptureIsOn`, and `returnStitchedPDF` match the recipe
- [ ] Hardening includes `moreMenuIsOn = false` and `ignoreRemoteSettings = true`
- [ ] Launcher is inside `configure { ok -> }`
- [ ] `uploadImages` is documented as UI-SDK no-UI upload, separate from Headless Receipts
- [ ] Fraud and local fields are not promised from this artifact alone
- [ ] Any-Documents templates are escalated to support
- [ ] Size guidance compares the installed app

- [ ] Every relative link in the generated host notes stays inside this skill folder
- [ ] Unconfirmed behavior the host depends on is labeled **Unconfirmed** in the delivery note
- [ ] Host `<application>` sets `android:allowBackup="false"` and `tools:replace="android:allowBackup"` so a new-project `true` does not fail the manifest merger
- [ ] No repository password, Demo `SettingsHolder` preset, or `VeryfiLensHelper` mutation is in the host code
