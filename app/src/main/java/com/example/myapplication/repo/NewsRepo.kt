package com.example.myapplication.repo

import com.example.myapplication.data.entity.News
import com.example.myapplication.data.entity.NewsListResult
import kotlinx.coroutines.flow.Flow

interface NewsRepo {

    suspend fun nbaIndex(page: Int = 1, size: Int = 20): NewsListResult

    suspend fun nbaIndex2(page: Int = 1, size: Int = 10): Flow<FlowResult<List<News>>?>
}