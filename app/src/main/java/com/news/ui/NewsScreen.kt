package com.news.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import com.news.data.entity.News
import com.news.viewmodel.LoginViewModel
import com.news.viewmodel.NewsViewModel
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalPagingApi
@Composable
fun NewsScreen(
    vm: NewsViewModel,
    loginViewModel: LoginViewModel,
    lazyItems: LazyPagingItems<News>,
    onItemClicked: ((News) -> Unit)? = null,
    navController: NavController? = null
) {
    Box(modifier = Modifier.fillMaxSize()) {
        NewsListContent(
            modifier = Modifier.padding(top = 56.dp),
            vm = vm,
            loginViewModel = loginViewModel,
            lazyItems = lazyItems,
            onItemClicked = onItemClicked,
            navController = navController
        )
        TopAppBar(title = { Text(text = Screen.News.name) })
    }
}