package com.example.nativeapp.presentation.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

class AppointmentViewModel: ViewModel() {

    // To stablish
    fun setDataToFirebase(date: String){
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
    fun getDateFromDatabase(): String{
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
                        firstNameUser = document.data["appointment"].toString()
                        break
                    }
                }
            }
        return firstNameUser
    }

    fun deleteDataToFirebase(){
        var user = FirebaseAuth.getInstance().currentUser
        val database = Firebase.firestore
        database
            .collection("users")
            .document(user?.email.toString())
            .update(
                mapOf("appointment" to "null")
            )
    }

    // This method checks if the dateSelected is before to the actual one
    @RequiresApi(Build.VERSION_CODES.O)
    fun isDateBeforeToday(year: Int, month: Int, dayOfMonth: Int): Boolean {
        val selectedDate = LocalDate.of(year,month, dayOfMonth)
        val currentDate = LocalDate.now()
        return selectedDate.isBefore(currentDate)
    }

}