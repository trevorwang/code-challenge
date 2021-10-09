package com.news.ui

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable


sealed class Screen(val route: String, val name: String, val icon: @Composable () -> Unit) {
    object News :
        Screen("news", "News", icon = { Icon(Icons.Default.Feed, contentDescription = null) })

    object Profile :
        Screen(
            "profile",
            "Profile",
            icon = { Icon(Icons.Default.Person, contentDescription = null) })

    object WebView : Screen("webview", "WebView", icon = {})

    object Login : Screen("login", "Login", icon = {})
}