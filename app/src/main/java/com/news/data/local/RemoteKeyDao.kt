package com.news.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.news.data.entity.RemoteKey

@Dao
interface RemoteKeyDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrReplace(remoteKey: RemoteKey)

  @Query("SELECT * FROM remote_key WHERE label = :query")
  suspend fun remoteKeyByQuery(query: String): RemoteKey

  @Query("DELETE FROM remote_key WHERE label = :query")
  suspend fun deleteByQuery(query: String)
}