package com.example.nativeapp.presentation.viewmodels.login

// One event is when the user fill any textField or click a button
sealed class LoginUIEvent {

    // To review this point: https://www.youtube.com/watch?v=peSfaIhKgfw&t=13s minute 9
    data class EmailChanged(val email: String): LoginUIEvent()
    data class PasswordChanged(val password: String): LoginUIEvent()

    object LoginButtonClicked: LoginUIEvent()



}