package com.example.myapplication.repo

import com.example.myapplication.data.entity.NewsListResult

interface NewsRepo {

    suspend fun nbaIndex(page: Int = 1, size: Int = 20): NewsListResult

}