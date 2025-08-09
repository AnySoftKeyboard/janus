# Building
- this project uses Gradle build system for Android related code. It uses Kotlin language.
- dependencies are declared in the Gradle's toml format at the file @gradle/libs.versions.toml, follow this pattern when adding/modifiying dependencies.
- Prefer not adding new 3rd party dependencies.
- Do not add 3rd-party dependencies without approval. If you have a solution that requires a new 3rd party, ask for an approval before implementing this solution.
- When implementing a request first search the codebase to find similar patterns and match them in your implementation. If you do not find good examples in the codebase, perform a web-search for examples.
- When you find yourself struggling with adding a dependency or implementing a request, please reach out to me for guidance.

# Lint and Format
- don't try to fix linting or formatting issues, we have auto-fixers for that. This is applicable for *all* code in the codebase.
- You can run the auto-fixers with `./gradlew spotlessApply`. This is applicable for *all* code in the codebase.

# Git Commit Guidelines
- Before creating a commit always ensure formating (with `./gradlew spotlessApply`) and tests are passing (`./gradlew testDebugUnitTest`).

## Commit Message
When creating a commit message, follow these guidelines:
- **Title:** Use a concise title. Prefix the title with [Gemini-cli].
- **Description:** The description should include a short description of the issue (bug, feature-request, crash, chore, etc) and a short description of the solution.

# Tests
- when ask to suggest tests for a function or file:
  - Do not implement anything or suggest how to implement.
  - You should only look at the code and suggest tests based on functionality and error cases.
  - Identify the "happy path" - core functionality - cases and mark them as such in your suggestions
  - Identify the error cases and mark them as such in your suggestions. Estimate importance based on likelyhood.
  - Identify the edge cases and mark them as such in your suggestions. Estimate importance based on likelyhood.
- when implementing tests:
  - For Kotlin, the test file name follows the pattern `[original_file_name]Test.java` and should be under the `src/test` folder in the module.
  - prefer creating fakes over mocks or patches. But, if it is simpler to patch or mock, do that.
  - remember that you need to use Robolectric as a test-runner in Android.
- when a test fails, Gradle will give some output in the console, but you can read the full error message in test-report XML file. This file can be located at `[path/to/the/module]/build/test-results/testDebugUnitTest/TEST-[package.name.TestClassName].xml`. For example, if the test fail in a class named `com.anysoftkeyboard.janus.app.MainActivityTest` in a module `app`, then the test report will be `app/build/test-results/testDebugUnitTest/TEST-com.anysoftkeyboard.janus.app.MainActivityTest.xml`

## Running Tests
- To run tests in gradle use `./gradlew :[path]:[to]:[module]:testDebugUnitTest`. For example, to run test under `database/`, call `./gradlew :detebase:testDebugUnitTest`.

# Naming
- use inclusive language when creating variables, functions, class names, stubs, etc:
  - Do not use "dummy", instead use "fake", "mock", "noop" etc.
  - Do not use "blacklist", instead use "disallow-list"
  - Do not use "whilelist", instead use "allow-list"
  - Stay away from: "master", "slave", "insane", "dumb", etc.
  - Use gender neutral pronouns
