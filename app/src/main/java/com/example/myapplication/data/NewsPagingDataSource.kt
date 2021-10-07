package com.example.myapplication.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.data.entity.News
import com.example.myapplication.repo.NewsRepo
import kotlinx.coroutines.delay
import java.lang.Exception
import javax.inject.Inject
import kotlin.time.Duration
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
            delay(Duration.seconds(2))
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