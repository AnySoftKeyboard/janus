# Building
- This project uses **Gradle** build system and the **Kotlin** language for all Android-related code.
- Declare and manage dependencies in the Gradle TOML format, specifically in the `@gradle/libs.versions.toml` file.
- Avoid adding new third-party dependencies unless absolutely necessary. Prefer using the latest version (do a web search to determine what is the latest).
- You must get approval before adding any new third-party dependency. If your solution requires one, get approval first before implementation.
- Before implementing new features or requests, search the codebase for similar patterns to maintain consistency. If you can't find a good example, do a web search for guidance.
- If you're having trouble with a dependency or a request, reach out for help.

---

# Lint and Format
- Do not manually fix linting or formatting issues. The project uses an auto-formatter.
- You can run the auto-formatter with the command `./gradlew spotlessApply`. This applies to all code in the codebase.

---

# Git Commit Guidelines
- Before creating a commit, always run `./gradlew testDebugUnitTest` to ensure all tests are passing and `./gradlew spotlessApply` to ensure proper formatting.

## Commit Messages
- **Title:** The title should be concise and prefixed with `[LLM]`.
- **Description:** Include a short description of the issue (bug, feature-request, crash, chore, etc) and a short description of the solution.
- Add your name at the end of the description to signify the commit was made by an AI Agent.
- When fixing or implementing a github issue, add the issue number at the end of the description with a `#` prefix. For example: `Fixes #123` or `Implements #123` or `Closes #123`.

---

# Tests
- **When asked to suggest tests:**
    - Do not implement or suggest implementation details.
    - Examine the code and suggest tests based on its functionality and potential errors.
    - Identify and label "happy path" cases (core functionality).
    - Identify and label "error cases" and "edge cases," estimating their importance based on likelihood.
- **When implementing tests:**
    - For Kotlin, the test file name should be **`[original_file_name]Test.kt`** and located in the module's **`src/test`** folder.
    - Prefer creating fakes over mocks or patches, but use whichever is simpler.
    - Use **Robolectric** as the test runner for Android tests.
    - If a test fails, you can find the full error report in the XML file located at **`[path/to/the/module]/build/test-results/testDebugUnitTest/TEST-[package.name.TestClassName].xml`**. For example, a test failure in **`com.anysoftkeyboard.janus.app.MainActivityTest`** in the `app` module would be in **`app/build/test-results/testDebugUnitTest/TEST-com.anysoftkeyboard.janus.app.MainActivityTest.xml`**.

## Running Tests
- To run tests in Gradle, use the command **`./gradlew :[path]:[to]:[module]:testDebugUnitTest`**. For example, to run tests in the `database` module, use **`./gradlew :database:testDebugUnitTest`**.

---

# Naming
- Use inclusive language for all variables, functions, and class names:
    - Replace "dummy" with "fake," "mock," or "noop."
    - Replace "blacklist" with "disallow-list."
    - Replace "whitelist" with "allow-list."
    - Avoid terms like "master," "slave," "insane," and "dumb."
    - Use gender-neutral pronouns.

---

# Documentation
- **Design & Branding**: [docs/DESIGN.md](docs/DESIGN.md) - UI/UX strategy, branding, typography, and themes.
- **Initial Design**: [docs/INITIAL-DESIGN.md](docs/INITIAL-DESIGN.md) - Original design manifest.
- **Translation Flow**: [docs/TRANSLATION_FLOW.md](docs/TRANSLATION_FLOW.md) - Technical flow of translation features.
- **F-Droid Submission**: [docs/FDROID_SUBMISSION.md](docs/FDROID_SUBMISSION.md) - Guide for submitting to F-Droid.
