package com.example.nativeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nativeapp.R
import com.example.nativeapp.presentation.viewmodels.home.HomeViewModel
import com.example.nativeapp.presentation.viewmodels.home.NavigationItem
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import kotlinx.coroutines.launch


/**
 * Method for create the text for the Sign up scree. The parameter value refers to string shown
 */
@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center
    )
}

/**
 * Method for create the text header for the Sign up scree. The parameter value refers to string shown
 */
@Composable
fun HeadingTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth(),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center
    )
}

/**
 * Basic OutlinedTextField for sign up and login screen, contains icon and text
 * It has been configured to not allow numbers, just text from a to z. Also it start with
 * Capital letter
 */
@Composable
fun MyTextFieldComponent(
    labelValue: String,
    icon: ImageVector,
    onTextSelected: (String) -> Unit,
    imeAction: ImeAction,
    errorStatus: Boolean = false
) {
    // remember is used to store the value of this variable. So during the recomposition of the
    // screen it will remember the previous value
    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
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
        isError = errorStatus
    )
}

/**
 * Basic OutlinedTextField for sign up and login screen, contains icon and text,
 * there's no restriction for number and letters
 */
@Composable
fun NumberAndTextFieldComponent(
    labelValue: String,
    icon: ImageVector,
    onTextSelected: (String) -> Unit
) {
    // remember is used to store the value of this variable. So during the recomposition of the
    // screen it will remember the previous value
    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Number
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
        }
    )
}

/**
 * Same method that MyTextField with the difference of that KeyboardType is for email in this case and
 * it start without capital letter
 */
@Composable
fun EmailFieldComponent(
    labelValue: String,
    icon: ImageVector,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    // remember is used to store the value of this variable. So during the recomposition of the
    // screen it will remember the previous value
    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Email
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
        isError = errorStatus
    )
}

/**
 * Contains the outlinedtextfild component, with icon, text and icon. The text is stabled as not visible
 * by the keyboardOptions. Last icon is included to show and hide the password visibility
 */
@Composable
fun PasswordTextFieldComponent(
    labelValue: String,
    icon: ImageVector,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    // remember is used to store the value of this variable. So during the recomposition of the
    // screen it will remember the previous value
    val password = remember { mutableStateOf("") }

    // In order to remember the state of password's visibility
    val passwordVisible = remember { mutableStateOf(false) }

    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        label = { Text(text = labelValue) },
        leadingIcon = {
            Icon(
                imageVector = icon, contentDescription = stringResource(
                    id = R.string.profile
                )
            )
        },
        // clearFocus help to get out from the password textfield once done button has been clicked
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        // To implement the icon of visibility
        trailingIcon = {
            // For changing the state of the visibility icon
            val iconImage =
                if (passwordVisible.value) {
                    Icons.Outlined.Visibility
                } else {
                    Icons.Outlined.VisibilityOff
                }
            val description =
                if (passwordVisible.value) {
                    stringResource(
                        id = R.string.hide_password
                    )
                } else {
                    stringResource(
                        id = R.string.show_password
                    )
                }
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        // transforms the visual representation of the input value. PasswordVisualTransformation
        // if it's visible it will show the data, otherwise it won't. It won't show it by default
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else
            PasswordVisualTransformation(),
        isError = errorStatus,
        supportingText = {
            if (password.value.length in 2..7) {
                Text(text = stringResource(id = R.string.security_password))
            }
        }
    )
}

/**
 * Checkbox component with string of terms and conditions
 */
@Composable
fun CheckboxComponent(
    value: String,
    onTextSelected: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val checkedState = remember {
            mutableStateOf(false)
        }

        Checkbox(checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
                onCheckedChange.invoke(it)
            })
        ClickableTextComponent(value = value, onTextSelected)
    }
}

/**
 * Text of terms and conditions which includes the complex navigation between screens
 * ** need to understand more of it
 */
@Composable
fun ClickableTextComponent(value: String, onTextSelected: (String) -> Unit) {
    val initialText = "By continuing you accept our "
    val privacyPolicyText = "Privacy Policy"
    val andText = " and "
    val termAndConditionText = "Term of Use"

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(MaterialTheme.colorScheme.onBackground)) {
            pushStringAnnotation(tag = initialText, annotation = initialText)
            append(initialText)
        }
        withStyle(style = SpanStyle(MaterialTheme.colorScheme.primary)) {
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        withStyle(style = SpanStyle(MaterialTheme.colorScheme.onBackground)) {
            pushStringAnnotation(tag = andText, annotation = andText)
            append(andText)
        }
        withStyle(style = SpanStyle(MaterialTheme.colorScheme.primary)) {
            pushStringAnnotation(tag = termAndConditionText, annotation = termAndConditionText)
            append(termAndConditionText)
        }
    }
    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also { span ->

                if ((span.item == termAndConditionText) || (span.item == privacyPolicyText)) {
                    onTextSelected(span.item)
                }
            }
    })
}

