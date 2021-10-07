package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import com.example.myapplication.data.db.DemoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    fun db(@ApplicationContext context: Context): DemoDatabase {
        return Room.databaseBuilder(context, DemoDatabase::class.java, "demo")
            .addMigrations(Migration(1, 2) {
            })
            .build()
    }

}