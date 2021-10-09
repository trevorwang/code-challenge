package com.news.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.news.data.entity.News
import com.news.viewmodel.LoginViewModel
import com.news.viewmodel.NewsViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.news.R


@Composable
fun NewsListContent(
    modifier: Modifier = Modifier,
    vm: NewsViewModel,
    loginViewModel: LoginViewModel,
    lazyItems: LazyPagingItems<News>,
    onItemClicked: ((News) -> Unit)? = null,
    navController: NavController? = null
) {
    val refresh = lazyItems.loadState.refresh is LoadState.Loading
    SwipeRefresh(
        modifier = modifier,
        state = rememberSwipeRefreshState(isRefreshing = refresh),
        onRefresh = {
            lazyItems.refresh()
        }) {
        NewsList(
            lazyItems,
            vm,
            onItemClicked = onItemClicked,
            loginViewModel = loginViewModel,
            navController = navController
        )
    }
}

@Composable
private fun NewsList(
    lazyItems: LazyPagingItems<News>,
    vm: NewsViewModel,
    loginViewModel: LoginViewModel,
    onItemClicked: ((News) -> Unit)? = null,
    navController: NavController? = null
) {
    val favorites by loginViewModel.favorites.observeAsState()
    val user by loginViewModel.user.observeAsState()
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        items(lazyItems) {
            it?.let { news ->

                val size = favorites?.filter { f -> f.news.id == news.id }?.size ?: 0

                val fav =
                    if (user != null) {
                        size > 0
                    } else false
                Box(modifier = Modifier.clickable {
                    onItemClicked?.invoke(news)
                }) {
                    NewsRow(news = news, favorite = fav, onChecked = { toggle ->
                        if (user == null) {
                            navController?.navigate(Screen.Login.route)
                        } else {
                            loginViewModel.toggleFavorite(news, toggle)
                        }
                    })
                }
            }
        }

        lazyItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingRow() }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth()
                        ) {
                            LoadingRow("Loading more...")
                        }
                    }
                }
                loadState.refresh is LoadState.Error -> {
                }
                loadState.append is LoadState.Error -> {
                }
            }
        }
    }
}

@Composable
fun NewsRow(
    news: News,
    favorite: Boolean,
    onChecked: ((Boolean) -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.padding(4.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                imageModel = news.picUrl,
                modifier = Modifier
                    .size(64.dp, 64.dp)
                    .border(
                        border = BorderStroke(1.dp, color = Color.Transparent),
                        shape = RoundedCornerShape(4.dp)
                    ),
                contentScale = ContentScale.Crop,
                requestOptions = RequestOptions().override(256, 256)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop(),
                placeHolder = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
            )
            Column(
                Modifier
                    .weight(1f)
            ) {
                Text(text = news.title, fontSize = 14.sp, maxLines = 2)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = news.source,
                        fontSize = 11.sp,
                        color = Color.LightGray,
                    )
                    Text(text = news.ctime, fontSize = 11.sp, color = Color.LightGray)
                }
            }

            IconToggleButton(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(alignment = Alignment.CenterVertically),
                checked = favorite,
                onCheckedChange = {
                    onChecked?.invoke(it)
                }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = if (favorite) Color.Red else Color.Gray
                )
            }
        }
    }
}