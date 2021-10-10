package com.news.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.news.data.entity.Favorite
import com.news.data.entity.News
import com.news.data.entity.RemoteKey
import com.news.data.entity.User

@Database(version = 2, entities = [News::class, Favorite::class, User::class, RemoteKey::class])
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun userDao(): UserDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}