package com.example.nativeapp.presentation.app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.example.nativeapp.presentation.screens.drawer.AboutUsScreen
import com.example.nativeapp.presentation.screens.home.AppointmentScreen
import com.example.nativeapp.presentation.screens.home.ContactScreen
import com.example.nativeapp.presentation.screens.drawer.EditProfileScreen
import com.example.nativeapp.presentation.screens.start.ForgetPassword
import com.example.nativeapp.presentation.screens.home.HomeScreen
import com.example.nativeapp.presentation.screens.start.LoginScreen
import com.example.nativeapp.presentation.screens.home.MyProfileScreen
import com.example.nativeapp.presentation.screens.home.ProfessionalScreen
import com.example.nativeapp.presentation.screens.drawer.SettingScreen
import com.example.nativeapp.presentation.screens.admin.AdminRefresh
import com.example.nativeapp.presentation.screens.admin.AdminHomeScreen
import com.example.nativeapp.presentation.screens.drawer.AboutUs
import com.example.nativeapp.presentation.screens.healthprograms.HealthFitnessScreen
import com.example.nativeapp.presentation.screens.healthprograms.HealthNutritionScreen
import com.example.nativeapp.presentation.screens.healthprograms.HealthProgramScreen
import com.example.nativeapp.presentation.screens.home.Appointment
import com.example.nativeapp.presentation.screens.start.SignUpScreen
import com.example.nativeapp.presentation.screens.start.SplashScreen
import com.example.nativeapp.presentation.screens.start.TermAndConditionScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostOfficeApp() {
    // Surface component is a rectangular area with background, style and interaction properties
    // is different from Box component, box component organize components inside of is area
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        // allows to switch between two layouts with a crossFade animation
        // targetState indicate the screen that has to show and
        // depends of the value called it will show a screen or other
        Crossfade(targetState = PostOfficeAppRouter.currentScreen, label = "") { currentState->
            when(currentState.value){
                is Screen.SignUpScreen -> {
                    SignUpScreen()
                }
                is Screen.TermsAndConditionScreen -> {
                    TermAndConditionScreen()
                }
                is Screen.LoginScreen -> {
                    LoginScreen()
                }
                is Screen.HomeScreen -> {
                    HomeScreen()
                }
                is Screen.ForgetPassword -> {
                    ForgetPassword()
                }
                is Screen.SplashScreen -> {
                    SplashScreen()
                }
                is Screen.SettingScreen -> {
                    SettingScreen()
                }
                is Screen.AppointmentScreen -> {
                    AppointmentScreen()
                }
                is Screen.ContactScreen -> {
                    ContactScreen()
                }
                is Screen.AboutUsScreen -> {
                    AboutUsScreen()
                }
                is Screen.ProfessionalScreen -> {
                    ProfessionalScreen()
                }
                is Screen.EditProfileScreen -> {
                    EditProfileScreen()
                }
                is Screen.MyProfileScreen -> {
                    MyProfileScreen()
                }
                is Screen.HealthProgramScreen -> {
                    HealthProgramScreen()
                }
                is Screen.HealthNutritionScreen -> {
                    HealthNutritionScreen()
                }
                is Screen.HealthFitnessScreen -> {
                    HealthFitnessScreen()
                }
                is Screen.AdminHomeScreen -> {
                    AdminHomeScreen()
                }
                is Screen.AdminRefresh -> {
                    AdminRefresh()
                }
                is Screen.Appointment -> {
                    Appointment()
                }
                is Screen.AboutUs -> {
                    AboutUs()
                }


                else -> {}
            }
        }
    }
}