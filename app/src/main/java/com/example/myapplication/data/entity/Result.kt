package com.example.myapplication.data.entity

import com.google.gson.annotations.SerializedName

data class NewsListResult(
    val code: Int = 200,
    val msg: String = "success",
    @SerializedName("newslist")
    val newsList: List<News>
)

data class Result<T>(
    val code: Int = 200,
    val msg: String = "success",
    val data: T?
)





