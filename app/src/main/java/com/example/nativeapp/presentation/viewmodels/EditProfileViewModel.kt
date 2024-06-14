package com.example.nativeapp.presentation.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EditProfileViewModel : ViewModel() {

    // To stablish
    fun setDataToFirebase(date: String) {
        var user = FirebaseAuth.getInstance().currentUser
        val database = Firebase.firestore
        database
            .collection("users")
            .document(user?.email.toString())
            .update(
                mapOf("appointment" to date)
            )
    }

    @Composable
    fun getProfilePictureFromDatabase(): Int {
        var user = FirebaseAuth.getInstance().currentUser
        // Variable to store the data
        var profilePicture by remember { mutableStateOf<Int>(0) }
        // Is where data are fetched. In collection() is indicated the collection
        Firebase.firestore.collection("users")
            // .get() return all the data users
            .get()
            .addOnSuccessListener {
                // there is checked if the primary key email is equals to the currentUser email
                // the name registered in the sign up screen is store in firstNameUser
                for (document in it) {
                    if (document.id == user?.email) {
                        profilePicture = document.data["profile_picture"] as Int
                        break
                    }
                }
            }
        return profilePicture
    }

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

    @Composable
    fun getLastNameFromDatabase(): String {
        var user = FirebaseAuth.getInstance().currentUser
        // Variable to store the data
        var lastNameUser by remember { mutableStateOf("") }
        // Is where data are fetched. In collection() is indicated the collection
        Firebase.firestore.collection("users")
            // .get() return all the data users
            .get()
            .addOnSuccessListener {
                // there is checked if the primary key email is equals to the currentUser email
                // the name registered in the sign up screen is store in firstNameUser
                for (document in it) {
                    if (document.id == user?.email) {
                        lastNameUser = document.data["last_name"].toString()
                        break
                    }
                }
            }
        return lastNameUser
    }


    @Composable
    fun getSerialNumberFromDatabase(): String {
        var user = FirebaseAuth.getInstance().currentUser
        // Variable to store the data
        var serialNumber by remember { mutableStateOf("") }
        // Is where data are fetched. In collection() is indicated the collection
        Firebase.firestore.collection("users")
            // .get() return all the data users
            .get()
            .addOnSuccessListener {
                // there is checked if the primary key email is equals to the currentUser email
                // the name registered in the sign up screen is store in firstNameUser
                for (document in it) {
                    if (document.id == user?.email) {
                        serialNumber = document.data["serial_number"].toString()
                        break
                    }
                }
            }
        return serialNumber
    }

    fun deleteDataToFirebase() {
        var user = FirebaseAuth.getInstance().currentUser
        val database = Firebase.firestore
        database
            .collection("users")
            .document(user?.email.toString())
            .update(
                mapOf("appointment" to "null")
            )
    }

    @Composable
    fun getbirthdateFromDatabase(): String {
        var user = FirebaseAuth.getInstance().currentUser
        // Variable to store the data
        var birthdate by remember { mutableStateOf("") }
        // Is where data are fetched. In collection() is indicated the collection
        Firebase.firestore.collection("users")
            // .get() return all the data users
            .get()
            .addOnSuccessListener {
                // there is checked if the primary key email is equals to the currentUser email
                // the name registered in the sign up screen is store in firstNameUser
                for (document in it) {
                    if (document.id == user?.email) {
                        birthdate = document.data["birthdate"].toString()
                        break
                    }
                }
            }
        return birthdate
    }

    fun setBirthdateToFirebase(date: String) {
        var user = FirebaseAuth.getInstance().currentUser
        val database = Firebase.firestore
        database
            .collection("users")
            .document(user?.email.toString())
            .update(
                mapOf("birthdate" to date)
            )
    }

    fun setAddressToFirebase(address: String) {
        var user = FirebaseAuth.getInstance().currentUser
        val database = Firebase.firestore
        database
            .collection("users")
            .document(user?.email.toString())
            .update(
                mapOf("address" to address)
            )
    }

    fun setProfilePictureToFirebase(picture: String) {
        var user = FirebaseAuth.getInstance().currentUser
        val database = Firebase.firestore
        database
            .collection("users")
            .document(user?.email.toString())
            .update(
                mapOf("profile_picture" to picture)
            )
    }

    fun setNameToFirebase(name: String) {
        var user = FirebaseAuth.getInstance().currentUser
        val database = Firebase.firestore
        database
            .collection("users")
            .document(user?.email.toString())
            .update(
                mapOf("first_name" to name)
            )
    }

    fun setSurnameToFirebase(surname: String) {
        var user = FirebaseAuth.getInstance().currentUser
        val database = Firebase.firestore
        database
            .collection("users")
            .document(user?.email.toString())
            .update(
                mapOf("last_name" to surname)
            )
    }

    @Composable
    fun getAddressFromDatabase(): String {
        var user = FirebaseAuth.getInstance().currentUser
        // Variable to store the data
        var address by remember { mutableStateOf("") }
        // Is where data are fetched. In collection() is indicated the collection
        Firebase.firestore.collection("users")
            // .get() return all the data users
            .get()
            .addOnSuccessListener {
                // there is checked if the primary key email is equals to the currentUser email
                // the name registered in the sign up screen is store in firstNameUser
                for (document in it) {
                    if (document.id == user?.email) {
                        address = document.data["address"].toString()
                        break
                    }
                }
            }
        return address
    }


    // With this method the session will be closed and it will move our screen to loginScreen
    fun logOut() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
    }

    /**
     * This method delete the current user from Firebase database and Firestore database
     */
    fun deleteAccount() {
        // Two variables, one with the current user and the other with his database
        val firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(user?.email.toString())
        docRef.delete()
        user?.delete()?.addOnSuccessListener {
            PostOfficeAppRouter.navigateTo(Screen.SplashScreen)
        }
            ?.addOnFailureListener { e ->
                Log.d("DeleteAccount", "It wasn't possible")
            }
    }

    // TODO AVERIGUAR COMO HACER PARA CAMBIAR EMAIL Y PASSWORD
    /*fun setEmailToFirebase(email: String) {
        var user = FirebaseAuth.getInstance().currentUser
        val database = Firebase.firestore
        database
            .collection("users")
            .document(user?.email.toString())
            .update(
                mapOf("email" to email)
            )
    }*/

    /**
     * This method change the password from firebase not from firestore db
     */
    fun setNewPasswordToFirebase(password: String) {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null){
            user.updatePassword(password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                    } else {
                        val errorMessage = task.exception?.message
                    }
                }
        } else {
        }
    }
}