package com.example.nativeapp.presentation.viewmodels.signup

// One event is when the user fill any textfield or click a button
sealed class SignUpUIEvent {

    // To review this point: https://www.youtube.com/watch?v=peSfaIhKgfw&t=13s minute 9
    data class FirstNameChanged(val firstName: String): SignUpUIEvent()
    data class LastNameChanged(val lastName: String): SignUpUIEvent()
    data class EmailChanged(val email: String): SignUpUIEvent()
    data class PasswordChanged(val password: String): SignUpUIEvent()
    data class PrivacyPolicyCheckboxClicked(val status: Boolean): SignUpUIEvent()

    object RegisterButtonClicked: SignUpUIEvent()



}