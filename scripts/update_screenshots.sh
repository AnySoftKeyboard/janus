#!/bin/bash
set -e

./gradlew :app:recordDebugAndroidTestScreenshots -Pandroid.testInstrumentationRunnerArguments.class=com.anysoftkeyboard.janus.app.ScreenshotGenerator

MANAGED_OUTPUT="app/build/outputs/androidTest-results/connected/debug/reference"

if [ -d "$MANAGED_OUTPUT" ] && [ "$(find "$MANAGED_OUTPUT" -name "*.png")" ]; then
    echo "Screenshots found in managed device output."
else
    echo "Warning: No screenshots found in managed device output: $MANAGED_OUTPUT"
fi

DEST="fastlane/metadata/android/en-US/images/phoneScreenshots"

rm -f "$DEST"/*.png
cp "$MANAGED_OUTPUT"/*.png "$DEST/"

echo "✅ Screenshots synced to Fastlane metadata."
