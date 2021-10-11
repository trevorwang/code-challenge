package com.news.data.local

import androidx.room.*
import com.news.data.entity.Favorite

@Dao
interface FavoriteDao {

    @Query("SELECT id, news_id, news_ctime,news_description, news_picUrl,news_source,news_title, news_url FROM favorite")
    fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorite WHERE news_id IN (:newsIds)")
    fun loadAllByIds(newsIds: Array<String>): List<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)
}