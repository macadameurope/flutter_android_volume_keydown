# flutter_android_volume_keydown

A simple flutter plugin for Android that listens to the volume up down button. It blocks the buttons from changing the volume while listening for events.

# How to use

Using the plugin is very simple:

- Add flutter_android_volume_keydown to your pubspec.yaml
- Change your MainActivity.java or MainActivity.kt to extend FlutterAndroidVolumeKeydownActivity
- Implement a simple listener:

```dart
var subscription = FlutterAndroidVolumeKeydown.stream.listen((event) {
      if (event == HardwareButton.volume_down) {
        print("Volume down received");
      } else if (event == HardwareButton.volume_up) {
        print("Volume up received");
      }
    });
```

To stop listening:

```dart
subscription?.cancel();
```

While listening the Volume button events are not handled by Android, when you stop listening the buttons resume their normal functionality again.
---

# Repository Overview

**What it is:** A Flutter Android plugin that intercepts hardware volume button presses. Used by Macadam.CarCheck.

**What it does:**
- Listens to Android volume up/down button events
- Blocks buttons from changing device volume while listening
- Exposes a Dart stream of `HardwareButton` events

**Tech Stack:** Dart · Java/Kotlin (Android native) · Flutter plugin

**Key Components:**
- `lib/` — Dart plugin interface (`FlutterAndroidVolumeKeydown.stream`)
- `android/` — native Android implementation
- `example/` — example Flutter app

**How to Use:**
1. Add to `pubspec.yaml`
2. Extend `FlutterAndroidVolumeKeydownActivity` in `MainActivity.java/kt`
3. Listen to `FlutterAndroidVolumeKeydown.stream` for `HardwareButton.volume_up/down` events
