package com.news.data.db

import androidx.room.*
import com.news.data.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun currentUser(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg user: User)

    @Delete
    fun delete(user: User)
}