package com.news.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import com.news.data.local.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    fun db(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(context, TodoDatabase::class.java, "news.db")
            .addMigrations(Migration(1, 2) {
            })
            .build()
    }

}