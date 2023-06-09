package com.lkonlesoft.wally

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.List
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material.icons.twotone.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem (var route: String, var tittle: String, var icon: ImageVector){
    object Home: NavigationItem("home", "Home", Icons.TwoTone.Home)
    object Collection: NavigationItem("collection", "Collections", Icons.TwoTone.List)
    object Favorite: NavigationItem("favorite", "Favorites", Icons.TwoTone.Favorite)
    object Setting: NavigationItem("setting", "Settings", Icons.TwoTone.Settings)
    object RateUs: NavigationItem("rateus", "Rate us!", Icons.TwoTone.Star)
    object Donate: NavigationItem("donate", "Donates", Icons.TwoTone.ThumbUp)
    object About: NavigationItem("about", "About", Icons.TwoTone.Info)
}
