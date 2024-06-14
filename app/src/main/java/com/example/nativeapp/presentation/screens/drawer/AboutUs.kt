package com.example.nativeapp.presentation.screens.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nativeapp.R
import com.example.nativeapp.presentation.components.MainStructure
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen

@Composable
fun AboutUs() {
    MainStructure(topNavBarName = stringResource(id = R.string.about_us), onBackClick = {
        PostOfficeAppRouter.navigateTo(
            Screen.SettingScreen)}) {
        ListOfBanners()
    }
}

@Composable
fun ListOfBanners() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = stringResource(id = R.string.mission_vision),
                modifier = Modifier.padding(start = 24.dp, top = 24.dp),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            AboutUsBanner(painterResource(id = R.drawable.couraged), stringResource(id = R.string.courage),stringResource(id = R.string.courage_desc))
            AboutUsBanner(painterResource(id = R.drawable.trustd), stringResource(id = R.string.trust),stringResource(id = R.string.trust_desc))
            AboutUsBanner(painterResource(id = R.drawable.innovationd), stringResource(id = R.string.innovation),stringResource(id = R.string.innovation_desc))
            AboutUsBanner(painterResource(id = R.drawable.adaptabilityd), stringResource(id = R.string.adaptability),stringResource(id = R.string.adaptabilty_desc))
            Spacer(modifier = Modifier.heightIn(20.dp))
        }
    }
}

@Composable
fun AboutUsBanner(picture: Painter, title: String, description: String) {
    var control by remember { mutableStateOf(false) }
    Column {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
            .clickable { control = !control }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = ShapeDefaults.Medium)
            ) {
                Image(
                    painter = picture,
                    contentScale = ContentScale.FillWidth,
                    contentDescription = ""
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black
                )
            }
        }
        if (control) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 8.dp)
            ) {
                Text(text = description,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}