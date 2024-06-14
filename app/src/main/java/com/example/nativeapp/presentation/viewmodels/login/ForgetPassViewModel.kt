package com.example.nativeapp.presentation.viewmodels.login

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.nativeapp.data.rules.Validator
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

class ForgetPassViewModel : ViewModel() {

    var forgetPassUIState = mutableStateOf(ForgetPassUIState())

    var allValidationPassed = mutableStateOf(false)

    fun onEvent(event: ForgetPassUIEvent) {
        when (event) {
            is ForgetPassUIEvent.EmailChanged -> {
                forgetPassUIState.value = forgetPassUIState.value.copy(
                    email = event.email
                )
            }

            is ForgetPassUIEvent.ForgetPassButtonClicked -> {
                sendEmailPassword()
            }
        }
        validateLoginUIDataWithRules()
    }

    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            email = forgetPassUIState.value.email
        )
        Log.d("aaaa", "${emailResult.status}")
        forgetPassUIState.value = forgetPassUIState.value.copy(
            emailError = emailResult.status
        )

        allValidationPassed.value = emailResult.status

    }

    /**
     * This method will send a email to the email inserted with a new password
     */
    private fun sendEmailPassword() {
        val email = forgetPassUIState.value.email
        FirebaseAuth
            .getInstance()
            .sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
                }
            }
            .addOnFailureListener {
            }
    }
}