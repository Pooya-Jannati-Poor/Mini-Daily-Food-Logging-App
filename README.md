# **Mini Daily Food Logging App**

A small, local-only Android application for logging and managing daily food entries. This project was built with the goal of implementing Clean Architecture principles, modern Android development patterns, and robust testing practices.

## **✨ Features**

* **Add, edit, and delete** food logs.  
* Display a list of logs recorded for **today**.  
* A summary card to show the **total calorie intake** for the day.  
* **Undo** functionality for deleted items via a Snackbar.  
* A simple and smooth user interface using RecyclerView and BottomSheet.  
* Persistent data storage in a local database using **Room**.  
* Displays the Shamsi date based on the Tehran timezone.

## **🏛️ Architecture**

This project is designed based on **Clean Architecture** principles to ensure the code is maintainable, testable, and scalable. Dependencies always point inwards, meaning outer layers depend on inner layers.

The project is divided into three main modules:

### **DOMAIN**

* **The core of the application.** This layer contains the pure business logic.  
* Completely independent of the Android framework and written in **pure Kotlin**.  
* Includes **UseCases** (for each specific action), **Models** (core data classes), and **Repository Interfaces** (contracts that the Data layer must implement).

### **DATA**

* This layer is responsible for **managing data sources**.  
* It implements the interfaces defined in the Domain layer.  
* Includes the **Repository** implementation, the **Room** database (DAOs and Entities), and could in the future include networking code (like Retrofit).  
* This layer knows *where* to get the data from (database, network, etc.).

### **PRESENTATION**

* This layer is responsible for displaying data in the **UI** and handling user interactions.  
* It follows the **MVVM (Model-View-ViewModel)** pattern.  
* Includes **Fragments**, **ViewModels**, **Adapters**, and other UI-related classes.  
* Uses **Hilt** for Dependency Injection and **Jetpack Navigation** for screen transitions.  
* It observes data from the ViewModel as StateFlow and one-time events as SharedFlow.

## **🛠️ Technical Specifications**

* **Language:** Kotlin  
* **Architecture:** Clean Architecture \+ MVVM  
* **Dependency Injection:** Hilt  
* **Asynchrony:** Coroutines \+ Flow  
* **Database:** Room  
* **Navigation:** Jetpack Navigation Component  
* **UI:** ViewBinding, RecyclerView(DiffUtils), Material Design

### **Versions**

* **Minimum SDK:** 24 (Android 7.0 Nougat)  
* **Target SDK:** 36  
* **Kotlin Version:** 2.2.20  
* **Android Gradle Plugin (AGP) Version:** 8.13.0

## **🚀 Setup and Run**

To set up and run the project, follow these steps:

1. **Clone the repository:**  
   git clone: https://github.com/Pooya-Jannati-Poor/Mini-Daily-Food-Logging-App.git

2. Open in Android Studio:  
   Open the project in the latest stable version of Android Studio.  
3. Sync Gradle:  
   Android Studio will automatically download the necessary dependencies and sync the project.  
4. Run:  
   Run the application on an emulator or a physical device.

## **🧪 Testing**

This project includes various tests to ensure code quality and correctness:

* **Unit Tests:** To test the business logic in UseCases (Domain layer). These tests run on the JVM and are very fast.  
* **Integration Tests:** To test the DAO functionality and interaction with the Room database (Data layer). These tests run on an Android emulator or device.