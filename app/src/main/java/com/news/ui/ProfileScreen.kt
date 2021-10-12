package com.news.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.ExperimentalPagingApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.news.R
import com.news.viewmodel.LoginViewModel
import kotlin.time.ExperimentalTime


@ExperimentalTime
@Composable
@ExperimentalPagingApi
fun ProfileScreen(
    navController: NavController?,
    loginViewModel: LoginViewModel,
) {
    val user = loginViewModel.user.observeAsState()
    val favorites = loginViewModel.favorites.observeAsState()
    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.padding(top = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                Row(modifier = Modifier.clickable {
                    if (user.value == null) {
                        navController?.navigate(Screen.Login.route)
                    }

                }) {
                    Image(
                        painter = rememberImagePainter(data = user.value?.avatar, builder = {
                            transformations(CircleCropTransformation())
                            error(R.drawable.ic_launcher_foreground)
                        }),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp, 120.dp)
                            .padding(8.dp)
                            .border(
                                border = BorderStroke(1.dp, color = Color.Transparent),
                                shape = RoundedCornerShape(4.dp)
                            ),
                    )

                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = "Welcome!", fontSize = 30.sp)
                        Divider(color = Color.Transparent, thickness = 12.dp)

                        if (user.value == null)
                            TextButton(onClick = {
                                if (user.value == null) {
                                    navController?.navigate(Screen.Login.route)
                                }
                            }) {
                                Text("Click here to login")
                            }
                        else Text(user.value!!.nickname)
                    }
                }
            }

            items(favorites.value ?: listOf()) {
                NewsRow(modifier = Modifier.clickable {
                    navController?.navigate("${Screen.WebView.route}?url=${it.news.url}&title=${it.news.title}&id=${it.news.id}")
                }, news = it.news, favorite = true, onChecked = { _ ->
                    loginViewModel.toggleFavorite(it.news, false)
                })

            }


            item {
                if (user.value != null) {
                    Divider(color = Color.Transparent, thickness = 80.dp)
                    TextButton(onClick = {
                        loginViewModel.logout()
                    }) {
                        Text(text = "LOGOUT")
                    }
                }
            }
        }
        TopAppBar(title = { Text(text = "Profile") })
    }
}