package com.example.nativeapp.presentation.viewmodels.signup

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.nativeapp.data.rules.Validator
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random
import kotlin.random.nextInt

// there is store all the data related to our UI
class SignUpViewModel : ViewModel() {

    // Every time that any event happens on the register screen, it will be saved in this variable
    // Depending on the event (textField or button) that has been changed, it will rebuild the scene
    var registrationUIState = mutableStateOf(RegistrationUIState())

    // Control if every fieldText and checkButton are true
    var allValidationsPassed = mutableStateOf(false)

    var signUpInProgress = mutableStateOf(false)

    // Once the user make an event, this method will be called
    fun onEvent(event: SignUpUIEvent) {
        when (event) {
            is SignUpUIEvent.FirstNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    firstName = event.firstName
                )
            }

            is SignUpUIEvent.LastNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    lastName = event.lastName
                )
            }

            is SignUpUIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
            }

            is SignUpUIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
            }

            is SignUpUIEvent.RegisterButtonClicked -> {
                signUp()
            }

            is SignUpUIEvent.PrivacyPolicyCheckboxClicked -> {
                registrationUIState.value = registrationUIState.value.copy(
                    privacyPolicyAccepted = event.status
                )
            }
        }
        // The reason of calling this method is to update the validationError in real time
        // while the user is writing the data on the text field
        validateDataWithRules()
    }

    // Once the user click on the sign up button, it will call the method of validate data
    private fun signUp() {
        //validateDataWithRules()
        // In order to pass the values to createUserInFirebase() they have to be done by data registered
        createUserInFirebase(
            email = registrationUIState.value.email,
            password = registrationUIState.value.password
        )
    }

    // Here is where the validator is called and is doing is function for each value
    private fun validateDataWithRules() {
        // fNameResult store a final data from the validator result
        // it checks if the data inserted is valid or not
        val fNameResult = Validator.validateFirstName(
            fName = registrationUIState.value.firstName
        )
        val lNameResult = Validator.validateLastName(
            lName = registrationUIState.value.lastName
        )
        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )
        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
            statusValue = registrationUIState.value.privacyPolicyAccepted
        )

        Log.d(TAG, "Inside_validateDataWithRules")
        Log.d(TAG, "fNameResult= $fNameResult")
        Log.d(TAG, "lNameResult= $lNameResult")
        Log.d(TAG, "emailResult= $emailResult")
        Log.d(TAG, "passwordResult= $passwordResult")
        Log.d(TAG, "privacyPolicyResult= $privacyPolicyResult")

        // There's where the error can be store to notice that the user inserted invalid data
        // if the status is true it will show an error in the method of the textField (isError parameter)
        registrationUIState.value = registrationUIState.value.copy(
            firstNameError = fNameResult.status,
            lastNameError = lNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            privacyPolicyError = privacyPolicyResult.status
        )

        // It checks if all the fields of sign up screen are valid
        allValidationsPassed.value = !fNameResult.status && !lNameResult.status &&
                !emailResult.status && !passwordResult.status && !privacyPolicyResult.status


    }

    // This method register an user in firebase with the values passed by the fieldText
    private fun createUserInFirebase(email: String, password: String) {
        // When the user is being created it will show the circularProgress
        signUpInProgress.value = true

        // In order to create the user, it needs the instance and pass email and password
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "${it.isSuccessful}")

                // Once the user has been created circularProgressBar will disappear
                signUpInProgress.value = false

                // If the user has been created correctly it will navigate to HomeScreen
                if (it.isSuccessful){

                    // Here the database is assigned to a variable
                    val database = Firebase.firestore
                    // in collection() is where the collection of documents is selected from firebase
                    database.collection("users")
                        // document() requires the "primary key"
                        .document(registrationUIState.value.email)
                        // here is where the data are stored
                        .set(hashMapOf(
                            "first_name" to registrationUIState.value.firstName,
                            "last_name" to registrationUIState.value.lastName,
                            "serial_number" to
                                    Random.Default.nextInt(1000..9999).toString() + " " +
                                    Random.Default.nextInt(100000000..999999999).toString() + " " +
                                    Random.Default.nextInt(100..999).toString()
                        ))
                        .addOnSuccessListener {
                            PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
                        }
                }

            }
            .addOnFailureListener {
                Log.d(TAG, "${it.message}")
            }
    }


    // With this method the session will be closed and it will move our screen to loginScreen
    fun logOut(){
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        val authStateListener = AuthStateListener{
            if(it.currentUser == null){
                Log.d("logout", "log out successful")
                PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
    }
}