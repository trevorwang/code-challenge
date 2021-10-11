package com.news.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.news.data.entity.News

@Dao
interface NewsDao {

    @Query("SELECT * FROM  news")
    fun getAll(): List<News>

    @Query("SELECT * FROM news WHERE id IN (:newsIds)")
    fun loadAllByIds(newsIds: Array<String>): List<News>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<News>)

    @Delete
    fun delete(news: News)


    @Query("SELECT * FROM news")
    fun pagingSource(): PagingSource<Int, News>

    @Query("DELETE from news where 1=1")
    fun deleteAll()

}