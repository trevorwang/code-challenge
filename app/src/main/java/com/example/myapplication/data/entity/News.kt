package com.example.myapplication.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
    @PrimaryKey val id: String,
    @ColumnInfo val ctime: String,
    @ColumnInfo val title: String,
    @ColumnInfo val description: String,
    @ColumnInfo val source: String,
    @ColumnInfo val picUrl: String,
    @ColumnInfo val url: String
)


