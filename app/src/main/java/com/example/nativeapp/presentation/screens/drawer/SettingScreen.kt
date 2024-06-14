package com.example.nativeapp.presentation.screens.drawer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nativeapp.R
import com.example.nativeapp.presentation.components.MainStructure
import com.example.nativeapp.presentation.components.MyTextFieldComponent
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.example.nativeapp.presentation.navigation.SystemBackButtonHandler
import com.example.nativeapp.presentation.viewmodels.EditProfileViewModel
import com.example.nativeapp.presentation.viewmodels.login.LoginUIEvent
import com.example.nativeapp.presentation.viewmodels.login.LoginViewModel

/**
 * Show an activity divided in two sections. The first one shows 4 options, editProfile, support,
 * log out and delete account. The second section show 2 options, one for change dark mode and the
 * other for notifications
 */
@Composable
fun SettingScreen(editProfileViewModel: EditProfileViewModel = viewModel()) {
    var openDialogLogOut by remember { mutableStateOf(false) }
    // Stores the value from opening or not the dialog
    var openDialog by remember { mutableStateOf(false) }
    // Same structure that others screens
    MainStructure(topNavBarName = stringResource(id = R.string.settings), onBackClick = {PostOfficeAppRouter.navigateTo(Screen.HomeScreen)}) {
        LazyColumn {
            item {
                //CanvasUsing()
                BasicTitleCard(stringResource(id = R.string.account))
                BasicTextDoubleIconComponent(
                    Icons.Outlined.Edit,
                    stringResource(id = R.string.edit_profile),
                    firstIconTint = MaterialTheme.colorScheme.primary,
                    Icons.Outlined.ArrowForward,
                    onClick = {
                        // On click moves the route to EditProfile
                        PostOfficeAppRouter.navigateTo(Screen.EditProfileScreen)
                    }
                )
                BasicTextDoubleIconComponent(
                    Icons.Outlined.SupportAgent,
                    stringResource(id = R.string.support),
                    firstIconTint = MaterialTheme.colorScheme.primary,
                    Icons.Outlined.ArrowForward,
                    onClick = {
                        PostOfficeAppRouter.navigateTo(Screen.ContactScreen)
                    }
                )
                BasicTextDoubleIconComponent(
                    Icons.Outlined.Logout,
                    stringResource(id = R.string.log_out),
                    firstIconTint = MaterialTheme.colorScheme.primary,
                    Icons.Outlined.ArrowForward,
                    onClick = {
                        /*PostOfficeAppRouter.navigateTo(Screen.SplashScreen)
                        editProfileViewModel.logOut()*/
                        openDialogLogOut = true
                    }
                )
                BasicTextDoubleIconComponent(
                    Icons.Outlined.Delete,
                    stringResource(id = R.string.delete_account),
                    firstIconTint = MaterialTheme.colorScheme.error,
                    Icons.Outlined.ArrowForward,
                    onClick = {
                        openDialog = true
                    }
                )

                if (openDialogLogOut) {
                    Dialog(
                        onDismissRequest = { openDialogLogOut = false },
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background)
                                    .clip(shape = ShapeDefaults.Medium)
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                TitleTextLogOutDialog(editProfileViewModel)
                                Button(
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                    onClick = {
                                        openDialogLogOut = false
                                        editProfileViewModel.logOut()
                                        PostOfficeAppRouter.navigateTo(Screen.SplashScreen)
                                    }
                                ){
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
                        })
                }


                // This structure shows a personalized dialog with the option of delete the current
                // account
                if (openDialog) {
                    Dialog(
                        onDismissRequest = { openDialog = false },
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background)
                                    .clip(shape = ShapeDefaults.Medium)
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                TitleTextDeleteDialog(editProfileViewModel)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    Button(
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                        onClick = {
                                            openDialog = false
                                            editProfileViewModel.deleteAccount()
                                            PostOfficeAppRouter.navigateTo(Screen.SplashScreen)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Delete,
                                            contentDescription = null
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = stringResource(id = R.string.delete),
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                    Button(onClick = { openDialog = false }) {
                                        Text(
                                            text = stringResource(id = R.string.cancel),
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
    SystemBackButtonHandler {
        PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
    }
}


@Preview(showBackground = true)
@Composable
fun CanvasUsing() {
    val backgroundColor = MaterialTheme.colorScheme.inverseOnSurface
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawRoundRect(
                color = backgroundColor, // Puedes cambiar el color según tus necesidades
                topLeft = Offset(0f, size.height - 1200f), // Posición inicial del rectángulo
                size = Size(size.width, size.height), // Tamaño del rectángulo (ancho de la pantalla, altura deseada)
                cornerRadius = CornerRadius(32f, 32f)
                )
        }
    }


}

@Composable
fun TitleTextLogOutDialog(editProfileViewModel: EditProfileViewModel) {
    Text(
        text = stringResource(id = R.string.hi) + editProfileViewModel.getNameFromDatabase(),
        style = MaterialTheme.typography.displaySmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.heightIn(8.dp))
    Text(modifier = Modifier.padding(horizontal = 12.dp),
        text = stringResource(id = R.string.start_log_out_message),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Medium
    )
    Spacer(modifier = Modifier.heightIn(8.dp))
    Text(
        text = stringResource(id = R.string.log_out_message),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Light
    )
    Spacer(modifier = Modifier.heightIn(16.dp))
}

@Composable
fun TitleTextDeleteDialog(editProfileViewModel: EditProfileViewModel) {
    Text(
        text = stringResource(id = R.string.hi) + editProfileViewModel.getNameFromDatabase(),
        style = MaterialTheme.typography.displaySmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.heightIn(8.dp))
    Text(
        text = stringResource(id = R.string.start_delete_message),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Medium
    )
    Spacer(modifier = Modifier.heightIn(8.dp))
    Text(
        text = stringResource(id = R.string.delete_message),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Light
    )
    Spacer(modifier = Modifier.heightIn(16.dp))
}

/**
 * This method is the construction of icon + text + icon. Also it can change the icon tint and
 * allows one action when the user click the component
 */
@Composable
fun BasicTextDoubleIconComponent(
    firstIcon: ImageVector,
    title: String,
    firstIconTint: Color,
    lastIcon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
            .padding(16.dp)
    ) {
        Icon(
            imageVector = firstIcon,
            contentDescription = null,
            modifier = Modifier.weight(0.2f),
            tint = firstIconTint
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(0.6f)
        )
        Icon(
            modifier = Modifier.weight(0.2f),
            imageVector = lastIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )

    }
    Divider(Modifier.padding(start = 20.dp, end = 20.dp))
}

/**
 * This method contains icon + text + switcher. It allows to add the title text and the icon type
 * by parameters
 */
@Composable
fun BasicTextIconSwitchComponent(firstIcon: ImageVector, title: String) {
    var checked by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = firstIcon,
            contentDescription = null,
            modifier = Modifier.weight(0.2f),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(0.6f)
        )
        Switch(
            modifier = Modifier.weight(0.2f),
            checked = checked,
            onCheckedChange = {
                checked = it
            })
    }
    Divider(Modifier.padding(start = 20.dp, end = 20.dp))
}

/**
 * This method show a card that contains the title of each section
 */
@Composable
fun BasicTitleCard(title: String) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 20.dp, top = 12.dp, bottom = 12.dp)
        )
    }
}

@Composable
fun TestInteractiveComponent(
    firstIcon: ImageVector,
    title: String,
    lastIcon: ImageVector,
    loginViewModel: LoginViewModel = viewModel()
) {
    val state = remember {
        mutableStateOf(false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = firstIcon,
            contentDescription = null,
            modifier = Modifier.weight(0.2f),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(0.6f)
        )
        IconButton(onClick = { state.value = true }, modifier = Modifier.weight(0.2f)) {
            Icon(
                imageVector = lastIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }

        if (state.value) {
            MyTextFieldComponent(labelValue = "Change Password",
                icon = Icons.Outlined.Email,
                imeAction = ImeAction.Done,
                onTextSelected = { loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it)) }
            )
        }
    }
    Divider(Modifier.padding(start = 20.dp, end = 20.dp))
}
