package com.example.nativeapp.presentation.screens.start

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nativeapp.R
import com.example.nativeapp.presentation.components.ButtonComponent
import com.example.nativeapp.presentation.components.EmailFieldComponent
import com.example.nativeapp.presentation.components.HeadingTextComponent
import com.example.nativeapp.presentation.components.MyTextFieldComponent
import com.example.nativeapp.presentation.components.NormalTextComponent
import com.example.nativeapp.presentation.viewmodels.login.ForgetPassUIEvent
import com.example.nativeapp.presentation.viewmodels.login.ForgetPassViewModel
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.example.nativeapp.presentation.navigation.SystemBackButtonHandler

/**
 * This will show a textField where introduce the email and a button to a new password to that
 * email. It will be done by FirebaseAuth
 */
@Composable
fun ForgetPassword(forgetPassViewModel: ForgetPassViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(28.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                NormalTextComponent(value = stringResource(id = R.string.hey_there))
                HeadingTextComponent(value = stringResource(id = R.string.forgot_password))
                Spacer(modifier = Modifier.height(20.dp))
                EmailFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    icon = Icons.Outlined.Email,
                    onTextSelected = {
                        forgetPassViewModel.onEvent(ForgetPassUIEvent.EmailChanged(it))
                    },
                    errorStatus = forgetPassViewModel.forgetPassUIState.value.emailError
                )
                Spacer(modifier = Modifier.height(80.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.submit),
                    onButtonClicked = {
                        forgetPassViewModel.onEvent(ForgetPassUIEvent.ForgetPassButtonClicked)
                    },
                    isEnable = true
                )
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
        // When the back button is clicked it will handler that action to the screen selected
        SystemBackButtonHandler {
            PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
        }
    }
}