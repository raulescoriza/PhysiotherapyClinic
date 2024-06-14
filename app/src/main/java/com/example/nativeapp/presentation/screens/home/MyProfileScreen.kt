package com.example.nativeapp.presentation.screens.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Numbers
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
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
import java.time.Instant
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyProfileScreen() {
    MainStructure(topNavBarName = stringResource(id = R.string.my_profile), onBackClick = {PostOfficeAppRouter.navigateTo(Screen.HomeScreen)}) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                TopMyProfileScreen()
                BottomMyProfileScreen()
            }
        }
    }
    SystemBackButtonHandler {
        PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
    }
}


@Composable
fun TopMyProfileScreen(editProfileViewModel: EditProfileViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)

    ) {
        Spacer(modifier = Modifier.height(60.dp))
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = Modifier.padding(start = 32.dp, top = 32.dp, end = 32.dp, bottom = 120.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.rehab_club),
                    modifier = Modifier.padding(24.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Start
                )
                Icon(
                    imageVector = Icons.Filled.Healing,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = stringResource(id = R.string.rehab_club)
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = editProfileViewModel.getNameFromDatabase().toUpperCase() + " "
                        + editProfileViewModel.getLastNameFromDatabase().toUpperCase(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 24.dp, bottom = 4.dp),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start
            )
            Text(
                text = editProfileViewModel.getSerialNumberFromDatabase(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 24.dp, bottom = 16.dp),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color =  MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomMyProfileScreen(editProfileViewModel: EditProfileViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth().padding(end = 36.dp, top = 16.dp, bottom = 4.dp), contentAlignment = Alignment.CenterEnd){
            Icon(
                imageVector = Icons.Outlined.Edit,
                modifier = Modifier.clickable {
                    PostOfficeAppRouter.navigateTo(Screen.EditProfileScreen)
                },
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(id = R.string.edit_profile)
            )
        }
        Divider(modifier = Modifier.padding(horizontal = 120.dp))
        Spacer(modifier = Modifier.height(40.dp))


        Spacer(modifier = Modifier.width(4.dp))
        TextEditProfile(
            value = editProfileViewModel.getNameFromDatabase().toUpperCase()
                    + " "
                    + editProfileViewModel.getLastNameFromDatabase().toUpperCase(),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            Icons.Outlined.Person
        )
        TextEditProfile(
            value = editProfileViewModel.getSerialNumberFromDatabase(),
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground,
            Icons.Outlined.CreditCard
        )
        DividerEditProfile()
        BirthdateEditProfile(
            stringResource(id = R.string.birthdate),
            editProfileViewModel
        )
        DividerEditProfile()
        AddressEditProfile(stringResource(id = R.string.address), editProfileViewModel)
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun DividerEditProfile() {
    Divider(
        modifier = Modifier.padding(
            start = 20.dp,
            end = 20.dp,
            top = 12.dp,
            bottom = 12.dp
        )
    )
}

/**
 * This method store the date selected in the database and show it to the user
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdateEditProfile(title: String, editProfileViewModel: EditProfileViewModel) {
    var openDialog by remember { mutableStateOf(false) }
    var datePickerState = rememberDatePickerState()
    TextEditProfile(
        value = title,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        Icons.Outlined.Cake
    )
    if (editProfileViewModel.getbirthdateFromDatabase() != "null" || editProfileViewModel.getbirthdateFromDatabase() == null) {
        SubtextEditProfile(
            value = editProfileViewModel.getbirthdateFromDatabase(),
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground
        )
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubtextEditProfile(
                value = stringResource(id = R.string.not_registered_yet),
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(120.dp))
            Icon(
                imageVector = Icons.Filled.AddCircleOutline,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        openDialog = true
                    },
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )

            // It shows the dialog of date picker
            if (openDialog) {
                DatePickerDialog(
                    // It close the dialog
                    onDismissRequest = { openDialog = false },
                    confirmButton = {
                        // It close the dialog
                        Button(onClick = { openDialog = false }) {
                            Text(text = stringResource(id = R.string.confirm_date))
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
            // To get the date from the DatePicker
            val date = datePickerState.selectedDateMillis
            // To store the date in a variable
            val dateSelected =
                date?.let { Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate() }
            // It saves the date in the database
            editProfileViewModel.setBirthdateToFirebase(dateSelected.toString())
        }
    }
}

/**
 * This method store the address wrote in the database and show it to the user
 */
@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressEditProfile(title: String, editProfileViewModel: EditProfileViewModel) {
    var openDialog by remember { mutableStateOf(false) }
    TextEditProfile(
        value = title,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        Icons.Outlined.LocationOn
    )
    if (editProfileViewModel.getAddressFromDatabase() != "null" || editProfileViewModel.getAddressFromDatabase() == null) {
        SubtextEditProfile(
            value = editProfileViewModel.getAddressFromDatabase(),
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground
        )
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubtextEditProfile(
                value = stringResource(id = R.string.not_registered_yet),
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(120.dp))
            Icon(
                imageVector = Icons.Filled.AddCircleOutline,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        openDialog = true
                    },
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )

            // It shows the dialog of date picker
            if (openDialog) {
                Dialog(
                    onDismissRequest = { openDialog = false },
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                                .clip(ShapeDefaults.Medium)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Introduce your address")
                            MyTextFieldComponent(
                                labelValue = stringResource(id = R.string.address),
                                icon = Icons.Outlined.LocationOn,
                                imeAction = ImeAction.Done,
                                onTextSelected = {
                                    editProfileViewModel.setAddressToFirebase(it).toString()
                                })
                            Button(onClick = { openDialog = false }) {
                                Text(text = stringResource(id = R.string.confirm))
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TextEditProfile(value: String, fontWeight: FontWeight?, color: Color, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 26.dp, bottom = 12.dp)
    ) {
        Icon(
            imageVector = icon,
            tint = color,
            contentDescription = stringResource(id = R.string.profile)
        )
        Text(
            text = value,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp),
            style = MaterialTheme.typography.titleMedium,
            color = color,
            fontWeight = fontWeight,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun SubtextEditProfile(value: String, fontWeight: FontWeight?, color: Color) {
    Text(
        text = value,
        modifier = Modifier
            .padding(start = 26.dp, bottom = 12.dp, end = 26.dp),
        style = MaterialTheme.typography.titleMedium,
        color = color,
        fontWeight = fontWeight,
        textAlign = TextAlign.Start
    )
}