package com.example.nativeapp.presentation.viewmodels

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * This class contain the methods to get data from the database and set data to it
 */
class AdminViewModel : ViewModel() {

    @Composable
    fun getNameFromDatabase(currentUser: String): String {
        var userData by remember {
            mutableStateOf("")
        }
        LaunchedEffect(Unit) {
            val db = Firebase.firestore
            val docRef = db.collection("users").document(currentUser)

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        userData = document.data?.get("first_name")?.toString() ?: ""
                    } else {
                        userData = "not found"
                    }
                }
        }
        return userData
    }

    @Composable
    fun getSurnameFromDatabase(currentUser: String): String {
        var userData by remember {
            mutableStateOf("")
        }
        LaunchedEffect(Unit) {
            val db = Firebase.firestore
            val docRef = db.collection("users").document(currentUser)

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        userData = document.data?.get("last_name")?.toString() ?: ""
                    } else {
                        userData = "not found"
                    }
                }
        }
        return userData
    }

    @Composable
    fun getAddressFromDatabase(currentUser: String): String {
        var userData by remember {
            mutableStateOf("")
        }
        LaunchedEffect(Unit) {
            val db = Firebase.firestore
            val docRef = db.collection("users").document(currentUser)

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        userData = document.data?.get("address")?.toString() ?: ""
                    } else {
                        userData = "not found"
                    }
                }
        }
        return userData
    }

    @Composable
    fun getBirthdateFromDatabase(currentUser: String): String {
        var userData by remember {
            mutableStateOf("")
        }
        LaunchedEffect(Unit) {
            val db = Firebase.firestore
            val docRef = db.collection("users").document(currentUser)

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        userData = document.data?.get("birthdate")?.toString() ?: ""
                    } else {
                        userData = "not found"
                    }
                }
        }
        return userData
    }

    @Composable
    fun getNextAppointmentFromDatabase(currentUser: String): String {
        var userData by remember {
            mutableStateOf("")
        }
        LaunchedEffect(Unit) {
            val db = Firebase.firestore
            val docRef = db.collection("users").document(currentUser)

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        userData = document.data?.get("appointment")?.toString() ?: ""
                    } else {
                        userData = "not found"
                    }
                }
        }
        return userData
    }

    fun setAddressToFirebase(document: String, address: String) {
        val database = Firebase.firestore
        database
            .collection("users")
            .document(document)
            .update(
                mapOf("address" to address)
            )
    }

    fun setBirthdateToFirebase(document: String, birthdate: String) {
        val database = Firebase.firestore
        database
            .collection("users")
            .document(document)
            .update(
                mapOf("birthdate" to birthdate)
            )
    }

    fun setNameToFirebase(document: String, name: String) {
        val database = Firebase.firestore
        database
            .collection("users")
            .document(document)
            .update(
                mapOf("first_name" to name)
            )
    }

    fun setSurnameToFirebase(document: String, surname: String) {
        val database = Firebase.firestore
        database
            .collection("users")
            .document(document)
            .update(
                mapOf("last_name" to surname)
            )
    }

    fun setAppointmentToFirebase(document: String, appointment: String) {
        val database = Firebase.firestore
        database
            .collection("users")
            .document(document)
            .update(
                mapOf("appointment" to appointment)
            )
    }

    /**
     * This method delete one document by passing his name, in this case the email
     */
    fun deleteAccount(document: String) {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(document)
        docRef.delete()
            .addOnSuccessListener {

            }
            .addOnFailureListener { e ->
                Log.d("DeleteAccount", "It wasn't possible")
            }
    }

    /**
     * This method create a new as if it's registered from the SignUpScreen. The password is
     * by default 1234567, it only add the name, surname and the serial number
     */
    fun addAccount(email: String, name: String, surname: String) {
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, "1234567")
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val database = Firebase.firestore
                    // in collection() is where the collection of documents is selected from firebase
                    database.collection("users")
                        // document() requires the "primary key"
                        .document(email)
                        // here is where the data are stored
                        .set(
                            hashMapOf(
                                "first_name" to name,
                                "last_name" to surname,
                                "serial_number" to
                                        Random.Default.nextInt(1000..9999).toString() + " " +
                                        Random.Default.nextInt(100000000..999999999)
                                            .toString() + " " +
                                        Random.Default.nextInt(100..999).toString()
                            )
                        )
                }
            }
    }

}