package com.example.nativeapp.presentation.screens.healthprograms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nativeapp.R
import com.example.nativeapp.presentation.components.MainStructure
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.example.nativeapp.presentation.navigation.SystemBackButtonHandler
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HealthProgramScreen() {
    MainStructure(topNavBarName = stringResource(id = R.string.health), onBackClick = {PostOfficeAppRouter.navigateTo(Screen.HomeScreen)}) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()

        ) {
            item {
                ProgramTitleText(stringResource(id = R.string.health_news))
                HealthProgramCard(
                    image = painterResource(id = R.drawable.test),
                    stringResource(id = R.string.title_exercise_pro),
                    stringResource(id = R.string.desc_exercise_pro),
                    stringResource(id = R.string.date_example1)
                )
                Spacer(modifier = Modifier.heightIn(24.dp))
                HealthProgramCard(
                    image = painterResource(id = R.drawable.nutrition),
                    stringResource(id = R.string.title_nutricional_pro),
                    stringResource(id = R.string.descrip_nutricional_pro),
                    stringResource(id = R.string.date_example2)
                )
                Spacer(modifier = Modifier.heightIn(24.dp))
                HealthProgramCard(
                    image = painterResource(id = R.drawable.lungerecovery),
                    stringResource(id = R.string.title_discover_pro),
                    stringResource(id = R.string.descrip_discover_pro),
                    stringResource(id = R.string.date_example3)
                )
                Spacer(modifier = Modifier.heightIn(12.dp))

            }
        }
        SystemBackButtonHandler {
            PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
        }
    }
}

@Composable
fun ProgramTitleText(title: String) {
    Text(
        modifier = Modifier.padding(20.dp),
        text = title,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.SemiBold,
    )
}


@Composable
fun HealthProgramCard(image: Painter, title: String, description: String, date: String) {
    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Image(
            modifier = Modifier
                .padding(16.dp)
                .clip(ShapeDefaults.Medium),
            painter = image,
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
        )
        Text(
            text = description,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
        )
        Text(
            text = date,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp, top = 8.dp)
        )
    }
}