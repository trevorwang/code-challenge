package com.news.repo.impl

import com.news.data.db.NewsDatabase
import com.news.data.entity.News
import com.news.data.entity.NewsListResult
import com.news.data.net.NewsApi
import com.news.repo.NewsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class NewRepoImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDatabase: NewsDatabase
) : NewsRepo {
    override suspend fun nbaIndex(page: Int, size: Int): NewsListResult {
        Timber.tag("APP").i("nbaIndex from NewRepoImpl")
        return newsApi.nbaIndex(page = page, size = size)
    }

    override suspend fun getNews(id: String): News? {
        return withContext(Dispatchers.IO) {
            newsDatabase.newsDao().loadAllByIds(arrayOf(id)).firstOrNull()
        }
    }

    override suspend fun insertAll(newsList: List<News>) {
        return withContext(Dispatchers.IO) {
            newsDatabase.newsDao().insertAll(*newsList.toTypedArray())
        }
    }
}