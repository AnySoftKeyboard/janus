<img src="fastlane/metadata/android/en-US/images/icon.png" width="96" align="right" />

[![CI](https://github.com/AnySoftKeyboard/janus/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/AnySoftKeyboard/janus/actions/workflows/ci.yml)
<a href="https://f-droid.org/en/packages/com.anysoftkeyboard.janus/"><img src="https://f-droid.org/badge/get-it-on-en-US.png" alt="Get it on F-Droid" height="60"></a>

**Janus** is an open-source Android app that provides contextual word and concept translations by leveraging the vast, human-curated knowledge graph of Wikipedia.

Instead of literal machine translation, it finds the corresponding Wikipedia article in the target language to offer a more accurate and context-aware translation.

## Main Features
* **Contextual Translation**: Uses Wikipedia article titles for accurate, context-aware translations instead of literal machine translation.
* **Multi-Language Support**: Translate from any Wikipedia-supported language to any other, with support for multiple target languages.
* **Rich Information**: Displays short descriptions and article summaries with HTML formatting for enhanced context.
* **Smooth Animations**: A guiding icon seamlessly transitions through the translation workflow, providing visual continuity and progress indication.
* **Full History & Bookmarks**: Keeps a chronological log of all translations and allows you to save important ones to a dedicated bookmarks screen.
* **Searchable History**: Quickly find past translations with real-time filtering in the history screen.
* **Quick Actions**: Copy translations to clipboard and open Wikipedia articles directly from the app.
* **Modern UI**: A clean, intuitive interface built with Jetpack Compose, supporting both light and dark themes.

## Building the Project
This is a standard Gradle project. To build it:
1. Clone the repository
2. Import it into the latest stable version of Android Studio
3. Sync Gradle and build

**Project Details:**
* Codename: Janus
* Application ID: `com.anysoftkeyboard.janus`
* For a detailed breakdown of the technical design and architecture, see the `INITIAL-DESIGN.md` file.
* For UI/UX strategy and branding, see the `DESIGN.md` file.
* For the technical flow of translation features, see the `TRANSLATION_FLOW.md` file.

## License
This project is licensed under the Apache License 2.0. See the `LICENSE` file for details.

Copyright 2025

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
