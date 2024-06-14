package com.example.nativeapp.presentation.screens.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nativeapp.R
import com.example.nativeapp.presentation.components.HeadingTextComponent
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.example.nativeapp.presentation.navigation.SystemBackButtonHandler

@Composable
fun TermAndConditionScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            item {
                HeadingTextComponent(value = stringResource(id = R.string.term_and_condition_header))
                Spacer(modifier = Modifier.height(20.dp))
                TermConditionText(
                    stringResource(id = R.string.acceptance_terms),
                    stringResource(id = R.string.acceptance_terms_descri)
                )
                TermConditionText(
                    stringResource(id = R.string.description_service),
                    stringResource(id = R.string.description_service_descri)
                )
                TermConditionText(
                    stringResource(id = R.string.registration_account),
                    stringResource(id = R.string.registration_account_descrip)
                )
                TermConditionText(
                    stringResource(id = R.string.data_collection_and_use),
                    stringResource(id = R.string.data_collection_and_use_descript)
                )
                TermConditionText(
                    stringResource(id = R.string.data_sharing),
                    stringResource(id = R.string.data_sharing_descript)
                )
                TermConditionText(
                    stringResource(id = R.string.data_security),
                    stringResource(id = R.string.data_security_descript)
                )
                TermConditionText(
                    stringResource(id = R.string.your_rights),
                    stringResource(id = R.string.your_rights_description)
                )
                TermConditionText(
                    stringResource(id = R.string.changes_to_term),
                    stringResource(id = R.string.changes_to_term_description)
                )
                TermConditionText(
                    stringResource(id = R.string.contact_us),
                    stringResource(id = R.string.contact_us_description)
                )

            }
        }
    }
    SystemBackButtonHandler {
        PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
    }

}

@Composable
fun TermConditionText(title: String, description: String) {
    Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = description,
        style = MaterialTheme.typography.titleSmall,
        textAlign = TextAlign.Justify
    )
    Spacer(modifier = Modifier.height(20.dp))

}