package com.news.repo.impl

import com.news.data.local.TodoDatabase
import com.news.data.entity.News
import com.news.data.entity.NewsListResult
import com.news.data.remote.NewsApi
import com.news.repo.NewsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class NewRepoImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val todoDatabase: TodoDatabase
) : NewsRepo {
    override suspend fun nbaIndex(page: Int, size: Int): NewsListResult {
        Timber.tag("APP").i("nbaIndex from NewRepoImpl")
        return newsApi.nbaIndex(page = page, size = size)
    }

    override suspend fun getNews(id: String): News? {
        return withContext(Dispatchers.IO) {
            todoDatabase.newsDao().loadAllByIds(arrayOf(id)).firstOrNull()
        }
    }

    override suspend fun insertAll(newsList: List<News>) {
        return withContext(Dispatchers.IO) {
            todoDatabase.newsDao().insertAll(newsList)
        }
    }
}