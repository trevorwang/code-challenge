package com.news.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val createdAt: String,
    val title: String,
    val content: String?,
    val completed: Boolean,
    val url: String?,
    val avatar: String?,
    val updatedAt: String,
    @PrimaryKey
    val id: String,
)
