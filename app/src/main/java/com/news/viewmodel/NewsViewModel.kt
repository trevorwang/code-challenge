package com.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.news.data.NewsRemoteMediator
import com.news.data.local.TodoDatabase
import com.news.data.remote.NewsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalPagingApi
@HiltViewModel
class NewsViewModel @Inject constructor(
    todoDatabase: TodoDatabase,
    newsApi: NewsApi
) :
    ViewModel() {
    val newsList = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            initialLoadSize = 20,
            maxSize = 200,
        ),
        remoteMediator = NewsRemoteMediator(todoDatabase, newsApi),
    ) {
        todoDatabase.newsDao().pagingSource()
    }


    init {
        viewModelScope.launch {

        }
    }
}