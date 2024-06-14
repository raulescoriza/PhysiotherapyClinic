package com.example.nativeapp.presentation.viewmodels.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeViewModel : ViewModel() {

    /**
     * Here are initialized all the items that are going to be displayed on the drawerNavigation
     */
    val navigationItemList = listOf(
        NavigationItem(
            title = "Home",
            icon = Icons.Filled.Home,
            description = "home",
            id = "home"
        ),
        NavigationItem(
            title = "Settings",
            icon = Icons.Filled.Settings,
            description = "Settings",
            id = "settings"
        ),
        NavigationItem(
            title = "About us",
            icon = Icons.Filled.People,
            description = "About us",
            id = "aboutUs"
        )
    )

    /**
     * This method return the name registered in the database of the current user
     */
    @Composable
    fun getNameFromDatabase(): String {
        var user = FirebaseAuth.getInstance().currentUser
        // Variable to store the data
        var firstNameUser by remember { mutableStateOf("") }
        // Is where data are fetched. In collection() is indicated the collection
        Firebase.firestore.collection("users")
            // .get() return all the data users
            .get()
            .addOnSuccessListener {
                // there is checked if the primary key email is equals to the currentUser email
                // the name registered in the sign up screen is store in firstNameUser
                for (document in it) {
                    if (document.id == user?.email) {
                        firstNameUser = document.data["first_name"].toString()
                        break
                    }
                }
            }
        return firstNameUser
    }


    // With this method the session will be closed and it will move our screen to loginScreen
    fun logOut() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        PostOfficeAppRouter.navigateTo(Screen.LoginScreen)

/*
        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                PostOfficeAppRouter.navigateTo(Screen.SplashScreen)
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)*/
    }

}