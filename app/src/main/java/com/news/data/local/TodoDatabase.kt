package com.news.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.news.data.entity.*

@Database(version = 3,
    entities = [News::class, Favorite::class, User::class, RemoteKey::class, Todo::class])
abstract class TodoDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun userDao(): UserDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}