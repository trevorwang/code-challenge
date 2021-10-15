package com.news.ui

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.news.viewmodel.LoginViewModel
import com.news.viewmodel.NewsViewModel
import timber.log.Timber
import kotlin.time.ExperimentalTime


val items = listOf(Screen.News, Screen.Todo)

@ExperimentalPagingApi
@ExperimentalTime
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val newsViewModel = viewModel<NewsViewModel>()
    val loginModel = viewModel<LoginViewModel>()
    Scaffold(bottomBar = {
        Timber.d("current route : ${navController.currentDestination?.route}")
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        if (arrayOf(
                Screen.News.route,
                Screen.Todo.route
            ).contains(currentDestination?.route)
        ) {
            BottomNavigation {
                items.forEach { screen ->
                    BottomNavigationItem(
                        selected = currentDestination?.hierarchy?.any { it.route === screen.route } == true,
                        icon = screen.icon,
                        label = { Text(screen.name) },
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        }
    }) {
        NavigationHost(navController, it, newsViewModel, loginModel)
    }
}

@ExperimentalPagingApi
@ExperimentalTime
@Composable
private fun NavigationHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    newsViewModel: NewsViewModel,
    loginModel: LoginViewModel,
) {
    val lazyItems = newsViewModel.newsList.flow.collectAsLazyPagingItems()
    NavHost(
        navController = navController,
        startDestination = Screen.News.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screen.News.route) {
            NewsScreen(loginViewModel = loginModel, lazyItems = lazyItems, onItemClicked = {
                navController.navigate("${Screen.WebView.route}?url=${it.url}&title=${it.title}&id=${it.id}")
            }, navController = navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                navController = navController,
                loginModel,
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(vm = loginModel, navController = navController)
        }

        composable(
            "${Screen.WebView.route}?url={url}&title={title}&id={id}",
            arguments = listOf(
                navArgument("url") {},
                navArgument("title") { nullable = true },
                navArgument("id") {})
        ) {
            DetailScreen(it, loginModel, navController)
        }

        composable(Screen.Todo.route) {
            TodoScreen(hiltViewModel())
        }
    }
}

@Composable
fun DetailScreen(
    navBack: NavBackStackEntry,
    loginModel: LoginViewModel,
    navController: NavController,
) {
    val url = navBack.arguments?.getString("url") ?: "NOT FOUND"
    val title = navBack.arguments?.getString("title") ?: "News Detail"
    val id = navBack.arguments?.getString("id") ?: ""
    val fav =
        loginModel.favorites.observeAsState().value?.firstOrNull { it.news.id == id } != null

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = title, maxLines = 1) }, navigationIcon = {
            Icon(
                Icons.Default.Close,
                contentDescription = "Back",
                modifier = Modifier.clickable {
                    navController.popBackStack()
                })
        })
    },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                loginModel.toggleFavorite(id, !fav)

            }, backgroundColor = Color.Black) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = if (fav) Color.Red else Color.Gray

                )
            }
        }
    ) {
        MyWebView(url = url, initSettings = {
            it?.javaScriptEnabled = true
            it?.userAgentString =
                "Mozilla/5.0 (Linux; Android ${Build.VERSION.RELEASE};  AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Mobile Safari/537.36"
        })
    }
}

