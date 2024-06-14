package com.example.nativeapp.presentation.screens.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nativeapp.R
import com.example.nativeapp.presentation.components.ButtonComponent
import com.example.nativeapp.presentation.components.CheckboxComponent
import com.example.nativeapp.presentation.components.DoubleTextWithClickable
import com.example.nativeapp.presentation.components.EmailFieldComponent
import com.example.nativeapp.presentation.components.HeadingTextComponent
import com.example.nativeapp.presentation.components.MyTextFieldComponent
import com.example.nativeapp.presentation.components.NormalTextComponent
import com.example.nativeapp.presentation.components.PasswordTextFieldComponent
import com.example.nativeapp.presentation.viewmodels.signup.SignUpUIEvent
import com.example.nativeapp.presentation.viewmodels.signup.SignUpViewModel
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen

@Composable
fun SignUpScreen(signUpViewModel: SignUpViewModel = viewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                NormalTextComponent(value = stringResource(id = R.string.hey_there))
                HeadingTextComponent(value = stringResource(id = R.string.create_account))
                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.name),
                    icon = Icons.Outlined.Person,
                    imeAction = ImeAction.Next,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUIEvent.FirstNameChanged(it))
                    },
                    errorStatus = signUpViewModel.registrationUIState.value.firstNameError
                )
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.surname),
                    icon = Icons.Outlined.Person,
                    imeAction = ImeAction.Next,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUIEvent.LastNameChanged(it))
                    },
                    errorStatus = signUpViewModel.registrationUIState.value.lastNameError
                )
                EmailFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    icon = Icons.Outlined.Email,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUIEvent.EmailChanged(it))
                    },
                    errorStatus = signUpViewModel.registrationUIState.value.emailError
                )
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    icon = Icons.Outlined.Lock,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUIEvent.PasswordChanged(it))
                    },
                    errorStatus = signUpViewModel.registrationUIState.value.passwordError
                )
                CheckboxComponent(
                    value = stringResource(id = R.string.term_conditions),
                    onTextSelected = {
                        PostOfficeAppRouter.navigateTo(Screen.TermsAndConditionScreen)
                    },
                    onCheckedChange = {
                        signUpViewModel.onEvent(SignUpUIEvent.PrivacyPolicyCheckboxClicked(it))
                    }
                )
            }

            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column {
                    ButtonComponent(
                        value = stringResource(id = R.string.register),
                        onButtonClicked = {
                            signUpViewModel.onEvent(SignUpUIEvent.RegisterButtonClicked)
                        },
                        // to make the button enable evb
                        isEnable = signUpViewModel.allValidationsPassed.value
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    DoubleTextWithClickable(
                        stringResource(id = R.string.have_account),
                        firstColor = MaterialTheme.colorScheme.onBackground,
                        stringResource(id = R.string.login),
                        secondColor = MaterialTheme.colorScheme.primary,
                        onTextSelected = {
                            // In case the user click here it will go to sign up screen
                            PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
                        }
                    )
                }
            }
        }
        // This will show a circularBarProgress when the user is been created
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (signUpViewModel.signUpInProgress.value) {
                CircularProgressIndicator()
            }
        }
    }
}