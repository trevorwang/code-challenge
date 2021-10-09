package com.news.repo.impl

import com.news.data.db.NewsDatabase
import com.news.data.entity.Favorite
import com.news.data.entity.News
import com.news.data.entity.User
import com.news.data.net.UserApi
import com.news.repo.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepoImpl @Inject constructor(private val userApi: UserApi, private val db: NewsDatabase) :
    UserRepo {
    override suspend fun login(username: String, password: String): User? {
        return withContext(Dispatchers.IO) {
            val user = userApi.login(username = username, password = password).data
            user?.let {
                db.userDao().insertAll(it)
            }
            user
        }
    }

    override suspend fun logout() {
        return withContext(Dispatchers.IO) {
            val user = db.userDao().currentUser()
            user?.let {
                db.userDao().delete(it)
            }
        }
    }

    override suspend fun addFavorite(news: News) {
        withContext(Dispatchers.IO) {
            val favorite = Favorite(0, news = news)
            db.favoriteDao().insertAll(favorite)
        }
    }

    override suspend fun removeFavorite(favorite: Favorite) {
        db.favoriteDao().delete(favorite = favorite)
    }

    override suspend fun removeFavorite(new_id: String) {
        withContext(Dispatchers.IO) {
            db.favoriteDao().loadAllByIds(arrayOf(new_id)).forEach {
                db.favoriteDao().delete(it)
            }
        }
    }

    override suspend fun isFavorite(news: News): Boolean {
        return withContext(Dispatchers.IO) {
            val result = db.favoriteDao().loadAllByIds(arrayOf(news.id))
            result.isNotEmpty()
        }
    }

    override suspend fun allFavorites(): List<Favorite> {
        return withContext(Dispatchers.IO) { db.favoriteDao().getAll() }
    }

    override suspend fun currentUser(): User? {
        return withContext(Dispatchers.IO) {
            db.userDao().currentUser()
        }
    }

    override suspend fun getFavorite(newsId: String): List<Favorite> {
        return withContext(Dispatchers.IO) {
            db.favoriteDao().loadAllByIds(arrayOf(newsId))
        }
    }

}