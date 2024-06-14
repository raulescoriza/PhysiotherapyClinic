package com.example.nativeapp.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nativeapp.R
import com.example.nativeapp.presentation.components.AppToolBar
import com.example.nativeapp.presentation.components.DashboardGroupCards
import com.example.nativeapp.presentation.components.NavigationDrawerBody
import com.example.nativeapp.presentation.components.NavigationDrawerHeader
import com.example.nativeapp.presentation.components.TitleTextDashboard
import com.example.nativeapp.presentation.components.WorkOnYouDashboard
import com.example.nativeapp.presentation.viewmodels.home.HomeViewModel
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.example.nativeapp.presentation.navigation.SystemBackButtonHandler
import com.example.nativeapp.presentation.screens.drawer.TitleTextLogOutDialog
import com.example.nativeapp.presentation.viewmodels.EditProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    editProfileViewModel: EditProfileViewModel = viewModel()
) {
    // This variable helps to remember the state of the drawerValue
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // To declare a coroutine to display a suspend fun
    val scope = rememberCoroutineScope()

    val logout = remember { mutableStateOf(false) }

    // This component provides access to an modal screen on the left side with different options
    ModalNavigationDrawer(
        drawerContent = {
            // Component that contains other components
            ModalDrawerSheet {
                // Box which contains the title
                NavigationDrawerHeader()
                // General method which include each option with Icon and description
                // it contains a list of options initialized at HomeViewModel
                NavigationDrawerBody(
                    navigationDrawerItems = homeViewModel.navigationItemList,
                    // there is declared which is the action
                    onNavigationItemClicked = {
                        if (it.id == "home") {
                            scope.launch {
                                drawerState.close()
                            }
                        } else if (it.id == "settings") {
                            PostOfficeAppRouter.navigateTo(Screen.SettingScreen)
                        } else if (it.id == "aboutUs") {
                            PostOfficeAppRouter.navigateTo(Screen.AboutUs)
                        }
                    }
                )
            }
        },
        // where is decided if the drawer component is shown or not
        drawerState = drawerState
    ) {
        // Scaffold is like a template, it has different parameters
        Scaffold(
            // topBar is related to the navigationTopBar, AppToolBar() contains
            topBar = {
                AppToolBar(
                    toolbarTitle = stringResource(id = R.string.home),
                    logoutButtonClicked = {
                        logout.value = true
                    },
                    drawerState = drawerState
                )
            },
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                        TitleTextDashboard(
                            value = "Hi ${
                                homeViewModel.getNameFromDatabase()
                            }",
                            description = stringResource(id = R.string.home_welcome_description)
                        )
                        DashboardGroupCards()
                        WorkOnYouDashboard()
                    }
                }
            }
        }
    }

    if (logout.value) {
        Dialog(
            onDismissRequest = { logout.value = false },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TitleTextLogOutDialog(editProfileViewModel)
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        onClick = {
                            logout.value = false
                            editProfileViewModel.logOut()
                            PostOfficeAppRouter.navigateTo(Screen.SplashScreen)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Logout,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.log_out),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        )
    }
    SystemBackButtonHandler {
        FirebaseAuth.getInstance().signOut()
    }
}