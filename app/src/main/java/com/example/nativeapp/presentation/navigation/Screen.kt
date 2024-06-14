package com.example.nativeapp.presentation.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

// This class is used to navigate between screens
sealed class Screen (){

    // There are declared the screens composable methods (the screen you want to show)
    object SignUpScreen: Screen()
    object TermsAndConditionScreen: Screen()
    object LoginScreen: Screen()
    object HomeScreen: Screen()
    object ForgetPassword: Screen()
    object SplashScreen: Screen()
    object SettingScreen: Screen()
    object AppointmentScreen: Screen()
    object ContactScreen: Screen()
    object AboutUsScreen: Screen()
    object ProfessionalScreen: Screen()
    object EditProfileScreen: Screen()
    object MyProfileScreen: Screen()
    object HealthProgramScreen: Screen()
    object HealthNutritionScreen: Screen()
    object HealthFitnessScreen: Screen()
    object AdminHomeScreen: Screen()
    object AdminRefresh: Screen()
    object Appointment: Screen()

    object AboutUs: Screen()
}

// In order to call the router it has to be called by PostOfficeAppRouter, and the destination
// will change the currentScreen
object PostOfficeAppRouter{

    val currentScreen: MutableState<Screen> = mutableStateOf(Screen.SplashScreen)

    // This function request a destiny and the current screen will be change up to that value
    fun navigateTo(destination: Screen){
        currentScreen.value = destination
    }
}