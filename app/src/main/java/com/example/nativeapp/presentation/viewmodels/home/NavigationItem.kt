package com.example.nativeapp.presentation.viewmodels.home

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * References each Navigation Item displayed on the drawerNavigation
 */
data class NavigationItem(
    val title: String,
    val description: String,
    val id: String,
    val icon: ImageVector
)
