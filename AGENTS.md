# Building
- This project uses **Gradle** build system and the **Kotlin** language for all Android-related code.
- Declare and manage dependencies in the Gradle TOML format, specifically in the `@gradle/libs.versions.toml` file.

---

# Localization
- **Always use localization for user-facing text.** Do not hardcode strings in the UI code.
- Add new strings to `app/src/main/res/values/strings.xml` (English/Default).
- You **MUST** translate new strings into all supported languages found in the `app/src/main/res/` directory (e.g., `values-ar`, `values-de`, `values-es`, `values-fr`, `values-he`, `values-ru`).

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

# Naming
- Use inclusive language for all variables, functions, and class names:
    - Replace "dummy" with "fake," "mock," or "noop."
    - Replace "blacklist" with "disallow-list."
    - Replace "whitelist" with "allow-list."
    - Avoid terms like "master," "slave," "insane," and "dumb."
    - Use gender-neutral pronouns.

---

# Agent Skills
- **App Changelog**: If you are asked to generate or update an app changelog, you **MUST** read and follow the instructions in [.claude/skills/write-app-change-log/SKILL.md](.claude/skills/write-app-change-log/SKILL.md).
- **Add Dependency**: If you need to add a third-party dependency, follow [.claude/skills/add-dependency/SKILL.md](.claude/skills/add-dependency/SKILL.md).
- **Create Unit Test**: When creating or running unit tests, follow [.claude/skills/create-unit-test/SKILL.md](.claude/skills/create-unit-test/SKILL.md).

---

# Documentation
- **Design & Branding**: [docs/DESIGN.md](docs/DESIGN.md) - UI/UX strategy, branding, typography, and themes.
- **Initial Design**: [docs/INITIAL-DESIGN.md](docs/INITIAL-DESIGN.md) - Original design manifest.
- **Translation Flow**: [docs/TRANSLATION_FLOW.md](docs/TRANSLATION_FLOW.md) - Technical flow of translation features.
