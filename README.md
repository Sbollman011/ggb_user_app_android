# Green grubbox Documentation
Green GrubBox offers the most sustainable eco-conscious way to enjoy takeout food from your favorite food
joints and stop the abuse of single-use. Made from high-quality, reusable, food-safe materials, 
containers are tracked by their QR codes using a patent pending inventory management system.
Used containers are sanitized and recirculated to be reused up to 1,000 times. Download the app to create your membership,
locate participating restaurants, scan QR codes, and find Green GrubBox return stations. Green GrubBox does everything else
to keep getting to-go food easy for you, easy for restaurants and easy on the planet.

# Live link
https://play.google.com/store/apps/details?id=com.tv.GreenGrubBox&hl=en_IN

## 1. Programming language
JAVA/J2EE

## 2. Technologies
* Android
* SDK 
* Google map
* viper architecture
* Retrofit
* Content provider


## 3. Dependencies
* Apidoc
* Gson
 

## 4. Database
* Shared preference
 ### 4.1 Database installation & connection
 Shared Preferences allows activities and applications to keep preferences, in the form of key-value pairs similar to a Map that will persist even when the user closes the application.
 
 ## 5. Getting started

### 5.1. Clone the project
```git clone https://github.com/Stargrass123/ggb_user_app_android```

### 5.2. Install dependencies
* Android studio  (https://developer.android.com/studio/?gclid=EAIaIQobChMIzKrKxYCT4wIVGyUrCh0YmQKIEAAYASAAEgILmvD_BwE)
* Andorid Sdk

## 6. Permissions
On Android versions prior to Android 6.0, wallabag requires the following permissions:

* Full Network Access.
* View Network Connections.
* Run at startup.
* Read and write access to external storage.
* Camera and access Location.

The "Run at startup" permission is only used if Auto-Sync feature is enabled and is not utilised otherwise. The network access permissions are made use of for downloading content. 
The external storage permission is used to cache article images for viewing offline.

## 7. Project structure

```base
GreenGrubBox_android/
├── app
│   ├── APK
│   │   ├── 18July
│   │   │   └── release
│   │   └── 20March
│   │       └── noon
│   │           └── release
│   ├── release
│   └── src
│       ├── androidTest
│       │   └── java
│       │       └── com
│       │           └── tv
│       │               └── GreenGrubBox
│       ├── main
│       │   ├── assets
│       │   ├── java
│       │   │   └── com
│       │   │       └── tv
│       │   │           └── GreenGrubBox
│       │   │               ├── ActivateAccount
│       │   │               ├── activites
│       │   │               │   └── activites
│       │   │               │       ├── BoxHistory
│       │   │               │       ├── MainViewScreen
│       │   │               │       ├── NewMainViewScreen
│       │   │               │       └── QrScanner
│       │   │               ├── adapter
│       │   │               ├── BaseClasses
│       │   │               ├── Callbacks
│       │   │               ├── config
│       │   │               ├── data
│       │   │               │   ├── modal
│       │   │               │   │   └── FoodItem
│       │   │               │   ├── network
│       │   │               │   │   └── retrofit
│       │   │               │   └── prefs
│       │   │               ├── di
│       │   │               │   ├── component
│       │   │               │   └── module
│       │   │               ├── Dialogs
│       │   │               │   ├── ChangeEmailDialog
│       │   │               │   ├── ChangeToAccountTypeDialog
│       │   │               │   └── ForgotPasswordDialog
│       │   │               ├── FCM
│       │   │               ├── Fragment
│       │   │               │   ├── Account
│       │   │               │   ├── Corporate
│       │   │               │   ├── Individual
│       │   │               │   ├── MapViewVendorFragment
│       │   │               │   └── VendorListFragment
│       │   │               ├── home
│       │   │               ├── launch
│       │   │               ├── Login
│       │   │               ├── SelectCategoryTab
│       │   │               ├── service
│       │   │               ├── signup
│       │   │               │   ├── signupcorporate
│       │   │               │   └── signupindividual
│       │   │               └── utils
│       │   │                   ├── rx
│       │   │                   └── Views
│       │   └── res
│       │       ├── anim
│       │       ├── color
│       │       ├── drawable
│       │       ├── drawable-hdpi
│       │       ├── drawable-v24
│       │       ├── drawable-xhdpi
│       │       ├── layout
│       │       ├── mipmap-hdpi
│       │       ├── mipmap-ldpi
│       │       ├── mipmap-mdpi
│       │       ├── mipmap-xhdpi
│       │       ├── mipmap-xxhdpi
│       │       ├── mipmap-xxxhdpi
│       │       ├── raw
│       │       ├── values
│       │       ├── values-hdpi
│       │       └── values-xhdpi
│       └── test
│           └── java
│               └── com
│                   └── tv
│                       └── GreenGrubBox
├── build
│   └── android-profile
└── gradle
    └── wrapper
```
