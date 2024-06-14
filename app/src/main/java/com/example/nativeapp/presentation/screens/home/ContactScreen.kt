package com.example.nativeapp.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Directions
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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

@Composable
fun ContactScreen() {
    MainStructure(topNavBarName = stringResource(id = R.string.contact), onBackClick = {PostOfficeAppRouter.navigateTo(Screen.HomeScreen)}) {
        LazyColumn {
            item {
                ContactImage()
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
    SystemBackButtonHandler {
        PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
    }
}




@Composable
fun ContactImage() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.contact),
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )
        ContentContactScreen()
        ContactCardTool()
    }
}

@Composable
fun ContentContactScreen(){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        shape = ShapeDefaults.ExtraSmall,

        ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                modifier = Modifier
                    .weight(0.3f)
                    .size(52.dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary)
            Column (modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(id = R.string.rehabilitation_center),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 12.dp, start = 4.dp, end = 8.dp, bottom = 4.dp)
                )
                Text(
                    text = stringResource(id = R.string.physiotherapy_clinic),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp, start = 4.dp, end = 8.dp, bottom = 12.dp)
                )
            }
        }
    }
}
@Composable
fun ContactCardTool() {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp)
    ) {
        IconTextComponent(stringResource(id = R.string.address_description), Icons.Outlined.Directions)
        IconTextComponent(stringResource(id = R.string.email_example), Icons.Outlined.Email)
        IconTextComponent(stringResource(id = R.string.contact_phone_example), Icons.Outlined.Phone)
        //IconTextComponent(stringResource(id = R.string.contact_schedule), Icons.Outlined.DateRange)
    }
}


@Composable
fun IconTextComponent(title: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(id = R.string.contact),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 16.dp, start = 24.dp, end = 24.dp, bottom = 8.dp)
                .size(28.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(top = 8.dp, start = 10.dp, end = 16.dp, bottom = 8.dp)
        )
    }
}