package com.example.nativeapp.presentation

import android.app.Application
import com.google.firebase.FirebaseApp

// This is the launching point of the application. This has been added to the manifest
class LoginFlowApp : Application(){

    override fun onCreate() {
        super.onCreate()

        // initialize the app
        FirebaseApp.initializeApp(this)
    }
}