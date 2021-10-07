package com.example.myapplication.repo.impl

import com.example.myapplication.data.entity.News
import com.example.myapplication.data.entity.NewsListResult
import com.example.myapplication.data.net.NewsApi
import com.example.myapplication.repo.FlowResult
import com.example.myapplication.repo.NewsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class NewRepoImpl @Inject constructor(private val newsApi: NewsApi) : NewsRepo {
    override suspend fun nbaIndex(page: Int, size: Int): NewsListResult {
        Timber.tag("APP").i("nbaIndex from NewRepoImpl")
        return newsApi.nbaIndex(page = page, size = size)
    }

    override suspend fun nbaIndex2(page: Int, size: Int): Flow<FlowResult<List<News>>?> {
        return flow {
            emit(FlowResult.loading())
            val result = newsApi.nbaIndex(page, size)
            emit(FlowResult.success(result.newsList))
        }.flowOn(Dispatchers.IO)
    }
}