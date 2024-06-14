package com.example.nativeapp.presentation.screens.drawer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nativeapp.R
import com.example.nativeapp.presentation.components.MainStructure
import com.example.nativeapp.presentation.components.PasswordTextFieldComponent
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.example.nativeapp.presentation.navigation.SystemBackButtonHandler
import com.example.nativeapp.presentation.viewmodels.EditProfileViewModel
import java.time.Instant
import java.time.ZoneId

/**
 * Contains 2 sections, top section has an image for the logo, it can be editable. It also has name + surname
 * and the serial number from that profile. The data comes from database.
 * Bottom section contains 2 subsections, the first is about personal data, it contains 3 textFields
 * with name, surname and address, and icon+button for set the age.
 * The second part contains account data, email, password. Both sections has a button for confirm that
 * changes
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditProfileScreen(editProfileViewModel: EditProfileViewModel = viewModel()) {
    MainStructure(
        topNavBarName = stringResource(id = R.string.edit_profile),
        onBackClick = { PostOfficeAppRouter.navigateTo(Screen.SettingScreen) }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                TopEditProfileScreen(editProfileViewModel)
                BottomEditProfile(editProfileViewModel)
            }
        }
    }
    SystemBackButtonHandler {
        PostOfficeAppRouter.navigateTo(Screen.SettingScreen)
    }
}

/**
 * It is a Box with rectangleShape. Inside a row, it contains a box with picture logo + icon for edit
 * On the right side it has 2 text with name + surname obtained from db. Under text with serialnumber
 */
@Composable
fun TopEditProfileScreen(editProfileViewModel: EditProfileViewModel) {
    var openAvatarDialog by remember { mutableStateOf(false) }
    var pictureSelected by remember { mutableStateOf("0") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(128.dp)
            .background(
                MaterialTheme.colorScheme.primary
            )
    )
    {
        Row(
            modifier = Modifier.padding(start = 24.dp, top = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.BottomStart) {
                // La imagen no aparece al principio porque es null, pero cuando se clica en una
                // aparece. tampoco se guarda en la bbdd
                Image(
                    // TODO : BUSCAR COMO HACER PARA QUE AL SELECCIONR UNA FOTO SE GUARDE ESA
                    // TODO: Y SE MUESTRE COMO CAMBIO DE PERFIL
                    painter = painterResource(id = R.drawable.profilepicture),
                    modifier = Modifier
                        .width(88.dp)
                        .height(88.dp)
                        .clip(CircleShape),
                    contentDescription = null
                )
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        openAvatarDialog = !openAvatarDialog
                    }
                )
            }

            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.padding(start = 8.dp)) {
                TitleTextEditProfile(
                    editProfileViewModel.getNameFromDatabase(),
                    editProfileViewModel.getLastNameFromDatabase()
                )
                Spacer(modifier = Modifier.height(4.dp))
                SubTitleTextEditProfile(editProfileViewModel.getSerialNumberFromDatabase())
            }
        }
    }
    if (openAvatarDialog) {
        Dialog(onDismissRequest = { openAvatarDialog = false }
        ) {
            val items = listOf(
                R.drawable.profilepicture,
                R.drawable.profile3,
                R.drawable.profile2,
                R.drawable.profile5,
                R.drawable.profile8,
                R.drawable.profile4,
                R.drawable.profile6,
                R.drawable.profile9,
                R.drawable.profile7
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    text = "Pick your profile picture!",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    columns = GridCells.Fixed(3)
                ) {
                    items(items) { imageResId ->
                        Image(
                            painter = painterResource(id = imageResId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(104.dp)
                                .clip(shape = ShapeDefaults.ExtraLarge)
                                .padding(horizontal = 4.dp)
                                .clickable {
                                    pictureSelected = imageResId.toString()
                                    editProfileViewModel.setProfilePictureToFirebase(imageResId.toString())
                                    openAvatarDialog = false
                                }
                        )
                    }
                }
                Spacer(modifier = Modifier.heightIn(32.dp))
            }
        }
    }
}

/**
 * This method is a row with 2 text between an spacer
 */
