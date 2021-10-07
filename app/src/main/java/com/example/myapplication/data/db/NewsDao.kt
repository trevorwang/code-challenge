package com.example.myapplication.data.db

import androidx.room.*
import com.example.myapplication.data.entity.News

@Dao
interface NewsDao {

    @Query("SELECT * FROM  news")
    fun getAll(): List<News>

    @Query("SELECT * FROM news WHERE id IN (:newsIds)")
    fun loadAllByIds(newsIds: Array<String>): List<News>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg news: News)

    @Delete
    fun delete(news: News)
}