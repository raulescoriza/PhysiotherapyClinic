package com.example.nativeapp.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.nativeapp.R
import com.example.nativeapp.presentation.components.MainStructure
import com.example.nativeapp.presentation.viewmodels.professionals.ProfessionalViewModel
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.example.nativeapp.presentation.navigation.SystemBackButtonHandler
import com.example.nativeapp.data.api.ApiService
import com.example.nativeapp.data.model.PhotoTest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalScreen(professionalViewModel: ProfessionalViewModel = viewModel()) {
    val retrofit1 = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit1.create(ApiService::class.java)

    MainStructure(
        topNavBarName = stringResource(id = R.string.our_team), onBackClick = {PostOfficeAppRouter.navigateTo(Screen.HomeScreen)}
    ) {
        LazyColumn {
            item {
                TextComponent(apiService = apiService, 1)
                TextComponent(apiService = apiService, 2)
                TextComponent(apiService = apiService, 3)
                TextComponent(apiService = apiService, 5)
                TextComponent(apiService = apiService, 9)
                TextComponent(apiService = apiService, 10)
                TextComponent(apiService = apiService, 11)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

    }
    SystemBackButtonHandler {
        PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
    }
}



@Composable
fun TextComponent(apiService: ApiService, number: Int) {
    val name = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }
    val image = remember { mutableStateOf("") }

    LaunchedEffect(true) {
        name.value = apiService.getPhoto(number).name
        gender.value = apiService.getPhoto(number).gender
        image.value = apiService.getPhoto(number).image
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp, end = 20.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Row {
                    AsyncImage(
                        model = image.value,
                        contentDescription = stringResource(id = R.string.image_api),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.width(150.dp)
                    )
                    Column {
                        Text(
                            text = name.value,
                            modifier = Modifier.padding(start = 12.dp, top = 12.dp),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = gender.value,
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }

        }
    }
}

/**
 * This method will be used on AppointmentScreen() to add the professional which will attend the
 * visit
 */
@Composable
fun TextComponentWithoutCard(apiService: ApiService, number: Int) {
    val name = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }
    val image = remember { mutableStateOf("") }

    LaunchedEffect(true) {
        name.value = apiService.getPhoto(number).name
        gender.value = apiService.getPhoto(number).gender
        image.value = apiService.getPhoto(number).image
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = image.value,
                contentDescription = stringResource(id = R.string.image_api),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(64.dp)
                    .clip(CircleShape)
            )
            Column {
                Text(
                    text = name.value,
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = stringResource(id = R.string.speaks),
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun TextComponent2(apiService: ApiService) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            val test = remember { mutableStateOf<MutableList<PhotoTest>>(mutableListOf()) }
            LaunchedEffect(true) {
                for (i in 1..5) {
                    test.value.add(apiService.getPhoto(i))
                }
            }
            for (item in test.value) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 4.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Row {
                        AsyncImage(
                            model = item.image,
                            contentDescription = "Image",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(150.dp)
                                .fillMaxHeight()
                        )
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = item.name,
                                modifier = Modifier.padding(10.dp),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = item.gender,
                                modifier = Modifier.padding(10.dp),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }
        }
    }
}