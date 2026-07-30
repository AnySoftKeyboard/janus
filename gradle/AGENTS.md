# Target SDK Upgrades

- **Verify Android SDK Migration Guide:** Whenever modifying `targetSdk` or `compileSdk` levels in `gradle/libs.versions.toml`:
  - Consult the official Android SDK migration guide and behavior changes documentation for the new API level (e.g., `https://developer.android.com/about/versions/<version>/behavior-changes-<version>`).
  - Review all system behavioral changes and targeted API changes.
  - Identify and suggest all required application code adaptations to maintain full compliance and compatibility with the new target SDK level.
