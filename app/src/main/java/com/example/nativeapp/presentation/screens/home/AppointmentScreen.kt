package com.example.nativeapp.presentation.screens.home

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nativeapp.R
import com.example.nativeapp.data.api.ApiService
import com.example.nativeapp.presentation.components.MainStructure
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.example.nativeapp.presentation.navigation.SystemBackButtonHandler
import com.example.nativeapp.presentation.viewmodels.AppointmentViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.time.ZoneId

@Composable
fun AppointmentScreen(appointmentViewModel: AppointmentViewModel = viewModel()) {
    MainStructure(topNavBarName = stringResource(id = R.string.appointment), onBackClick = {PostOfficeAppRouter.navigateTo(Screen.HomeScreen)}) {
        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                NewAppointmentCard(appointmentViewModel)
            }
        }
    }
    SystemBackButtonHandler {
        PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
    }
}


@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewAppointmentCard(appointmentViewModel: AppointmentViewModel) {
    // Date selected by the user
    var dateSelected = ""

    val state = rememberDatePickerState()
    var control by remember { mutableStateOf(false) }
    var isSelected by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Column {
        // This is first Card with a simple title and a button that open the datePickerDialog
        CardAppointmentScreen { showDialog = true }

        // If no one date has been chosen it won't show the date selected
        if (!control) {
            CardWithDate(appointmentViewModel)
        }

        // When the button Calendar is clicked it will show a date picker with the date
        // if the dateSelected is empty, it will be shown again
        if (showDialog && dateSelected == "") {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                DatePickerDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        Button(onClick = { showDialog = false }) {
                            Text(text = stringResource(id = R.string.confirm_date))
                        }
                    }) {
                    DatePicker(state = state)
                }
            }
        }

        // Here is where the date selected for the user will be shown on the screen once it has been
        // picked
        val date = state.selectedDateMillis
        date?.let {
            // To save the date
            val localDate = Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
            // This make control over show or not the date, while no date has been selected, it won't
            // show nothing
            val status = remember { mutableStateOf(true) }
            // this variable saves a boolean as result if the dateSelected is before to the current
            val isBeforeToday = appointmentViewModel.isDateBeforeToday(
                localDate.year,
                localDate.monthValue,
                localDate.dayOfMonth
            )

            // if the dateSelected is before to the current date it will show a toast,
            // the dialog will appear again and dateSelected will be empty
            if (isBeforeToday) {
                dateSelected = ""
                showDialog = true
            } else {
                // if the dateSelected after than the current date
                // this variable will be store the date picked from the calendar
                dateSelected = "${localDate.dayOfMonth}/${localDate.monthValue}/${localDate.year}"
            }


            AppointmentsTitle()
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(start = 12.dp, top = 16.dp, bottom = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(id = R.string.appointment),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(64.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 12.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.next_appointment),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        if (status.value) {
                            Text(
                                modifier = Modifier
                                    .padding(bottom = 12.dp)
                                    .fillMaxWidth(),
                                // If dateSelected is empty it will show that's not possible and open
                                // on the other hand it will show the data selected
                                text =
                                if (dateSelected == "") {
                                    stringResource(id = R.string.date_incorrect)
                                } else {
                                    "${localDate.dayOfMonth}/${localDate.monthValue}/${localDate.year}"
                                },
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
                if (status.value) {
                    ProfessionalToDeal()
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Divider(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp)
                                .fillMaxWidth(),
                            color = MaterialTheme.colorScheme.secondary,
                            thickness = 1.dp
                        )
                        Text(
                            text = stringResource(id = R.string.cancel_appointment),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clickable {
                                    appointmentViewModel.deleteDataToFirebase()
                                    status.value = !status.value
                                }
                        )
                    }

                }
                // if a date was picked, that date will be store in the database
                if (status.value) {
                    appointmentViewModel.setDataToFirebase(dateSelected)
                }
                isSelected = true
                control = true
            }
        }
    }
}

/**
 * Show a card with the date of the next appointment (once the user has selected one and it has been
 * registered in the database)
 */
@Composable
fun CardWithDate(appointmentViewModel: AppointmentViewModel) {
    val status = remember {
        mutableStateOf(true)
    }
    AppointmentsTitle()
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, top = 16.dp, bottom = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = stringResource(id = R.string.appointment),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.next_appointment),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (status.value) {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .fillMaxWidth(),
                        text =
                        // This will check if the user has any date stored in the database
                        // in case there's no one it will show choose_one_day message, on the other hand
                        // it will show the date stored on the database
                        if (appointmentViewModel.getDateFromDatabase() != "null") {
                            appointmentViewModel.getDateFromDatabase()

                        } else {
                            stringResource(id = R.string.choose_one_day)
                        },
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }

        // Is there is no date selected or the field from database is null, it won't show the next:
        if (status.value && appointmentViewModel.getDateFromDatabase() != "null" && appointmentViewModel.getDateFromDatabase() != "") {

            // Method that use a retrofit object and made a call for get a professional
            ProfessionalToDeal()

            // This is the last part of the card, here is where you can find Cancel appointment
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Divider(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.secondary,
                    thickness = 1.dp
                )
                // If the user click here the date stored in the db will be deleted
                // The status will be false because now any date is selected
                Text(
                    text = stringResource(id = R.string.cancel_appointment),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            appointmentViewModel.deleteDataToFirebase()
                            status.value = !status.value
                        }
                )
            }
        }
    }
}

/**
 * This method build a retrofit object and get made use of TextComponentWithoutCard method for
 * store data
 */
@Composable
fun ProfessionalToDeal() {
    val retrofit1 = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit1.create(ApiService::class.java)
    TextComponentWithoutCard(apiService = apiService, 11 /*Random.Default.nextInt(1..10)*/)
}

/**
 * A simple text with the string of appointments
 */
@Composable
fun AppointmentsTitle() {
    Text(
        text = stringResource(id = R.string.appointments),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 20.dp, start = 24.dp)
    )
}

@Composable
fun CardAppointmentScreen(onClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Image(
                painter = painterResource(id = R.drawable.dashboardca),
                contentDescription = stringResource(id = R.string.calendar),
                modifier = Modifier
                    .height(140.dp)
                    .clip(shape = ShapeDefaults.Medium),
            )
            Column {
                Text(
                    modifier = Modifier.padding(
                        start = 12.dp,
                        end = 12.dp,
                        top = 12.dp,
                        bottom = 16.dp
                    ),
                    text = stringResource(id = R.string.new_appointment),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 4.dp, bottom = 12.dp, end = 24.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    ElevatedButton(
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimary),
                        onClick = {
                            onClick()
                        })
                    {
                        Text(
                            text = stringResource(id = R.string.calendar),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }

}