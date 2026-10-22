# Little Orbit for Android

A native Android starter app for the Little Orbit long-distance companion concept.

## Download

Once the GitHub Actions build succeeds, download the latest APK here:

**[Download Little Orbit APK](https://github.com/stalinvismay2020-del/-little-orbit-android-/releases/latest/download/little-orbit.apk)**

Build runs: [GitHub Actions](https://github.com/stalinvismay2020-del/-little-orbit-android-/actions)

## Current scope

The current starter APK provides a native Android shell with Home, Chat, Daily check-in, Games, Memories, and Calls screens. Local demo interactions are available, but this is not yet a fully connected multi-user service: cross-device chat, account/login, online/offline presence notifications, real voice/video calls, cloud-synced memories, and multiplayer games require a backend and are not claimed as working.

## Build

Pushes to `main` run `.github/workflows/android-apk.yml`, which builds a debug APK, stores a workflow artifact, and updates the latest GitHub Release.

This debug APK is unsigned for production distribution. For broad public release, configure a protected signing key and sign a release build; never commit signing keys or passwords to the repository.
