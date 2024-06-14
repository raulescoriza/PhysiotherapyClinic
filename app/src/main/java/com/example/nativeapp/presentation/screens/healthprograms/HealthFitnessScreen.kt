package com.example.nativeapp.presentation.screens.healthprograms

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.nativeapp.R
import com.example.nativeapp.presentation.components.MainStructure
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.example.nativeapp.presentation.navigation.SystemBackButtonHandler

@Composable
fun HealthFitnessScreen() {
    MainStructure(
        topNavBarName = stringResource(id = R.string.health), onBackClick = {PostOfficeAppRouter.navigateTo(Screen.HomeScreen)}) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()

        ) {
            item {
                ProgramTitleText(stringResource(id = R.string.health_exercise))
                HealthProgramCard(
                    image = painterResource(id = R.drawable.fitness3),
                    stringResource(id = R.string.title_fitness_3),
                    stringResource(id = R.string.desc_fitness_3),
                    stringResource(id = R.string.date_example4)
                )
                Spacer(modifier = Modifier.heightIn(24.dp))
                HealthProgramCard(
                    image = painterResource(id = R.drawable.fitness4),
                    stringResource(id = R.string.title_fitness_4),
                    stringResource(id = R.string.desc_fitness_4),
                    stringResource(id = R.string.date_example5)
                )
                Spacer(modifier = Modifier.heightIn(24.dp))
                HealthProgramCard(
                    image = painterResource(id = R.drawable.fitness1),
                    stringResource(id = R.string.title_fitness_1),
                    stringResource(id = R.string.desc_fitness_1),
                    stringResource(id = R.string.date_example4)
                )
                Spacer(modifier = Modifier.heightIn(24.dp))
                HealthProgramCard(
                    image = painterResource(id = R.drawable.fitness2),
                    stringResource(id = R.string.title_fitness_2),
                    stringResource(id = R.string.desc_fitness_2),
                    stringResource(id = R.string.date_example1)
                )

                Spacer(modifier = Modifier.heightIn(12.dp))


            }
        }
        SystemBackButtonHandler {
            PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
        }
    }
}
