# RoadPilot AI - Specification Document

## 1. Project Overview

**Project Name:** RoadPilot AI
**Project Type:** Android AI Automotive Operating System

**Core Functionality:** A comprehensive AI-powered driving assistant that transforms any Android phone into a premium intelligent driving companion, combining AI copilot, professional dashcam, navigation, voice assistant, vehicle assistant, safety features, and media controls into one seamless experience.

## 2. Technology Stack & Choices

### Framework and Language
- **Language:** Kotlin 1.9.x
- **Min SDK:** 26 (Android 8.0)
- **Target SDK:** 34 (Android 14)
- **Compile SDK:** 34

### Key Libraries/Dependencies
- **UI:** Jetpack Compose with Material 3
- **Architecture:** MVVM + Clean Architecture
- **Dependency Injection:** Hilt
- **Navigation:** Navigation Compose
- **State Management:** StateFlow, Kotlin Coroutines
- **Local Database:** Room
- **Background Tasks:** WorkManager
- **Camera:** CameraX
- **Media Playback:** Media3 (ExoPlayer)
- **Maps:** Google Maps SDK
- **AI Integration:** Gemini SDK, OpenAI ChatGPT API
- **Speech Recognition:** Android SpeechRecognizer
- **Text-to-Speech:** Android TTS Engine
- **Location:** Google Play Services Location
- **Foreground Service:** Foreground Services with notification
- **DataStore:** Preferences DataStore

### Architecture Pattern
- Clean Architecture with 3 layers (Presentation, Domain, Data)
- MVVM for UI layer
- Repository pattern for data access
- Module-based architecture with plugin support
- Single Activity with Compose Navigation

## 3. Feature List

### Core Features
1. **AI Voice Assistant**
   - Wake phrase "Hey RoadPilot"
   - Voice command recognition
   - Text-to-speech responses
   - Continuous conversation support
   - Proactive AI suggestions

2. **Professional Dashcam**
   - CameraX continuous recording
   - Loop recording (1/3/5/10 minute segments)
   - Protected/emergency recordings
   - GPS/Compass/Timestamp overlay
   - Video quality selector
   - Storage management

3. **Navigation System**
   - Google Maps integration
   - Voice navigation
   - Route planning
   - Traffic information
   - Favorite locations
   - Trip history

4. **Operating Modes**
   - Offline Mode (core features without internet)
   - Online Mode (full AI capabilities)
   - Hybrid Mode (automatic switching)

5. **Media Controller**
   - Media session integration
   - Music playback control
   - Playlist/Artist/Album support
   - Volume control

6. **Vehicle Assistant**
   - Trip computer
   - Trip history
   - Parking location
   - Fuel log
   - Maintenance reminders

7. **Safety Features**
   - Impact detection
   - Emergency SOS
   - Emergency contacts
   - Recording lock
   - Location sharing

8. **AI Integration**
   - Gemini AI support
   - ChatGPT support
   - Conversation context
   - Task assistance

### Secondary Features
- Dark/Light theme
- Car mode (auto-launch on Bluetooth/USB connection)
- Cloud backup (Google Drive)
- Local data storage with Room
- Foreground service for background operation

## 4. UI/UX Design Direction

### Overall Visual Style
- Material Design 3 (Material You)
- Premium automotive-inspired aesthetics
- Tesla + Mercedes MBUX inspired interface
- Glass morphism effects for overlays
- Minimalist yet information-dense dashboard

### Color Scheme
- Primary: Deep Blue (#1A73E8)
- Secondary: Electric Cyan (#00BCD4)
- Surface: Dark Gray (#1E1E1E) for dark theme
- Accent: Safety Orange (#FF6D00) for alerts
- Success: Green (#4CAF50)
- Error: Red (#F44336)

### Layout Approach
- **Primary:** Landscape-first design optimized for car mount
- **Navigation:** Bottom navigation with 4 main tabs (Drive, Camera, AI, Settings)
- **Dashboard:** Speed, compass, recording status, AI status, navigation preview
- **Floating overlays:** Minimal touch UI during driving
- **Large touch targets:** Minimum 48dp for safe driving interaction
- **Voice-first interaction:** Most actions accessible via voice

### Key Screens
1. **Drive Screen (Main)**
   - Full dashboard with speed, compass, AI assistant
   - Quick action buttons
   - Navigation mini-view

2. **Camera Screen**
   - Live camera preview
   - Recording controls
   - Quick settings overlay

3. **AI Assistant Screen**
   - Chat interface
   - Voice wave animation
   - Quick suggestions

4. **Settings Screen**
   - Organized preference categories
   - Mode selection (Offline/Online/Hybrid)
   - Recording settings
   - Safety settings
   - AI configuration
