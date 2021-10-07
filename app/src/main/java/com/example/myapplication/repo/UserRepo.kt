package com.example.myapplication.repo

import com.example.myapplication.data.entity.Favorite
import com.example.myapplication.data.entity.News
import com.example.myapplication.data.entity.User
import com.example.myapplication.data.entity.Result

interface UserRepo {

    suspend fun login(username: String, password: String): User?

    suspend fun logout()

    suspend fun addFavorite(news: News)

    suspend fun removeFavorite(favorite: Favorite)

    suspend fun removeFavorite(new_id: String)

    suspend fun isFavorite(news: News): Boolean

    suspend fun allFavorites(): List<Favorite>

    suspend fun currentUser(): User?
}