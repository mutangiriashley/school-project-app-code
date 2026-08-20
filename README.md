# 🌱 FoodBridge Zambia

> Connecting local markets, bakeries, restaurants, and farms in Zambia with community centers, shelters, and feeding initiatives to eliminate food waste and fight hunger.

## 📌 Overview

![License](https://img.shields.io/badge/License-MIT-green.svg)
![Location](https://img.shields.io/badge/Region-Zambia%20%F0%9F%87%BF%F0%9F%87%B2-emerald)
![Stack](https://img.shields.io/badge/Stack-HTML5%20%7C%20CSS3%20%7C%20JS-blue)

**FoodBridge Zambia** is a lightweight, community-driven Android application designed to redirect surplus perishable food before it goes to landfills. By bridging local food donors (supermarkets, commercial bakeries, farms, and restaurants) with community organizations across Zambia—including Lusaka, Ndola, Kitwe, and Livingstone—FoodBridge facilitates rapid food rescue.

##  Key Features

* **Authentication & User Management:** Includes a secure local signup and login flow.
* **Interactive Food Donor Registration:** Allows Zambian businesses to quickly register their daily surplus volume, business type, and pickup location.
* **Location-Based Search:** Filter active meal listings by town (e.g., *Lusaka, Ndola, Kitwe, Livingstone*).
* **Dietary & Cultural Filters:** Search listings tailored to specific dietary requirements (e.g., *Halal, Vegan, Vegetarian, Gluten-Free*).
* **Real-Time Claim System:** Direct reservation triggers with instant status tracking for community drivers and shelters.
* **Mobile-First & Ultra-Lightweight:** Built natively with Jetpack Compose for fast rendering on mobile networks and low-bandwidth connections.

##  Tech Stack

* **Language:** Kotlin
* **UI Framework:** Jetpack Compose (Material Design 3)
* **Architecture:** MVVM (Model-View-ViewModel)
* **Local Persistence:** Room Database with Kotlin Symbol Processing (KSP)

## My Default Test Account

I made the application come pre-seeded with a default user account for testing the login flow immediately, but you can register if you want
* **Email:** `ashleymutangiri@gmail.com`
* **Password:** `12345`

##  Getting Started

I built this project with Android Studio using standard Android Gradle tooling. To build and run:
1. Open the project in Android Studio (or run standard Gradle tasks).
2. Sync the Gradle files.
3. Build and deploy to an Android Emulator or physical device using `gradle assembleDebug` or by clicking "Run".
