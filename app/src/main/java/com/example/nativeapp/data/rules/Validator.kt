package com.example.nativeapp.data.rules

import android.util.Patterns

// This class will contain all the rules that has been decided to be implemented
object Validator {

    // For each component that we want to validate, it has to be declared.
    // It will return a true/false value
    fun validateFirstName(fName: String): ValidationResult {
        return ValidationResult(
            // checks if the value is null/empty or his length is lower than 2
            fName.trim().length < 2
        )
    }

    fun validateLastName(lName: String): ValidationResult {
        return ValidationResult(
            lName.length < 2
        )
    }

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        )
    }

    /**
     * It checks if the password contains at least 8 characters
     */
    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            password.length < 8
        )
    }

    fun validatePrivacyPolicyAcceptance(statusValue: Boolean): ValidationResult {
        return ValidationResult(
            !statusValue
        )
    }

}

// Return if the status is true or false
data class ValidationResult(
    val status: Boolean = false
)