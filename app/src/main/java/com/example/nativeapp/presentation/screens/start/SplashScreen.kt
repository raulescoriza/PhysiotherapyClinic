package com.example.nativeapp.presentation.screens.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nativeapp.R
import com.example.nativeapp.presentation.components.ButtonComponent
import com.example.nativeapp.presentation.components.DoubleTextWithClickable
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen

/**
 * Contains the splash screen with a image which fill max size and a column with title, log in button
 * and a text component clickable to go straight register screen
 */
/*@Composable
fun SplashScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Spacer(modifier = Modifier.padding(top = 72.dp))
                Text(
                    text = stringResource(id = R.string.company_name_line_break),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.padding(30.dp),
                    color = Color.White
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Spacer(modifier = Modifier.height(360.dp))
                    // In case the user click here it will go to login screen
                    ButtonComponent(
                        value = stringResource(id = R.string.log_in),
                        onButtonClicked = {
                            PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
                        },
                        isEnable = true
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    DoubleTextWithClickable(
                        stringResource(id = R.string.dont_have_account),
                        firstColor = Color.White,
                        stringResource(id = R.string.register),
                        secondColor = Color.White,
                        onTextSelected = {
                            // In case the user click here it will go to sign up screen
                            PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
                        })
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}*/

@Composable
fun SplashScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.splash),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                Spacer(modifier = Modifier.heightIn(56.dp))
                Text(
                    text = stringResource(id = R.string.company_name_line_break),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.padding(30.dp),
                    color = Color.White
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                // In case the user click here it will go to login screen
                ButtonComponent(
                    value = stringResource(id = R.string.log_in),
                    onButtonClicked = {
                        PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
                    },
                    isEnable = true
                )
                Spacer(modifier = Modifier.heightIn(20.dp))
                DoubleTextWithClickable(
                    stringResource(id = R.string.dont_have_account),
                    firstColor = Color.White,
                    stringResource(id = R.string.register),
                    secondColor = Color.White,
                    onTextSelected = {
                        // In case the user click here it will go to sign up screen
                        PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
                    }
                )
                Spacer(modifier = Modifier.heightIn(32.dp))
            }
        }
    }
}