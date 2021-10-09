package com.news.repo

import com.news.data.entity.Favorite
import com.news.data.entity.News
import com.news.data.entity.User

interface UserRepo {

    suspend fun login(username: String, password: String): User?

    suspend fun logout()

    suspend fun addFavorite(news: News)

    suspend fun removeFavorite(favorite: Favorite)

    suspend fun removeFavorite(new_id: String)

    suspend fun isFavorite(news: News): Boolean

    suspend fun allFavorites(): List<Favorite>

    suspend fun currentUser(): User?

    suspend fun getFavorite(newsId: String): List<Favorite>
}