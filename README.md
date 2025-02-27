# WhisperWize Kotlin UI

This repository contains the Kotlin-based user interface for the WhisperWize AI-powered spam call detection application. This UI is designed to interact with the backend server to provide a real-time call monitoring and interaction experience.

## Features

-   **Real-time Transcription Display:** Shows the transcribed text from the ongoing call.
-   **AI Response Visualization:** Displays the AI-generated text responses.
-   **Audio Playback:** Plays the AI-generated audio responses.
-   **Call Control:** Allows users to initiate and end calls.
-   **WebSocket Communication:** Establishes and manages real-time communication with the backend.
-   **WebRTC Integration:** Manages the audio connection of the call.
-   **Clean and Responsive UI:** Designed for ease of use and adaptability.

## Technologies

-   **Kotlin:** Primary programming language.
-   **Android Studio:** Integrated development environment.
-   **WebSocket:** For real-time communication.
-   **WebRTC:** For audio streaming.
-   **Coroutines:** For asynchronous programming.
-   **Retrofit/Ktor (optional):** For HTTP requests (if needed).
-   **ExoPlayer (optional):** For audio playback.
-   **Jetpack Compose or XML:** For UI design.

## Installation and Setup

**1. Prerequisites:**

-   **Android Studio:** Download and install Android Studio from the official website: [https://developer.android.com/studio](https://developer.android.com/studio)
-   **Android SDK:** Ensure you have the necessary Android SDK components installed via the SDK Manager in Android Studio.
-   **Java Development Kit (JDK):** Android Studio typically includes a compatible JDK, but ensure you have one installed.
-   **Git:** For cloning the repository.

**2. Clone the Repository:**

-   Open your terminal or command prompt.
-   Navigate to the directory where you want to clone the project.
-   Run the following command:

    ```bash
    git clone <(https://github.com/NETBOTS-TECH/whisper-app)>
    ```

**3. Open in Android Studio:**

-   Open Android Studio.
-   Select "Open an existing Android Studio project."
-   Navigate to the cloned repository directory and select the project folder.
-   Android Studio will import the project and start Gradle synchronization.

**4. Configure Backend URL:**

-   Locate the file containing the backend URL configuration. This might be:
    -   A `Constants.kt` file within your project.
    -   A value defined in the `build.gradle` (Module: app) file.
    -   A value in `local.properties`.
-   Update the URL to match the address of your backend server. Example:

    ```kotlin
    // Constants.kt
    object Constants {
        const val BACKEND_URL = "ws://(https://whisper-backend-8aqk.onrender.com)" 
    }
    ```

**5. Add Dependencies:**

-   Open the `build.gradle` file for the app module (`app/build.gradle.kts`).
-   Verify that the necessary dependencies are present. If you need to add dependencies, add them to the `dependencies` block.

    ```kotlin
    dependencies {
        // Example dependencies (adjust as needed)
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.11.0")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3") //coroutines
        // add webSocket, webRTC, ExoPlayer, retrofit/ktor as needed.
    }
    ```

-   Click "Sync Project with Gradle Files" to download and add the dependencies.

**6. Build and Run:**

-   Connect an Android device to your computer or start an Android emulator.
-   In Android Studio, select your device or emulator from the device selection dropdown.
-   Click the "Run" button (green triangle) in the toolbar.
-   Android Studio will build the app and install it on your device or emulator.

**7. Permissions:**

-   Ensure that the app has the necessary permissions (e.g., microphone, network) granted. You might be prompted to grant permissions when you run the app for the first time.

## Usage

1.  **Connect to the Backend:**

    -   The app will attempt to connect to the backend server upon launch.

2.  **Initiate or Receive Calls:**

    -   Use the call initiation button to start a call.
    -   The app will display incoming call notifications.

3.  **View Transcriptions and Responses:**

    -   The UI will display real-time transcriptions and AI-generated responses.

5.  **End Calls:**

    -   Use the end call button to terminate the call.
