# ✨ Quotes App  
Welcome to the Quotes App! A simple and user-friendly Android application that inspires you with random quotes, lets you save your favorites, and reminds you to stay motivated with periodic notifications.  

## 🖌 Table of Contents  
- 🚀 Overview  
- ✨ Features  
- 🔧 Technologies Used  
- 🗃 Prerequisites  
- ⚡ Getting Started  
  - Step 1: Clone the Repository  
  - Step 2: Open the Project in Android Studio  
  - Step 3: Build and Run the App  
- 🔔 Setting Up Notifications  
- 🤝 [Contributing](CONTRIBUTING.md)  
- 📧 Contact  

---

## 🚀 Overview  
Quotes App is designed to bring motivation and inspiration right to your fingertips. Whether you’re looking for daily quotes or wish to save your favorites for later, this app has got you covered.  

---

## ✨ Features  
- 🌟 Display random quotes on the main screen.  
- ❤️ Save your favorite quotes to revisit them anytime.  
- 🗂 View all your saved favorite quotes in a dedicated section.  
- ⏰ Notifications to display a new quote at regular intervals.  
- 📱 Clean, simple, and intuitive user interface.  

---

## 🔧 Technologies Used  
- **Android SDK**: Core framework for Android development.  
- **Java**: Programming language used to build the app.  
- **SharedPreferences**: Local storage for saving user data.  
- **AlarmManager**: Schedule and manage periodic notifications.  
- **NotificationManager**: Display notifications.  
- **XML**: Layout design for the app's user interface.  

---

## 🗃 Prerequisites  
Before you start, make sure you have the following installed:  
- Android Studio (latest version)  
- Java Development Kit (JDK 8 or higher)  
- An Android device or emulator for testing  

---

## ⚡ Getting Started  

### Step 1: Clone the Repository  
Clone the project to your local machine:  
```bash  
git clone https://github.com/alfiyafatima09/quotesapp.git  
```  

### Step 2: Open the Project in Android Studio  
1. Launch Android Studio.  
2. Select **Open an existing project** and navigate to the cloned folder.  
3. Wait for Gradle to sync all dependencies.  

### Step 3: Build and Run the App  
1. Connect your Android device via USB or set up an emulator.  
2. Click **Run** ▶️ in Android Studio.  
3. Enjoy the app on your device!  

---

## 🔔 Setting Up Notifications  
The app uses `AlarmManager` to display notifications at regular intervals.  
1. By default, notifications are scheduled every 24 hours.  
2. Modify the interval or notification settings in the `NotificationHelper` class if needed.  

---
