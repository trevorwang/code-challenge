package com.example.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.entity.Favorite
import com.example.myapplication.data.entity.News
import com.example.myapplication.data.entity.User

@Database(version = 2, entities = [News::class, Favorite::class, User::class])
abstract class DemoDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun userDao(): UserDao
}