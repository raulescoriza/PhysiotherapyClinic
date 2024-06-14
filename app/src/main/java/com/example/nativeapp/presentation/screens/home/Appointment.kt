package com.example.nativeapp.presentation.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nativeapp.R
import com.example.nativeapp.presentation.components.MainStructure
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.example.nativeapp.presentation.navigation.SystemBackButtonHandler
import com.example.nativeapp.presentation.viewmodels.AppointmentViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Appointment(appointmentViewModel: AppointmentViewModel = viewModel()) {
    var state by remember { mutableStateOf(false) }
    var dateSelected by remember { mutableStateOf("a") }
    MainStructure(topNavBarName = stringResource(id = R.string.appointment), onBackClick = {PostOfficeAppRouter.navigateTo(Screen.HomeScreen)}) {
        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                Button(onClick = { state = !state }) {
                    Text(text = "Book appointment")
                }

                if (state) dateSelected = Fecha()



                if (
                    appointmentViewModel.getDateFromDatabase() == "null" ||
                    appointmentViewModel.getDateFromDatabase() == ""
                ) {
                    Text(text = dateSelected)
                }
            }
        }
    }
    SystemBackButtonHandler {
        PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Fecha(appointmentViewModel: AppointmentViewModel = viewModel()): String {
    val state = rememberDatePickerState()
    var show by remember { mutableStateOf(true) }
    var dateSelected by remember { mutableStateOf("") }
    if (show) {
        DatePickerDialog(
            onDismissRequest = { show = false },
            confirmButton = {
                Button(onClick = { show = false }) {
                    Text(text = stringResource(id = R.string.confirm_date))
                }
            }) {
            DatePicker(state = state)
        }
    }
    var date = state.selectedDateMillis
    date?.let {
        // To save the date
        val localDate = Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
        dateSelected = "${localDate.dayOfMonth}/${localDate.monthValue}/${localDate.year}"
    }

    appointmentViewModel.setDataToFirebase(dateSelected)

    return dateSelected
}

