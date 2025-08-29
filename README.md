# The Cairo Times

A modern Android news application built with Jetpack Compose, providing users with an elegant interface to access news content through The New York Times API.

This project was developed as part of the Summit Technology Solution internship program, demonstrating proficiency in modern Android development practices and advanced UI implementation techniques.

## Features

- **Animated Landing Page**: Smooth animations with background imagery and dynamic text positioning
- **User Authentication**: Secure login functionality with form validation
- **News Feed**: Browse and read news articles from The New York Times
- **Modern UI**: Built entirely with Jetpack Compose for a native Android experience
- **Custom Typography**: Integrated custom fonts for enhanced visual appeal
- **Responsive Design**: Optimized layouts for various screen sizes

## Architecture

The application follows modern Android development patterns:

- **MVVM Architecture**: Clear separation of concerns using ViewModels
- **Jetpack Navigation**: Seamless navigation between screens
- **Compose UI**: Modern declarative UI toolkit
- **Repository Pattern**: Centralized data management
- **Retrofit Integration**: RESTful API communication

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Navigation**: Navigation Compose
- **Networking**: Retrofit 2.9.0 with Gson converter
- **Image Loading**: Coil for Compose
- **Architecture Components**: ViewModel, LiveData

## Project Structure

```
app/src/main/java/com/cocochanel/trial/
├── MainActivity.kt                    # Main application entry point
├── data/
│   ├── model/                        # Data models
│   └── repository/                   # Data layer implementation
└── ui/
    ├── features/
    │   ├── landingpage/              # Landing screen with animations
    │   ├── loginpage/                # Authentication interface
    │   ├── newspage/                 # News feed display
    │   └── newsdetailspage/          # Article details view
    └── theme/                        # App theming and styling
```



### Prerequisites

- Android Studio Arctic Fox or later
- JDK 11
- Android SDK with API level 36
- New York Times API key

### Installation

1. Clone the repository
2. Open the project in Android Studio
3. Create a `local.properties` file in the root directory
4. Add your New York Times API key:
   ```
   nytimes.api.key=YOUR_API_KEY_HERE
   ```
5. Sync the project with Gradle files
6. Run the application on an emulator or physical device

### API Key Setup

This application requires a New York Times API key to fetch news content. 

1. Visit the [New York Times Developer Portal](https://developer.nytimes.com/)
2. Create an account and register a new application
3. Generate an API key for the Article Search API
4. Add the key to your `local.properties` file as shown above


### Core Android
- Kotlin 1.9+
- AndroidX Core KTX
- AndroidX Lifecycle Runtime
- AndroidX Activity Compose

### UI and Compose
- Jetpack Compose BOM
- Material Design 3
- Navigation Compose
- Material Icons Extended
- Coil for image loading

### Networking
- Retrofit 2.9.0
- Gson Converter
- OkHttp Logging Interceptor

### Architecture
- ViewModel Compose
- Lifecycle ViewModel KTX

## Build Configuration

- **Application ID**: com.cocochanel.trial
- **Version Code**: 1
- **Version Name**: 1.0
- **Compile SDK**: 36
- **Min SDK**: 24
- **Target SDK**: 36

## Security

- API keys are stored securely in `local.properties` and not committed to version control
- ProGuard configuration included for release builds
- Input validation implemented for user authentication

