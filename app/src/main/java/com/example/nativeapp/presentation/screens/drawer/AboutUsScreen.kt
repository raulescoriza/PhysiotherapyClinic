package com.example.nativeapp.presentation.screens.drawer

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nativeapp.R
import com.example.nativeapp.presentation.components.MainStructure
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen
import com.example.nativeapp.presentation.navigation.SystemBackButtonHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AboutUsScreen() {
    val context = LocalContext.current
    val toastText = stringResource(id = R.string.toast_alert)
    val images = listOf(
        "https://img.freepik.com/foto-gratis/patoient-osteopatia-recibiendo-masaje-tratamiento_23-2149159109.jpg?w=740&t=st=1707825678~exp=1707826278~hmac=621a71de8a35ae62eb80d0f46363ffee23c30909d07b8a08fe65e2cb15b3818f",
        "https://img.freepik.com/foto-gratis/mujer-que-tiene-sesion-fisioterapia_23-2149115642.jpg?w=1380&t=st=1707825635~exp=1707826235~hmac=af4ed49f211d273679f0e0a977e901c4da381bdfd40011f5bddffe4e8ef6f8e5",
        "https://img.freepik.com/foto-gratis/fisioterapeuta-realizando-masaje-terapeutico-cliente-masculino_23-2149143841.jpg?w=740&t=st=1707825690~exp=1707826290~hmac=ad62407a0d95050b22c261f6f2a959b68df2c1f5d0d7801e38bbb588693ca091",
        "https://img.freepik.com/foto-gratis/medico-osteopata-masculino-comprobando-escapula-paciente-femenino_23-2148846569.jpg?w=740&t=st=1707825702~exp=1707826302~hmac=9796af505ee017e26fb28769078fd0db1f1f020dbf34170189eae4592285720f",
        "https://img.freepik.com/foto-gratis/fisioterapeuta-femenino-aplicando-vendaje-medico-elastico-al-paciente-masculino_23-2149143838.jpg?w=740&t=st=1707826337~exp=1707826937~hmac=167c676101df9148804c9021f3f2c662b4a509812182d1c2b7eba61ab5dfa197",
        "https://img.freepik.com/foto-gratis/mujer-hace-ejercicio-estirar-musculos-espalda-rehabilitacion-despues-lesion_169016-48541.jpg?w=740&t=st=1707826381~exp=1707826981~hmac=1544840389838037c52d0d014e721e0922b7488f6a013f83eca46969938d056f"
    )
    MainStructure(
        topNavBarName = stringResource(id = R.string.about_us), onBackClick = {PostOfficeAppRouter.navigateTo(Screen.SettingScreen)}
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.mission_vision),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                CarouselSlider(images)
                Text(
                    text = stringResource(id = R.string.information_description),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.secondary
                )
                /*Text(
                    text = stringResource(id = R.string.information_description2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.secondary
                )*/

                Text(
                    text = stringResource(id = R.string.know_more),
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = stringResource(id = R.string.check_us),
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            Toast
                                .makeText(context, toastText, Toast.LENGTH_SHORT)
                                .show()
                        },
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    SystemBackButtonHandler {
        PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
    }
}

@Composable
private fun CarouselSlider(images: List<String>) {

    var index by remember { mutableStateOf(0) }
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true, block = {
        coroutineScope.launch {
            while (true) {
                delay(3500)
                if (index == images.size - 1) index = 0
                else index++
                scrollState.animateScrollToItem(index)
            }
        }
    })

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.padding(6.dp)) {
            LazyRow(
                state = scrollState,
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                itemsIndexed(images) { index, image ->
                    Card(
                        modifier = Modifier.height(260.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dp
                        )
                    ) {
                        AsyncImage(
                            model = image,
                            contentDescription = "Image",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.width(260.dp)
                        )
                    }
                }
            }
        }
    }
}