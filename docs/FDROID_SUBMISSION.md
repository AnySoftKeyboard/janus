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
AntiFeatures:
  TetheredNet:
    en-US: Depends on Wikipedia servers
Categories:
  - Translation & Dictionary
License: Apache-2.0
SourceCode: https://github.com/AnySoftKeyboard/janus
IssueTracker: https://github.com/AnySoftKeyboard/janus/issues
Changelog: https://github.com/AnySoftKeyboard/janus/releases

AutoName: Janus

RepoType: git
Repo: https://github.com/AnySoftKeyboard/janus.git
Binaries: 
  https://github.com/AnySoftKeyboard/janus/releases/download/v%v/app-foss-release.apk

Builds:
  - versionName: 0.1.6
    versionCode: 11
    commit: 8cce3bdefe181cdb06a4bbebc64d5105b01277ea
    subdir: app
    gradle:
      - foss
    prebuild: sed -i -e 's/Xmx2048m/Xmx4g/' ../gradle.properties

AllowedAPKSigningKeys: 7f8511a4ed715d0e4c7adaa5b66aad463455637a60303883755a03db7cf95393

AutoUpdateMode: Version
UpdateCheckMode: Tags
CurrentVersion: 0.1.6
CurrentVersionCode: 11
```

## Step 2: Create the Merge Request

1.  Commit the file `metadata/com.anysoftkeyboard.janus.yml` to a new branch on your forked `fdroiddata` repo.
2.  Open a Merge Request (MR) against the official `fdroid/fdroiddata` repository.
3.  Title the MR: `New app: com.anysoftkeyboard.janus`.
4.  Wait for the F-Droid bot and maintainers to review it.

## How Updates Work

Once merged, the line `UpdateCheckMode: Tags` tells F-Droid to watch your GitHub repository.
When you push a new tag (e.g., `v0.1.7`), F-Droid's bot will:
1.  Detect the new tag.
2.  Automatically create a new build entry in their system.
3.  Build the app from source.
4.  Sign it with F-Droid's key.
5.  Publish it to the store.

You do **not** need to open new Merge Requests for standard updates.
