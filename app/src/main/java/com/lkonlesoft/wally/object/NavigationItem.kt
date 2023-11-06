package com.lkonlesoft.wally.`object`

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem (val route: String, val tittle: String, val icon: ImageVector, val selectedIcon: ImageVector){
    data object Home: NavigationItem("home", "Home", Icons.Outlined.Home, Icons.Filled.Home)
    data object Collection: NavigationItem("collection", "Collections", Icons.Outlined.List, Icons.Filled.List)
    data object Favorite: NavigationItem("favorite", "Favorites", Icons.Outlined.Favorite, Icons.Filled.Favorite)
    data object Setting: NavigationItem("setting", "Settings", Icons.Outlined.Settings, Icons.Filled.Settings)
    data object RateUs: NavigationItem("rate", "Rate us!", Icons.Outlined.Star, Icons.Filled.Star)
    data object Donate: NavigationItem("donate", "Donates", Icons.Outlined.ThumbUp, Icons.Filled.ThumbUp)
    data object About: NavigationItem("about", "About", Icons.Outlined.Info, Icons.Filled.Info)
}
