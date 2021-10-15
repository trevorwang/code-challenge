package com.news.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.news.R
import com.news.viewmodel.TodoViewModel
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalPagingApi
@Composable
fun TodoScreen(todoViewModel: TodoViewModel = viewModel()) {
    val lazyItems = todoViewModel.list.flow.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.padding(top = 56.dp)) {
            items(lazyItems) { todo ->

                Row(Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = todo?.completed ?: false, onCheckedChange = {
                        todo?.let { _ ->
                            val t = todo.copy(completed = it)
                            todoViewModel.update(t)
                        }
                    })

                    Divider(Modifier.width(8.dp), color = Color.Transparent)
                    Image(
                        painter = rememberImagePainter(data = todo?.avatar, builder = {
                            crossfade(true)
                            transformations(CircleCropTransformation())
                            placeholder(R.drawable.ic_launcher_foreground)
                            error(R.drawable.ic_launcher_foreground)
                        }),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp, 64.dp)
                            .border(
                                border = BorderStroke(1.dp, color = Color.Transparent),
                                shape = RoundedCornerShape(4.dp)
                            ),
                    )

                    Divider(Modifier.width(8.dp), color = Color.Transparent)
                    Column(verticalArrangement = Arrangement.Center) {
                        Text(text = todo?.title ?: "")
                        Text(text = todo?.content ?: "")
                    }
                }
                Divider()
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
        TopAppBar(title = { Text(text = Screen.Todo.name) })
    }
}