/**
 * Simple button that inside it contains a box (with a gradient brush for color) with a text
 */
@Composable
fun ButtonComponent(value: String, onButtonClicked: () -> Unit, isEnable: Boolean = false) {
    Button(
        onClick = {
            onButtonClicked.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        enabled = isEnable
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primary
                        )
                    ),
                    shape = RoundedCornerShape(50.dp)
                ), contentAlignment = Alignment.Center
        )
        {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun DoubleTextWithClickable(
    value1: String,
    firstColor: Color,
    value2: String,
    secondColor: Color,
    onTextSelected: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            text = value1,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            color = firstColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value2,
            modifier = Modifier.clickable {
                onTextSelected()
            },
            style = MaterialTheme.typography.titleSmall,
            color = secondColor,
            fontWeight = FontWeight.SemiBold,
            textDecoration = TextDecoration.Underline
        )
    }
}

/**
 * Normal text function with underline modification
 */
@Composable
fun UnderLinedTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                PostOfficeAppRouter.navigateTo(Screen.ForgetPassword)
            }
            .heightIn(min = 40.dp)
            ,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}

/**
 * This method contain TopAppBar component with 3 parameters, one for the title, the second is
 * for button functionality and the third for the drawer state.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolBar(toolbarTitle: String, logoutButtonClicked: () -> Unit, drawerState: DrawerState) {
    // This coroutine helps to reconstruct the view
    val scope = rememberCoroutineScope()
    // This method require at least the parameter title, this is a component that can be used
    // on Scaffold topBar
    // Contains title, navigateIcon (menu icon), and actions (log out icon)
    TopAppBar(
        title = {
            Text(
                text = toolbarTitle,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        },
        // navigationIcon is related to the icon who will appear on left side of the TopAppBar
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = stringResource(id = R.string.menu),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .clickable {
                        // Once menu is clicked, it will open the drawerState
                        scope.launch {
                            drawerState.open()
                        }
                    }
                    .padding(12.dp)
            )
        },
        colors = TopAppBarDefaults
            .topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
        // actions is related to the icon on the right side
        actions = {
            // on click it will do the exit action
            IconButton(onClick = { logoutButtonClicked() }) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = stringResource(id = R.string.log_out),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}

/**
 * This method contain TopAppBar component with 3 parameters, one for the title, the second is
 * for button functionality and the third for the drawer state.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAppToolBar(toolbarTitle: String, onReload: () -> Unit, logoutButtonClicked: () -> Unit) {
    // This method require at least the parameter title, this is a component that can be used
    // on Scaffold topBar
    // Contains title, navigateIcon (menu icon), and actions (log out icon)
    TopAppBar(
        title = {
            Text(
                text = toolbarTitle,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        },
        // navigationIcon is related to the icon who will appear on left side of the TopAppBar
        navigationIcon = {
            IconButton(onClick = { onReload() }) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = stringResource(id = R.string.reload),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                )
            }
        },
        colors = TopAppBarDefaults
            .topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
        // actions is related to the icon on the right side
        actions = {
            // on click it will do the exit action
            IconButton(onClick = { logoutButtonClicked() }) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = stringResource(id = R.string.log_out),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}

/**
 * Contains the text for the title
 */
@Composable
fun NavigationDrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary
                    )
                )
            )
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.options),
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start
        )
    }
}

/**
 * LazyColum with a list of NavigationItems, there's required a list parameter with navigation items
 */
@Composable
fun NavigationDrawerBody(
    navigationDrawerItems: List<NavigationItem>,
    onNavigationItemClicked: (NavigationItem) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(navigationDrawerItems) {
            NavigationItemRow(navigationItem = it, onNavigationItemClicked)
        }
    }
}

/**
 * Contains the structure of the options with icon, space, text
 */
@Composable
fun NavigationItemRow(
    navigationItem: NavigationItem,
    onNavigationItemClicked: (NavigationItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onNavigationItemClicked.invoke(navigationItem)
            }
            .padding(16.dp)
    )
    {
        Icon(
            imageVector = navigationItem.icon,
            modifier = Modifier.size(28.dp),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = navigationItem.description
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = navigationItem.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )

    }
}