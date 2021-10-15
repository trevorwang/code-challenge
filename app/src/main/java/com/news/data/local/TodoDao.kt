package com.news.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.news.data.entity.Todo

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    suspend fun all(): List<Todo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Todo>)

    @Query("SELECT * FROM todo")
    fun pagingSource(): PagingSource<Int, Todo>

    @Query("DELETE from todo where 1=1")
    suspend fun deleteAll(): Int

    @Delete
    suspend fun delete(todo: Todo): Int

    @Update
    suspend fun update(todo: Todo)

}