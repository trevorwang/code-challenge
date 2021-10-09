package com.news.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.news.data.entity.News
import com.news.repo.NewsRepo
import javax.inject.Inject
import kotlin.time.ExperimentalTime

class NewsPagingDataSource @Inject constructor(private val newsRepo: NewsRepo) :
    PagingSource<Int, News>() {
    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return null
    }

    @ExperimentalTime
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        val pos = params.key ?: 1

        return try {
            val newsList = newsRepo.nbaIndex(pos, params.loadSize).newsList
            newsRepo.insertAll(newsList = newsList)
            LoadResult.Page(
                newsList,
                if (pos <= 1) null else pos - 1,
                if (newsList.size < params.loadSize) null else pos + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}