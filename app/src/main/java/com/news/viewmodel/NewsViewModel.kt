package com.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.news.data.NewsPagingDataSource
import com.news.repo.NewsRepo
import com.news.repo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepo: NewsRepo,
    private val userRepo: UserRepo
) :
    ViewModel() {
    val newsList = Pager(config = PagingConfig(
        pageSize = 20,
        enablePlaceholders = false,
        initialLoadSize = 20
    ), pagingSourceFactory = { NewsPagingDataSource(newsRepo = newsRepo) }).flow


    init {
        viewModelScope.launch {

        }
    }
}