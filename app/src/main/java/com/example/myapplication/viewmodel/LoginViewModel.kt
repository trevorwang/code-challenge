package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.entity.Favorite
import com.example.myapplication.data.entity.News
import com.example.myapplication.data.entity.User
import com.example.myapplication.repo.NewsRepo
import com.example.myapplication.repo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val newsRepo: NewsRepo
) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

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

    fun toggleFavorite(newsId: String, checked: Boolean) {
        viewModelScope.launch {
            val news = newsRepo.getNews(newsId)
            if (news != null) {
                toggleFavorite(news, checked = checked)
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {

            val result = userRepo.login(username = username, password = password)
            result?.let {
                _user.value = it
            }
            _favorites.value = userRepo.allFavorites()
            Timber.d("$result")
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepo.logout()
            _user.value = null
            _favorites.value = listOf()
        }
    }

    init {
        viewModelScope.launch {
            _user.value = userRepo.currentUser()
            _favorites.value = userRepo.allFavorites()
        }
    }
}
