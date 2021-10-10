package com.news.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class RemoteKey(@PrimaryKey val label: String, val nextKey: Int?)