# F-Droid Submission Guide

This guide outlines the steps to submit **Janus** to the official F-Droid repository.

## Prerequisites

1.  **GitLab Account**: You need an account on [GitLab.com](https://gitlab.com) (where F-Droid is hosted).
2.  **Fork fdroiddata**: Fork the [fdroiddata repository](https://gitlab.com/fdroid/fdroiddata) to your account.

## Step 1: Create the Metadata File

You need to create a new file in your forked `fdroiddata` repository.

**Path:** `metadata/com.anysoftkeyboard.janus.yml`

**Content:**

```yaml
Categories:
  - translation
License: Apache-2.0
SourceCode: https://github.com/AnySoftKeyboard/janus
IssueTracker: https://github.com/AnySoftKeyboard/janus/issues
Changelog: https://github.com/AnySoftKeyboard/janus/releases

AutoName: Janus
Summary: Contextual translations using Wikipedia's knowledge graph
Description: |-
  Janus provides contextual word and concept translations by leveraging the vast, human-curated knowledge graph of Wikipedia. Instead of literal machine translation, it finds the corresponding Wikipedia article in the target language to offer a more accurate and context-aware translation.

  Features:
  * Contextual Translation: Uses Wikipedia article titles for accurate, context-aware translations.
  * Multi-Language Support: Translate from any Wikipedia-supported language to any other.
  * Rich Information: Displays short descriptions and article summaries.
  * Full History & Bookmarks: Keeps a log of translations and allows saving important ones.
  * Searchable History: Quickly find past translations.
  * Quick Actions: Copy translations and open Wikipedia articles.
  * Modern UI: Clean interface supporting light and dark themes.

RepoType: git
Repo: https://github.com/AnySoftKeyboard/janus.git

Builds:
  - versionName: 1.0
    versionCode: 1
    commit: [INSERT_COMMIT_SHA_OF_TAG_HERE]
    subdir: app
    sudo:
      - apt-get update || apt-get update
      - apt-get install -y openjdk-17-jdk-headless
    gradle:
      - yes
    output: app/build/outputs/apk/release/app-release-unsigned.apk

AutoUpdateMode: Version janus_release_v%v
UpdateCheckMode: Tags
CurrentVersion: 1.0
CurrentVersionCode: 1
```

> [!IMPORTANT]
> **Replace `[INSERT_COMMIT_SHA_OF_TAG_HERE]`** with the actual full commit SHA of your `v1.0` tag (or whatever your initial release tag is).

## Step 2: Create the Merge Request

1.  Commit the file `metadata/com.anysoftkeyboard.janus.yml` to a new branch on your forked `fdroiddata` repo.
2.  Open a Merge Request (MR) against the official `fdroid/fdroiddata` repository.
3.  Title the MR: `New app: com.anysoftkeyboard.janus`.
4.  Wait for the F-Droid bot and maintainers to review it.

## How Updates Work

Once merged, the line `UpdateCheckMode: Tags` tells F-Droid to watch your GitHub repository.
When you push a new tag (e.g., `v1.1`), F-Droid's bot will:
1.  Detect the new tag.
2.  Automatically create a new build entry in their system.
3.  Build the app from source.
4.  Sign it with F-Droid's key.
5.  Publish it to the store.

You do **not** need to open new Merge Requests for standard updates.
