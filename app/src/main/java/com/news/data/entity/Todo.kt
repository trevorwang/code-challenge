package com.news.data.entity

data class Todo(
    val createdAt: String,
    val title: String,
    val content: String?,
    val completed: Boolean,
    val url: String?,
    val avatar: String?,
    val updatedAt: String,
    val id: String,
)
