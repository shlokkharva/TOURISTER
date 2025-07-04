# 🧭 TOURISTER – Travel Booking Android App

Tourister is a travel booking Android application that allows users to securely register and log in using Firebase Authentication, browse Indian travel packages, and send booking requests. Built using Firebase as the backend and Java on the frontend.

---

## ✨ Features

- 🔐 **Firebase Authentication** – Email/Password login and registration
- 🧳 **Browse Indian Travel Packages** – With title, description, image, and season
- 📥 **Booking Request System** – Users can send requests for tour packages
- 🗃️ **Firestore Integration** – Real-time data fetch and booking storage

---

## 🧱 Tech Stack

- **Frontend:** Java, Android SDK, ViewPager2, RecyclerView  
- **Backend:** Firebase Firestore  
- **Authentication:** Firebase Authentication

---

## 🚀 How to Run the App

### 1. **Clone the Repository**

```bash
git clone https://github.com/shlokkharva/TOURISTER.git
cd TOURISTER
````

---

### 2. **Open the Project in Android Studio**

* Open Android Studio
* Click on **Open** and select the cloned `TOURISTER` project directory

---

### 3. **Set Up Firebase**

#### a. Create a Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click **Add Project** and follow the steps
3. Add your Android app package name (e.g., `com.example.tourister`)

#### b. Add `google-services.json` to Your Project

1. Download the `google-services.json` file from Firebase
2. Move it into:
   `TOURISTER/app/google-services.json`

#### c. Enable Authentication

1. Go to **Authentication > Sign-in method**
2. Enable **Email/Password** authentication

#### d. Enable Firestore Database

1. Go to **Firestore Database**
2. Click **Create database**
3. Choose **Start in test mode** (for development only)
4. Click **Next** and select your region

---

### 4. **Configure Firebase in Code**

* In `build.gradle (Project)`:

```gradle
classpath 'com.google.gms:google-services:4.3.15'
```

* In `build.gradle (App)`:

```gradle
apply plugin: 'com.google.gms.google-services'

dependencies {
    implementation 'com.google.firebase:firebase-auth:22.1.2'
    implementation 'com.google.firebase:firebase-firestore:24.9.1'
}
```

* Click **Sync Project with Gradle Files**

---

### 5. **Build and Run the App**

* Connect a physical Android device or use an emulator
* Click **Run ▶️** in Android Studio to launch the app

---

## 📂 Firebase Collections and Usage

### 🔹 **Users Collection**

Stores user information after registration:

```json
{
  "uid": "FirebaseUID",
  "email": "user@example.com",
  "name": "User Name"
}
```

### 🔹 **Packages Collection**

Contains tour packages added manually or by admin:

```json
{
  "title": "Goa Trip",
  "description": "3 nights and 4 days beach tour",
  "imageUrl": "https://example.com/goa.jpg",
  "season": "Summer"
}
```

### 🔹 **Bookings Collection**

Stores booking requests sent by users:

```json
{
  "userId": "FirebaseUID",
  "packageId": "FirestorePackageID",
  "status": "Pending"
}
```

---

## 🙋‍♂️ Developed By

**Shlok Kharva**
B.E. Computer Engineering | Android Developer
[LinkedIn](https://www.linkedin.com/in/shlok-kharva17)

---

## 📌 Notes

* Make sure `google-services.json` is correctly placed.
* Use test mode in Firestore during development only.
* Admin panel features like accepting/rejecting bookings are not included in this version.

```

