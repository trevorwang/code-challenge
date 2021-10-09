package com.news.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val userId: Int,
    @ColumnInfo
    val nickname: String,
    @ColumnInfo
    val avatar: String
)
