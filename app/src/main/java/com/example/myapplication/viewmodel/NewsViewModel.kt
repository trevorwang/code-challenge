package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.myapplication.data.NewsPagingDataSource
import com.example.myapplication.data.entity.Favorite
import com.example.myapplication.data.entity.News
import com.example.myapplication.repo.NewsRepo
import com.example.myapplication.repo.UserRepo
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

    private val _favorites = MutableLiveData<List<Favorite>>(listOf())
    val favorites: LiveData<List<Favorite>> = _favorites

    fun toggleFavorite(news: News, checked: Boolean) {
        val set = _favorites.value ?: mutableSetOf()
        _favorites.value = set.toMutableList().also { list ->
            if (checked) {
                viewModelScope.launch {
                    userRepo.addFavorite(news)
                }
                list.add(Favorite(0, news = news))
            } else {
                val f = list.first { it.news.id == news.id }
                list.remove(f)
                viewModelScope.launch {
                    userRepo.removeFavorite(news.id)
                }
            }
        }
    }


    init {
        viewModelScope.launch {
            _favorites.value = userRepo.allFavorites()
        }
    }
}