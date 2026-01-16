# Asteroid Radar

Asteroid Radar is an Android application that allows users to view asteroids detected by NASA based on their proximity to Earth. It also features the NASA "Picture of the Day."

## Overview
The app fetches asteroid data from the NASA NeoWS (Near Earth Object Web Service) and displays them in a list. Users can see details about each asteroid, such as its estimated diameter, relative velocity, and distance from Earth. The app includes a background worker to keep the data up to date.

## Features
- View a list of asteroids from NASA's NeoWS API.
- View details of a specific asteroid.
- See the NASA Picture of the Day.
- Offline support using Room database.
- Background data synchronization using WorkManager.
- Multi-language support (English and Spanish).

## Stack & Tech
- **Language**: Kotlin
- **Framework**: Android SDK
- **Build System & Package Manager**: Gradle (Kotlin DSL)
- **Networking**: Retrofit
- **JSON Parsing**: Moshi
- **Image Loading**: Glide, Picasso
- **Database**: Room
- **Background Work**: WorkManager
- **Architecture**: MVVM with ViewBinding/DataBinding
- **Navigation**: Jetpack Navigation Component

## Requirements
- Android Studio (latest stable version recommended)
- JDK 11 or higher
- Android SDK 27 or higher (minSdk 27)
- A NASA API Key (Get it from [NASA APIs](https://api.nasa.gov/))

## Setup & Run

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd AsteroidRadar
   ```

2. **Configure API Key**:
   Create a `local.properties` file in the root directory (if it doesn't exist) and add your NASA API key:
   ```properties
   API_KEY=YOUR_NASA_API_KEY_HERE
   ```

3. **Open in Android Studio**:
   - Open Android Studio and select "Open an Existing Project".
   - Navigate to the `AsteroidRadar` directory and click "OK".

4. **Build and Run**:
   - Let Gradle sync and build the project.
   - Run the app on an emulator or a physical device.

## Scripts & Commands
The project uses the Gradle wrapper (`gradlew`). Common commands include:

- **Build project**: `./gradlew build`
- **Clean project**: `./gradlew clean`
- **Run unit tests**: `./gradlew test`
- **Run instrumentation tests**: `./gradlew connectedAndroidTest`
- **Assemble Debug APK**: `./gradlew assembleDebug`

## Project Structure
```text
app/
├── src/
│   ├── main/
│   │   ├── java/com/emman/android/asteroidradar/
│   │   │   ├── data/             # Data layer (Local DB, Remote API, Repository)
│   │   │   ├── domain/           # Domain models and repository interfaces
│   │   │   ├── presentation/     # UI layer (Activities, Fragments, ViewModels, Adapters)
│   │   │   ├── utils/            # Utility classes and constants
│   │   │   ├── work/             # WorkManager background workers
│   │   │   ├── AsteroidApplication.kt
│   │   │   └── MainActivity.kt
│   │   ├── res/                  # Resources (Layouts, Drawables, Values, Navigation)
│   │   └── AndroidManifest.xml
│   └── test/                     # Unit tests
└── build.gradle.kts              # App-level build configuration
```

## Environment Variables
The application uses the following configuration defined in `app/build.gradle.kts`, which reads from `local.properties`:

- `API_KEY`: Your NASA API key (Required for fetching data).
- `BASE_URL`: Defined as `https://api.nasa.gov/` in `build.gradle.kts`.

*Created as part of the Android Kotlin Developer Nanodegree.*
