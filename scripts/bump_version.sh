#!/bin/bash
set -e

# Increment Version Code
VERSION_CODE=$(grep "versionCode =" app/build.gradle.kts | grep -o "[0-9]*")
NEW_VERSION_CODE=$((VERSION_CODE + 1))
echo "Incrementing version code from $VERSION_CODE to $NEW_VERSION_CODE"
sed -i "s/versionCode = $VERSION_CODE/versionCode = $NEW_VERSION_CODE/" app/build.gradle.kts
echo "NEW_VERSION_CODE=$NEW_VERSION_CODE" >> $GITHUB_ENV

# Increment Version Name
VERSION_NAME=$(grep "versionName =" app/build.gradle.kts | awk -F'"' '{print $2}')
IFS='.' read -r -a parts <<< "$VERSION_NAME"
NEW_PATCH=$((${parts[2]} + 1))
NEW_VERSION_NAME="${parts[0]}.${parts[1]}.$NEW_PATCH"

echo "Incrementing version name from $VERSION_NAME to $NEW_VERSION_NAME"
sed -i "s/versionName = \"$VERSION_NAME\"/versionName = \"$NEW_VERSION_NAME\"/" app/build.gradle.kts
echo "NEW_VERSION_NAME=$NEW_VERSION_NAME" >> $GITHUB_ENV

# Create a new changelog file
CHANGELOG_FILE="fastlane/metadata/android/en-US/changelogs/${NEW_VERSION_CODE}.txt"
echo "Creating a new changelog file at $CHANGELOG_FILE"
echo "Welcome to Janus v${NEW_VERSION_NAME} (${NEW_VERSION_CODE})" > $CHANGELOG_FILE

# Export CHANGELOG_FILE for the next step
echo "CHANGELOG_FILE=$CHANGELOG_FILE" >> $GITHUB_ENV
