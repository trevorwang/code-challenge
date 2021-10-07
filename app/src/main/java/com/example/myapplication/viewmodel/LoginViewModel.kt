package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.entity.Favorite
import com.example.myapplication.data.entity.User
import com.example.myapplication.repo.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepo: UserRepo) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user


    private val _favorites = MutableLiveData<List<Favorite>>()
    val favorites: LiveData<List<Favorite>> = _favorites

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = userRepo.login(username = username, password = password)
            result?.let {
                _user.value = it
            }
            Timber.d("$result")
            _favorites.value = userRepo.allFavorites()
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

            _favorites.value = if (user.value == null) listOf() else userRepo.allFavorites()
        }
    }
}
