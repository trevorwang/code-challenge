package com.example.myapplication.repo.impl

import com.example.myapplication.data.entity.NewsListResult
import com.example.myapplication.data.net.NewsApi
import com.example.myapplication.repo.NewsRepo
import timber.log.Timber
import javax.inject.Inject

class NewRepoImpl @Inject constructor(private val newsApi: NewsApi) : NewsRepo {
    override suspend fun nbaIndex(page: Int, size: Int): NewsListResult {
        Timber.tag("APP").i("nbaIndex from NewRepoImpl")
        return newsApi.nbaIndex(page = page, size = size)
    }
}