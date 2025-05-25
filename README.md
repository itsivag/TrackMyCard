# TrackMyCard

TrackMyCard is a Kotlin Multiplatform mobile application that helps users track and manage their cards and transactions across Android and iOS platforms.

## Features

- Cross-platform support for Android and iOS
- Shared business logic using Kotlin Multiplatform
- Modern UI using Compose Multiplatform
- Card management and tracking
- Transaction history and monitoring

## Project Structure

The project follows a modular architecture with the following main components:

* `/composeApp` - Contains shared code for Compose Multiplatform applications
  * `commonMain` - Code shared across all platforms
  * Platform-specific code in respective folders (e.g., `iosMain`, `androidMain`)

* `/iosApp` - iOS application entry point and SwiftUI code
* `/cards` - Card management module
* `/transactions` - Transaction tracking module
* `/models` - Shared data models
* `/helper` - Utility functions and helper classes

## Technology Stack

- Kotlin Multiplatform
- Compose Multiplatform for UI
- Gradle for build management
- Fastlane for deployment automation

## Getting Started

### Prerequisites

- Android Studio or IntelliJ IDEA
- Xcode (for iOS development)
- Kotlin 1.9.0 or higher
- JDK 17 or higher

### Building the Project

1. Clone the repository
2. Open the project in Android Studio or IntelliJ IDEA
3. Sync Gradle files
4. Run the application on your desired platform

### iOS Development

The iOS app is located in the `/iosApp` directory. Open the Xcode project and build/run from there.

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Learn More

- [Kotlin Multiplatform Documentation](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)