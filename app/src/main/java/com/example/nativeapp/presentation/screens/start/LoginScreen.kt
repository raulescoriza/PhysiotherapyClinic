package com.example.nativeapp.presentation.screens.start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nativeapp.R
import com.example.nativeapp.presentation.components.ButtonComponent
import com.example.nativeapp.presentation.components.DoubleTextWithClickable
import com.example.nativeapp.presentation.components.EmailFieldComponent
import com.example.nativeapp.presentation.components.HeadingTextComponent
import com.example.nativeapp.presentation.components.MyTextFieldComponent
import com.example.nativeapp.presentation.components.NormalTextComponent
import com.example.nativeapp.presentation.components.PasswordTextFieldComponent
import com.example.nativeapp.presentation.components.UnderLinedTextComponent
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.example.nativeapp.presentation.navigation.SystemBackButtonHandler
import com.example.nativeapp.presentation.viewmodels.login.LoginUIEvent
import com.example.nativeapp.presentation.viewmodels.login.LoginViewModel

/**
 * Adapted for every device
 */
@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()

    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                NormalTextComponent(value = stringResource(id = R.string.hey_there))
                HeadingTextComponent(value = stringResource(id = R.string.welcome_physiotherapy))
                Spacer(modifier = Modifier.height(20.dp))
                EmailFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    icon = Icons.Outlined.Email,
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.emailError
                )
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    icon = Icons.Outlined.Lock,
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.passwordError
                )
                Spacer(modifier = Modifier.height(40.dp))
                UnderLinedTextComponent(value = stringResource(id = R.string.forgot_password))
            }
        }

        // bottom
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column {
                ButtonComponent(
                    value = stringResource(id = R.string.login),
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                    },
                    isEnable = loginViewModel.allValidationPassed.value
                )
                Spacer(modifier = Modifier.height(20.dp))
                DoubleTextWithClickable(
                    value1 = stringResource(id = R.string.dont_have_account),
                    firstColor = MaterialTheme.colorScheme.onBackground,
                    value2 = stringResource(id = R.string.register),
                    secondColor = MaterialTheme.colorScheme.primary,
                    onTextSelected = { PostOfficeAppRouter.navigateTo(Screen.SignUpScreen) }
                )
            }
        }
    }
    if (loginViewModel.loginInProgress.value) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    // When the back button is clicked it will handler that action to the screen selected
    SystemBackButtonHandler {
        PostOfficeAppRouter.navigateTo(Screen.SplashScreen)
    }
}