# Voice — Clarity fork

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE.md)
[![Upstream](https://img.shields.io/badge/upstream-PaulWoitaschek%2FVoice-brightgreen)](https://github.com/PaulWoitaschek/Voice)

**A fork of [Voice](https://github.com/PaulWoitaschek/Voice) focused on one problem: understanding audiobooks in a loud car without cranking the volume.**

Low-frequency road and engine rumble masks speech, and raising the volume to fight it risks your hearing. This fork adds a speech-in-noise processing chain (the approach used in hearing-aid research, sometimes called near-end listening enhancement) plus driving-friendly controls — on top of everything the excellent upstream player already does.

## What this fork adds

### Clarity mode

A DSP chain built on Android's native `DynamicsProcessing` effect, applied live to playback and controlled from a bottom sheet (swipe up on the playback screen, or ⋮ → Clarity):

- **Reduce rumble** — low-shelf cuts below 150/300 Hz, where road noise lives and speech meaning doesn't
- **Boost clarity** — presence lift across 1–4 kHz (where consonants carry meaning) plus a half-strength 4–7 kHz sparkle band
- **Even out volume** — a compressor that raises quiet syllables toward the loud ones so sentence tails don't sink under the noise, with makeup gain computed so overall loudness never drops, and a limiter as a safety net
- A master **on/off switch** that bypasses the whole chain instantly — your A/B test — while remembering the slider positions

Settings persist globally (it's car tuning, not book tuning) and apply in real time as you drag.

### Driving-first controls

- **Speed and Pitch steppers directly on the playback screen** — large ±0.05 buttons you can hit without looking; tap the value for a fine-grained slider
- **Cover gestures** — tap the cover art to play/pause (instantly, no double-tap delay), double-tap the left/right half to skip backward/forward, with fading YouTube-style indicators
- **Skip buttons that show your configured seek time** inside the arrow, live from settings
- **Compact layout** — small title bar, chapter row tucked at the top, more room for the cover and controls

### Pitch control

The original feature this fork started with: a global playback pitch adjustment (0.5–2.0×) using media3's Sonic processor. It turned out that naive pitch shifting *hurts* intelligibility (it smears the formants that define vowels and consonants) — which is why Clarity mode exists — but the control remains for anyone who wants it.

## Status and scope

This is a personal fork, unaffiliated with upstream. It installs under a distinct application ID (`de.ph1b.audiobook.pitch`), so it can live alongside the official Voice app. Builds are debug-signed and sideloaded; there is no store listing. Expect the DSP tuning to keep changing as road testing continues.

Everything else — the player, library, sleep timer, bookmarks, Android Auto — is upstream Voice. If you just want a great audiobook player, use [the original](https://github.com/PaulWoitaschek/Voice) from [Google Play](https://play.google.com/store/apps/details?id=de.ph1b.audiobook) or [F-Droid](https://f-droid.org/packages/de.ph1b.audiobook/), and consider [supporting its author](https://ko-fi.com/paul_voice).

## Building

```bash
./gradlew :app:assembleFreeDebug
```

Requires JDK 25 and the Android SDK (compileSdk 37). The APK lands in `app/build/outputs/apk/free/debug/`.

DSP tuning lives in [`ClarityLevels`](core/playback/src/main/kotlin/voice/core/playback/misc/ClarityEffect.kt) — the slider-to-decibel mappings are plain numbers, made to be adjusted by ear. UI components have Roborazzi snapshot tests (`./gradlew :features:playbackScreen:recordRoborazziDebug`) that render PNGs in seconds for fast design review without an emulator.

## License

Like upstream Voice, this fork is licensed under [GNU GPLv3](LICENSE.md). All modifications are published here in source form.
