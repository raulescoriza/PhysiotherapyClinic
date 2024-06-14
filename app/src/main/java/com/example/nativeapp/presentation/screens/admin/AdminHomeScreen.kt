package com.example.nativeapp.presentation.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nativeapp.R
import com.example.nativeapp.presentation.components.AdminAppToolBar
import com.example.nativeapp.presentation.components.AppToolBar
import com.example.nativeapp.presentation.components.EmailFieldComponent
import com.example.nativeapp.presentation.components.MyTextFieldComponent
import com.example.nativeapp.presentation.components.NumberAndTextFieldComponent
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.example.nativeapp.presentation.navigation.SystemBackButtonHandler
import com.example.nativeapp.presentation.screens.drawer.TitleTextLogOutDialog
import com.example.nativeapp.presentation.viewmodels.AdminViewModel
import com.example.nativeapp.presentation.viewmodels.EditProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * It only shown when the admin log in. It contains all the documents from the db
 * it has one main icon to show all the data from each document. also 2 buttons, one for edit that
 * will show a dialog if the admin has to make any change and other icon to delete any document
 * (document are equals to account)
 */
@Composable
fun AdminHomeScreen(
    editProfileViewModel: EditProfileViewModel = viewModel()
) {
    val logout = remember { mutableStateOf(false) }

    // Scaffold is like a template, it has different parameters
    Scaffold(
        // topBar is related to the navigationTopBar, AppToolBar() contains
        topBar = {
            AdminAppToolBar(
                toolbarTitle = stringResource(id = R.string.users_database),
                onReload = {
                    PostOfficeAppRouter.navigateTo(Screen.AdminRefresh)
                },
                logoutButtonClicked = {
                    logout.value = true
                }
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
            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {

                    item {
                        AdminTableView()
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

/**
 * This method store all the states of modifying datas from each
 */
@Composable
fun AdminTableView(
    adminViewModel: AdminViewModel = viewModel()
) {
    // It stores the different accounts added in the database
    var documentsList = remember { mutableStateOf<List<String>>(emptyList()) }

    // It shows data from each user, it also changes the color of the icon
    var control = remember { mutableStateOf(false) }

    // State to recognize if the admin delete any user
    var deletionTrigger by remember { mutableStateOf(false) }

    // To show or hide the dialog
    var openEditDialog by remember { mutableStateOf(false) }
    var openDeleteDialog by remember { mutableStateOf(false) }
    var openAddUserDialog by remember { mutableStateOf(false) }

    // for saving and sending the new states
    val name = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val birthdate = remember { mutableStateOf("") }
    val appointment = remember { mutableStateOf("") }
    var profileSelected by remember { mutableStateOf("") }

    // To store the data from the new user
    val newUserName = remember { mutableStateOf("") }
    val newUserSurname = remember { mutableStateOf("") }
    val newUserEmail = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val db = Firebase.firestore
        val collectionRef = db.collection("users")

        collectionRef.get()
            .addOnSuccessListener { result ->
                val documents = result.documents
                val list = documents.map { it.id }
                documentsList.value = list
            }
            .addOnFailureListener { exception ->
            }
    }

    // It charge again the documents once one of them have been deleted
    LaunchedEffect(deletionTrigger) {
        val db = Firebase.firestore
        val collectionRef = db.collection("users")
        collectionRef.get()
            .addOnSuccessListener { result ->
                val documents = result.documents
                val list = documents.map { it.id }
                documentsList.value = list
            }
            .addOnFailureListener { exception ->
            }
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .weight(0.8f),
                text = stringResource(id = R.string.list_current_users),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(id = R.string.add),
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .weight(0.1f)
                    .clickable {
                        openAddUserDialog = !openAddUserDialog
                    }
            )
            Icon(
                imageVector = if (control.value == false) Icons.Outlined.VisibilityOff else
                    Icons.Outlined.Visibility,
                contentDescription = stringResource(id = R.string.data_visible),
                tint = if (control.value == false) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .weight(0.1f)
                    .clickable {
                        control.value = !control.value
                    }
            )
        }
        documentsList.value.forEach { document ->
            Divider()
            AdminTable(
                user = document,
                {
                    profileSelected = document
                    openEditDialog = true
                },
                {
                    // To call and delete the document
                    profileSelected = document
                    openDeleteDialog = true
                    deletionTrigger = !deletionTrigger
                },
            )
            if (control.value) {
                StructureDataOfUser(adminViewModel, document)
            }
        }

        // Dialog with different text fields to fill.
        if (openEditDialog) {
            Dialog(
                onDismissRequest = { openEditDialog = false },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.editing),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = profileSelected,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.heightIn(12.dp))
                        MyTextFieldComponent(
                            labelValue = stringResource(id = R.string.name),
                            icon = Icons.Outlined.Person,
                            imeAction = ImeAction.Done,
                            onTextSelected = { name.value = it })
                        MyTextFieldComponent(
                            labelValue = stringResource(id = R.string.surname),
                            icon = Icons.Outlined.Person,
                            imeAction = ImeAction.Done,
                            onTextSelected = { surname.value = it })
                        MyTextFieldComponent(
                            labelValue = stringResource(id = R.string.address),
                            icon = Icons.Outlined.LocationOn,
                            imeAction = ImeAction.Done,
                            onTextSelected = { address.value = it })
                        NumberAndTextFieldComponent(
                            labelValue = stringResource(id = R.string.birthdate),
                            icon = Icons.Outlined.Cake,
                            onTextSelected = { birthdate.value = it })
                        NumberAndTextFieldComponent(
                            labelValue = stringResource(id = R.string.appointment),
                            icon = Icons.Outlined.DateRange,
                            onTextSelected = { appointment.value = it })
                        Spacer(modifier = Modifier.heightIn(12.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            onClick = {
                                openEditDialog = false
                                // Checks if a value has been inserted in the fields, if no one has been inserted
                                // nothing will be changed on the database
                                if (name.value != "") adminViewModel.setNameToFirebase(
                                    profileSelected,
                                    name.value
                                )
                                if (surname.value != "") adminViewModel.setSurnameToFirebase(
                                    profileSelected,
                                    surname.value
                                )
                                if (address.value != "") adminViewModel.setAddressToFirebase(
                                    profileSelected,
                                    address.value
                                )
                                if (birthdate.value != "") adminViewModel.setBirthdateToFirebase(
                                    profileSelected,
                                    birthdate.value.replace("-", "/")
                                )
                                if (appointment.value != "")
                                    adminViewModel.setAppointmentToFirebase(
                                        profileSelected,
                                        appointment.value.replace("-", "/")
                                    )
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.update),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            )
        }

        // Show a dialog with the current user to delete
        if (openDeleteDialog) {
            Dialog(
                onDismissRequest = { openDeleteDialog = false },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.delete_account_admin),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Justify,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = profileSelected,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.heightIn(12.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            onClick = {
                                openDeleteDialog = false
                                // To call the method and delete that account
                                adminViewModel.deleteAccount(profileSelected)
                                // To rebuild the screen
                                deletionTrigger = !deletionTrigger
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
                    }
                }
            )
        }

        // Show a dialog with some fields add information for the new account
        if (openAddUserDialog) {
            Dialog(
                onDismissRequest = { openAddUserDialog = false },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.create_new_account),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.heightIn(12.dp))
                        EmailFieldComponent(
                            labelValue = stringResource(id = R.string.email),
                            icon = Icons.Outlined.Email,
                            onTextSelected = { newUserEmail.value = it })
                        MyTextFieldComponent(
                            labelValue = stringResource(id = R.string.name),
                            icon = Icons.Outlined.Person,
                            imeAction = ImeAction.Next,
                            onTextSelected = { newUserName.value = it })
                        MyTextFieldComponent(
                            labelValue = stringResource(id = R.string.surname),
                            icon = Icons.Outlined.Person,
                            imeAction = ImeAction.Done,
                            onTextSelected = { newUserSurname.value = it })
                        Spacer(modifier = Modifier.heightIn(12.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            onClick = {
                                openAddUserDialog = false
                                // To call the method and delete that account
                                adminViewModel
                                    .addAccount(
                                        newUserEmail.value,
                                        newUserName.value,
                                        newUserSurname.value
                                    )
                                // To rebuild the screen
                                deletionTrigger = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(id = R.string.create),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            )
        }
    }
}

/**
 * This method show's the current email user, with two modifications, update and delete
 * user Parameters is referred to the user that is going to be changed
 */
@Composable
fun AdminTable(user: String, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Person,
            modifier = Modifier.weight(0.1f),
            contentDescription = stringResource(id = R.string.profile),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = user,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(0.8f),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium
        )
        Icon(
            imageVector = Icons.Outlined.Edit,
            modifier = Modifier
                .weight(0.1f)
                .clickable {
                    onEditClick()
                },
            contentDescription = stringResource(id = R.string.edit_profile),
            tint = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Outlined.Delete,
            modifier = Modifier
                .weight(0.1f)
                .clickable {
                    onDeleteClick()
                },
            contentDescription = stringResource(id = R.string.delete),
            tint = MaterialTheme.colorScheme.error
        )
    }
}

/**
 * Contains the structure for each user that will appear in the screen.
 * Each row are compose by using UserBodyStructure()
 */
@Composable
fun StructureDataOfUser(adminViewModel: AdminViewModel = viewModel(), currentUser: String) {
    Column {
        UserBodyStructure(
            title = stringResource(id = R.string.name),
            currentUser = adminViewModel.getNameFromDatabase(currentUser)
        )
        UserBodyStructure(
            title = stringResource(id = R.string.surname),
            currentUser = adminViewModel.getSurnameFromDatabase(currentUser)
        )
        UserBodyStructure(
            title = stringResource(id = R.string.address),
            currentUser = adminViewModel.getAddressFromDatabase(currentUser)
        )
        UserBodyStructure(
            title = stringResource(id = R.string.birthdate),
            currentUser = adminViewModel.getBirthdateFromDatabase(currentUser)
        )
        UserBodyStructure(
            title = stringResource(id = R.string.next_appointment),
            currentUser = adminViewModel.getNextAppointmentFromDatabase(currentUser)
        )
        Spacer(modifier = Modifier.heightIn(16.dp))
    }
}

/**
 * Row component composed by two texts. For key and value
 */
@Composable
fun UserBodyStructure(title: String, currentUser: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = currentUser,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Light
        )
    }
}

