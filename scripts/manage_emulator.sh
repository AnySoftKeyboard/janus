#!/bin/bash

# Exit on error
set -e

# 0. Configuration & Paths
[ -z "$ANDROID_HOME" ] && { echo "❌ ANDROID_HOME not set"; exit 1; }

SDK_MANAGER="$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager"
AVD_MANAGER="$ANDROID_HOME/cmdline-tools/latest/bin/avdmanager"
EMULATOR="$ANDROID_HOME/emulator/emulator"
ADB="$ANDROID_HOME/platform-tools/adb"

# Path Fallbacks
[ ! -f "$SDK_MANAGER" ] && SDK_MANAGER="$ANDROID_HOME/cmdline-tools/tools/bin/sdkmanager"
[ ! -f "$AVD_MANAGER" ] && AVD_MANAGER="$ANDROID_HOME/cmdline-tools/tools/bin/avdmanager"

AVD_NAME="pixel_6_api_33"
API_LEVEL="33"
DEVICE_PROFILE="pixel_6"

# Architecture detection
ARCH=$(uname -m)
case "$ARCH" in
    x86_64) ABI="x86_64"; SYS_IMG_ARCH="x86_64" ;;
    arm64|aarch64) ABI="arm64-v8a"; SYS_IMG_ARCH="arm64-v8a" ;;
    *) echo "❌ Unsupported architecture: $ARCH"; exit 1 ;;
esac

SYSTEM_IMAGE="system-images;android-${API_LEVEL};google_apis;${SYS_IMG_ARCH}"

# --- Helper Functions ---

get_serial_by_avd() {
    # Get all connected emulators
    local emulators
    emulators=$("$ADB" devices | grep "emulator-" | cut -f1)

    for serial in $emulators; do
        # Use 'timeout' (macOS: 'gtimeout' if installed via coreutils, or a background subshell)
        # to ensure a single dead emulator doesn't hang the loop.
        # Here we use a simple background check for portability.
        
        local name=""
        # We try to get the property with a 2-second limit
        name=$( ( "$ADB" -s "$serial" shell getprop ro.boot.qemu.avd_name & sleep 2; kill $! 2>/dev/null ) | tr -d '\r' )

        if [[ "$name" == "$AVD_NAME" ]] || [[ "${name// /_}" == "$AVD_NAME" ]]; then
            echo "$serial"
            return 0
        fi
    done
    return 1
}

stop_emulator() {
    echo "🔍 Killing OS process for $AVD_NAME..."
    # Finding PID by command line arguments
    local pid
    pid=$(ps -ef | grep "emulator" | grep "$AVD_NAME" | grep -v "grep" | awk '{print $2}')

    if [ -n "$pid" ]; then
        kill -9 "$pid"
        echo "🛑 Terminated PID $pid"
        sleep 2
    else
        echo "ℹ️ No process found for $AVD_NAME."
    fi
}

start_emulator() {
    echo "🔍 Verifying system image..."
    "$SDK_MANAGER" --install "$SYSTEM_IMAGE" > /dev/null 2>&1 || true

    if ! "$EMULATOR" -list-avds | grep -q "^$AVD_NAME$"; then
        echo "🏗️ Creating AVD $AVD_NAME..."
        echo "no" | "$AVD_MANAGER" create avd --name "$AVD_NAME" --package "$SYSTEM_IMAGE" --device "$DEVICE_PROFILE" --abi "$ABI" --force
    fi

    # Check if already running (with timeout protection)
    if get_serial_by_avd > /dev/null; then
        echo "✅ Emulator $AVD_NAME is already running."
        return 0
    fi

    # Launch flags
    WINDOW_FLAG=""
    GPU_FLAG="host"
    if [[ "$*" == *"--headless"* ]]; then
        echo "☁️ Starting Headless..."
        WINDOW_FLAG="-no-window -no-audio"
        [[ "$OSTYPE" == "linux-gnu"* ]] && GPU_FLAG="swiftshader_indirect"
    fi

    # Launching with -no-snapshot-save to prevent hangs on exit
    "$EMULATOR" -avd "$AVD_NAME" $WINDOW_FLAG -gpu "$GPU_FLAG" -no-snapshot-load -no-snapshot-save > /dev/null 2>&1 &

    echo "⏳ Waiting for $AVD_NAME to register (timeout 60s)..."
    local serial=""
    local retries=0
    while [ -z "$serial" ] && [ $retries -lt 20 ]; do
        sleep 3
        serial=$(get_serial_by_avd || echo "")
        ((retries++))
    done

    if [ -z "$serial" ]; then
        echo "❌ Timeout: $AVD_NAME failed to register with ADB."
        exit 1
    fi

    echo "⏳ Booting $AVD_NAME ($serial)..."
    "$ADB" -s "$serial" wait-for-device
    while [ "$("$ADB" -s "$serial" shell getprop sys.boot_completed | tr -d '\r')" != "1" ]; do
        sleep 3
    done
    echo "✨ Ready!"
}

# --- Main Logic ---
case "$1" in
    start) start_emulator "$@" ;;
    stop)  stop_emulator ;;
    *)     echo "Usage: $0 {start|stop} [--headless]"; exit 1 ;;
esac