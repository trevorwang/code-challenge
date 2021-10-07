package com.example.myapplication.repo.impl

import com.example.myapplication.data.db.DemoDatabase
import com.example.myapplication.data.entity.News
import com.example.myapplication.data.entity.NewsListResult
import com.example.myapplication.data.net.NewsApi
import com.example.myapplication.repo.NewsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class NewRepoImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val demoDatabase: DemoDatabase
) : NewsRepo {
    override suspend fun nbaIndex(page: Int, size: Int): NewsListResult {
        Timber.tag("APP").i("nbaIndex from NewRepoImpl")
        return newsApi.nbaIndex(page = page, size = size)
    }

    override suspend fun getNews(id: String): News? {
        return withContext(Dispatchers.IO) {
            demoDatabase.newsDao().loadAllByIds(arrayOf(id)).firstOrNull()
        }
    }

    override suspend fun insertAll(newsList: List<News>) {
        return withContext(Dispatchers.IO) {
            demoDatabase.newsDao().insertAll(*newsList.toTypedArray())
        }
    }
}