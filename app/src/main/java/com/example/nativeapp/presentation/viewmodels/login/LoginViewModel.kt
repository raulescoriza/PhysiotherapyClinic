package com.example.nativeapp.presentation.viewmodels.login

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.nativeapp.data.rules.Validator
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

// Same class to SignUpViewModel with some changes for login
class LoginViewModel: ViewModel() {

    // For update the state of data
    var loginUIState = mutableStateOf(LoginUIState())

    // To validate all values
    var allValidationPassed = mutableStateOf(false)

    // It will show a loading bar while is login to the account inserted
    var loginInProgress = mutableStateOf(false)

    // This method update the current value
    fun onEvent(event: LoginUIEvent){
        when(event){
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }
            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }
            is LoginUIEvent.LoginButtonClicked -> {
                login()
            }
        }
        // Validation in real time
        validateLoginUIDataWithRules()
    }

    //
    private fun validateLoginUIDataWithRules(){
        val emailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationPassed.value = !emailResult.status && !passwordResult.status

    }

    // email and password will be assigned and with FirebaseAuth a method for signIn will be called
    // if it's successful it will navigate to HomeScreen, on failure it won't do anything
    private fun login() {
        loginInProgress.value = true
        var email = loginUIState.value.email
        var password = loginUIState.value.password
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    loginInProgress.value = false
                    // This control structure separate the user functionality with the admin functionality
                    if (email == "admin@gmail.com"){
                        PostOfficeAppRouter.navigateTo(Screen.AdminHomeScreen)
                    } else {
                        PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
                    }

                }
            }
            .addOnFailureListener {
                loginInProgress.value = false
            }
    }
}
