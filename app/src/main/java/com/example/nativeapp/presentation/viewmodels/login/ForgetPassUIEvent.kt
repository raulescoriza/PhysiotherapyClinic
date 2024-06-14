package com.example.nativeapp.presentation.viewmodels.login

sealed class ForgetPassUIEvent {

    data class EmailChanged(val email: String): ForgetPassUIEvent()

    object ForgetPassButtonClicked: ForgetPassUIEvent()
}