package com.example.nativeapp.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nativeapp.R
import com.example.nativeapp.presentation.viewmodels.home.HomeViewModel
import com.example.nativeapp.presentation.navigation.PostOfficeAppRouter
import com.example.nativeapp.presentation.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainStructure(
    homeViewModel: HomeViewModel = viewModel(),
    topNavBarName: String,
    onBackClick:() -> Unit,
    content: @Composable () -> Unit
) {
        // Scaffold is like a template, it has different parameters
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AppToolBarMainStructure(
                    toolbarTitle = topNavBarName
                ) { onBackClick() }
            },
        ) {
            Box(
                modifier = Modifier.padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
            ) {
                content()
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolBarMainStructure(toolbarTitle: String, onBackClick:()-> Unit) {
    // This coroutine helps to reconstruct the view
    // This method require at least the parameter title, this is a component that can be used
    // on Scaffold topBar
    // Contains title, navigateIcon (menu icon), and actions (log out icon)
    TopAppBar(
        title = {
            Text(
                text = toolbarTitle,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        },
        // navigationIcon is related to the icon who will appear on left side of the TopAppBar
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = stringResource(id = R.string.menu),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .clickable {
                            onBackClick()
                    }
                    .padding(12.dp)
            )
        },
        colors = TopAppBarDefaults
            .topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
    )
}