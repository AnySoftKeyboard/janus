## 2024-05-22 - Android SDK Environment
**Learning:** The sandbox environment lacks the Android SDK, preventing execution of `./gradlew` commands including unit tests and spotless.
**Action:** Rely on static analysis and manual verification for Android-specific logic, or request environment setup if possible.

## 2024-05-22 - Compose List Optimization
**Learning:** `HistoryScreen` was re-mapping and re-grouping the entire history list on every recomposition. This is a common pattern to watch out for in Compose when mapping data models to UI models.
**Action:** Always check `collectAsState` consumers for expensive transformations that should be wrapped in `remember`.

## 2024-05-22 - Resource Lookup in Loops
**Learning:** Calling `context.getString()` inside a loop (like in `HistoryGrouper`) is a common minor performance issue.
**Action:** Lift resource lookups out of loops whenever possible.
