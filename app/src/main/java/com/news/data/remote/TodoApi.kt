package com.news.data.remote

import com.news.data.entity.Todo
import retrofit2.http.GET
import retrofit2.http.PUT

interface TodoApi {
    @GET("/todo")
    suspend fun todos(): List<Todo>

    @PUT("/todo/:id")
    suspend fun update(id: String, todo: Todo): Todo
}