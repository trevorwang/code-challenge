package com.example.myapplication.repo.impl

import com.example.myapplication.data.db.DemoDatabase
import com.example.myapplication.data.entity.Favorite
import com.example.myapplication.data.entity.News
import com.example.myapplication.data.entity.User
import com.example.myapplication.data.entity.Result
import com.example.myapplication.data.net.UserApi
import com.example.myapplication.repo.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepoImpl @Inject constructor(private val userApi: UserApi, private val db: DemoDatabase) :
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

}