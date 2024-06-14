package com.example.nativeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nativeapp.R
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen

/**
 * Two String parameters for the title and description. It has a column with two text
 */
@Composable
fun TitleTextDashboard(value: String, description: String) {
    Column(modifier = Modifier.padding(start = 28.dp, end = 28.dp, bottom = 28.dp)) {
        Text(
            text = value,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(),
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(),
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * This method group together simple cards methods
 */
@Composable
fun DashboardGroupCards() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            HomeCard(image = R.drawable.appointment,
                stringResource(id = R.string.appointment),
                stringResource(id = R.string.appoint_descript_home),
                onClick = {
                    PostOfficeAppRouter.navigateTo(Screen.AppointmentScreen)
                })
            HomeCard(image = R.drawable.contact,
                stringResource(id = R.string.contact),
                stringResource(id = R.string.contact_descript_home),
                onClick = {
                    PostOfficeAppRouter.navigateTo(Screen.ContactScreen)
                })
            HomeCard(image = R.drawable.ourteam,
                stringResource(id = R.string.our_team),
                stringResource(id = R.string.our_team_descript_home),
                onClick = {
                    PostOfficeAppRouter.navigateTo(Screen.ProfessionalScreen)
                })
            HomeCard(image = R.drawable.myprofile2,
                stringResource(id = R.string.my_profile),
                stringResource(id = R.string.my_profile_description),
                onClick = {
                    PostOfficeAppRouter.navigateTo(Screen.MyProfileScreen)
                })
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

/**
 * Contains a ElevatedCard(), inside has one column with 1 image and 2 texts
 */
@Composable
fun HomeCard(image: Int, title: String, description: String, onClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .width(180.dp)
            .height(276.dp)
            .padding(start = 20.dp, bottom = 12.dp)
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .padding()
                    .clip(shape = ShapeDefaults.Medium),
                painter = painterResource(image),
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 12.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            )
        }
    }
}

/**
 * Contains the Title for the second part of the HomeScreen(). Inside it has a Text(), with
 * 3 WorkOnHealthCard() a personalized method
 */
@Composable
fun WorkOnYouDashboard() {
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(id = R.string.take_care_health),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, bottom = 20.dp),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Start
    )
    WorkOnHealthCard(
        stringResource(id = R.string.latest_health_discoveries),
        stringResource(id = R.string.latest_health_discoveries_description),
        icon = Icons.Outlined.Newspaper
    ) { PostOfficeAppRouter.navigateTo(Screen.HealthProgramScreen) }
    WorkOnHealthCard(
        stringResource(id = R.string.fitness_health),
        stringResource(id = R.string.health_program_description),
        icon = ImageVector.vectorResource(R.drawable.heart)
    ) { PostOfficeAppRouter.navigateTo(Screen.HealthFitnessScreen) }
    WorkOnHealthCard(
        stringResource(id = R.string.nutrition_program),
        stringResource(id = R.string.nutrition_program_description),
        icon = ImageVector.vectorResource(R.drawable.food)
    ) {
        PostOfficeAppRouter.navigateTo(Screen.HealthNutritionScreen)
    }


}

/**
 * This method has 3 parameters, in case it's used again to change the title, description and icon
 */
@Composable
fun WorkOnHealthCard(title: String, description: String, icon: ImageVector, onClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = ShapeDefaults.Medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 20.dp, bottom = 20.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (modifier = Modifier.weight(1f)){
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = icon,
                modifier = Modifier
                    .weight(0.3f)
                    .size(60.dp),
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}