@Composable
fun TitleTextEditProfile(name: String, surname: String) {
    Row {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = surname,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * Text for that shows serial number
 */
@Composable
fun SubTitleTextEditProfile(serialNumber: String) {
    Text(
        text = serialNumber,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onPrimary,
        fontWeight = FontWeight.Light
    )
}

/**
 * This method is divided in two sections. First contains personal data
 * It has few variable with contains the data introduced from the textField. If the use confirm
 * it, they will be updated on the database.
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomEditProfile(editProfileViewModel: EditProfileViewModel) {
    // This variables store the data from the db
    var newName by remember { mutableStateOf("") }
    newName = editProfileViewModel.getNameFromDatabase()
    var newSurname by remember { mutableStateOf("") }
    newSurname = editProfileViewModel.getLastNameFromDatabase()
    var newAddress by remember { mutableStateOf("") }
    newAddress = editProfileViewModel.getAddressFromDatabase()
    var newBirthdate by remember { mutableStateOf("") }
    newBirthdate = editProfileViewModel.getbirthdateFromDatabase()
    // Stores the data from the changes on the textField
    val name = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var openDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    // To get the date from the DatePicker
    val date = datePickerState.selectedDateMillis
    // To store the date in a variable
    val dateSelected =
        date?.let { Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate() }
    Column(modifier = Modifier.padding(horizontal = 32.dp, vertical = 12.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BottomTitleTextEditProfile(stringResource(id = R.string.personal_data))
            Button(
                onClick = {
                    // Checks if a value has been inserted in the fields, if no one has been inserted
                    // nothing will be changed on the database
                    if (name.value != "") {
                        editProfileViewModel.setNameToFirebase(name.value)
                        // if a new value has change, it will be stored
                        newName = name.value
                    }
                    if (surname.value != "") {
                        editProfileViewModel.setSurnameToFirebase(surname.value)
                        // if a new value has change, it will be stored
                        newSurname = surname.value
                    }
                    if (address.value != "") {
                        editProfileViewModel.setAddressToFirebase(address.value)
                        // if a new value has change, it will be stored
                        newAddress = address.value
                    }
                },
                colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(id = R.string.update),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        // For each textField, first parameter for the value name, icon related to the context,
        // onTextSelected it will store each event introduced by the user.
        // textInfoSupport is message below with the current data
        TextFieldComponentPreviousData(
            labelValue = stringResource(id = R.string.name),
            icon = Icons.Outlined.Person,
            imeAction = ImeAction.Done,
            onTextSelected = { name.value = it },
            // The text info is the last data from the database, if it changes to any new, it will
            // change on the screen once the user has updated it
            textInfoSupport = newName
        )
        TextFieldComponentPreviousData(
            labelValue = stringResource(id = R.string.surname),
            icon = Icons.Outlined.Person,
            imeAction = ImeAction.Done,
            onTextSelected = { surname.value = it },
            textInfoSupport = newSurname
        )
        TextFieldComponentPreviousData(
            labelValue = stringResource(id = R.string.address),
            icon = Icons.Outlined.LocationOn,
            imeAction = ImeAction.Done,
            onTextSelected = { address.value = it },
            textInfoSupport = // Checks if at the db is some value
            if (editProfileViewModel.getAddressFromDatabase() != "null" ||
                editProfileViewModel.getAddressFromDatabase() != ""
            ) {
                newAddress
            } else {
                stringResource(id = R.string.information_not_added)
            }

        )
        Spacer(modifier = Modifier.heightIn(4.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Icon(
                imageVector = Icons.Outlined.Cake,
                contentDescription = stringResource(id = R.string.birthdate)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Button(
                onClick = { openDialog = true },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Text(
                    text = stringResource(id = R.string.set_birthdate),
                    style = MaterialTheme.typography.bodyLarge
                )
                if (openDialog) {
                    DatePickerDialog(
                        // It close the dialog
                        onDismissRequest = { openDialog = false },
                        confirmButton = {
                            // It close the dialog
                            Button(onClick = { openDialog = false }) {
                                Text(text = stringResource(id = R.string.confirm_date))
                                // It saves the date in the database
                                editProfileViewModel.setBirthdateToFirebase(dateSelected.toString())
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
            }
            Text(
                text = stringResource(id = R.string.current) +
                        if (editProfileViewModel.getbirthdateFromDatabase() != "null") {
                            editProfileViewModel.getbirthdateFromDatabase()
                        } else {
                            stringResource(id = R.string.information_not_added)
                        },
                modifier = Modifier.padding(start = 12.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.heightIn(16.dp))
        Divider()
        Spacer(modifier = Modifier.heightIn(12.dp))

        // Second Section

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BottomTitleTextEditProfile(stringResource(id = R.string.account_data))
            Button(
                onClick = {
                    // First checks if the password is not empty and if it is greater than 7 characters
                    if (password.value != "" && password.value.length > 7) {
                        editProfileViewModel.setNewPasswordToFirebase(password.value)
                        // Show a toast with the confirmation of that update
                    }
                },
                colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(id = R.string.update),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        PasswordTextFieldComponent(
            labelValue = stringResource(id = R.string.new_password),
            icon = Icons.Outlined.Lock,
            onTextSelected = {
                password.value = it
            }
        )
    }
}

@Composable
fun BottomTitleTextEditProfile(value: String) {
    Text(
        text = value,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold
    )
}

/**
 * Basic OutlinedTextField for sign up and login screen, contains icon and text
 * It has been configured to not allow numbers, just text from a to z. Also it start with
 * Capital letter. It will show the current value from the database.
 */
@Composable
fun TextFieldComponentPreviousData(
    labelValue: String,
    icon: ImageVector,
    onTextSelected: (String) -> Unit,
    imeAction: ImeAction,
    textInfoSupport: String,
    errorStatus: Boolean = false
) {
    // remember is used to store the value of this variable. So during the recomposition of the
    // screen it will remember the previous value
    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        value = textValue.value,
        onValueChange = {
            if (it.matches(Regex("[a-zA-Z ]*"))) {
                textValue.value = it
                onTextSelected(it)
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Text
        ),
        singleLine = true,
        maxLines = 1,
        label = { Text(text = labelValue) },
        leadingIcon = {
            Icon(
                imageVector = icon, contentDescription = stringResource(
                    id = R.string.profile
                )
            )
        },
        // checks if the status error is true or not. This value is inverted cause of configuration
        // in the validation method
        isError = errorStatus,
        supportingText = {
            Text(
                text = stringResource(id = R.string.current) + textInfoSupport,
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal
            )
        }
    )
}