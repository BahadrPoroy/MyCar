# My Car 🚗📱

**My Car** is a dual-target Android application built for **Android Auto** (projected) and **Android Automotive OS (AAOS)**, developed using Kotlin, Jetpack Compose, and the **Jetpack Car App Library**.

The primary goal of this experiment was to read real-time vehicle telemetry data (specifically vehicle speed) directly from the car's hardware abstraction layer (VHAL) and display it safely on the in-car infotainment display following driver distraction safety guidelines.

---

## 🛠️ Tech Stack & Architecture

- **Language:** Kotlin
- **UI Framework (Mobile):** Jetpack Compose & Material 3
- **In-Car UI Framework:** Jetpack Car App Library (Templates & Screens)
- **Vehicle Hardware APIs:** `CarHardwareManager` (`CarInfo` & `CarSensors`)
- **Architecture:** Multi-module Gradle project:
  - `:mobile` — Companion mobile app (Jetpack Compose) supporting Android Auto projection.
  - `:automotive` — Native app built for Android Automotive OS (AAOS) head units.
  - `:shared` — Core business logic, Car App Services, Screens (`MyCarAppScreen`, `AboutScreen`), and localized resource strings.

---

## ✨ Features

1. **Real-time Vehicle Speed Monitoring:**
   - Subscribes to vehicle speed updates using `CarInfo.addSpeedListener` and converts raw speed values (m/s) into km/h.
2. **In-Car About Section & Localization:**
   - Multi-language support (English and Turkish) adhering to system/car locale.
   - Dynamic app version retrieval displayed in an in-car "About" screen (`AboutScreen`).
3. **Mobile Connection Status:**
   - Uses the `CarConnection` API to monitor whether the mobile app is actively projecting to Android Auto or connected to AAOS.

---

## 🏎️ How It Works in Theory & Practice

- **In Theory (and Simulators):** 
  - Works out-of-the-box in the official **Android Auto Desktop Head Unit (DHU)** emulator and Android Automotive OS emulators.
- **On Real Cars (Wireless Android Auto Limitations):**
  - Sideloaded debug APKs face strict cryptographic and category restrictions on real car head units (such as Renault R-Link 2) when using **Wireless Android Auto**, as wireless projection enforces strict Google Play signing validation. To run debug/custom apps on a real car wirelessly, publishing via Google Play's **Internal Testing** track is required.

---

## 🚀 Getting Started

1. Clone the repository.
2. Sync the project with Gradle.
3. Run the `:mobile` module on an emulator or physical device with Android Auto Developer Mode enabled.
4. Run the `:automotive` module on an Android Automotive OS virtual device.
