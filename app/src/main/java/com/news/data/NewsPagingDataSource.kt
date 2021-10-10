package com.news.data

import androidx.paging.*
import androidx.room.withTransaction
import com.news.data.db.NewsDatabase
import com.news.data.entity.News
import com.news.data.entity.RemoteKey
import com.news.data.net.NewsApi
import com.news.repo.NewsRepo
import kotlinx.coroutines.delay
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class NewsPagingDataSource @Inject constructor(
    private val newsRepo: NewsRepo,

    ) :
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


@ExperimentalTime
@ExperimentalPagingApi
class NewsRemoteMediator @Inject constructor(
    private val newsDatabase: NewsDatabase,
    private val newsApi: NewsApi
) :
    RemoteMediator<Int, News>() {

    private val remoteKeyDao = newsDatabase.remoteKeyDao()
    private val newsDao = newsDatabase.newsDao()
    val query = "news"


    override suspend fun load(loadType: LoadType, state: PagingState<Int, News>): MediatorResult {
        Timber.tag("MEDIATOR").d("load Type --->   $loadType")
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    newsDatabase.withTransaction {
                        remoteKeyDao.remoteKeyByQuery(query)
                    }.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            val page = loadKey ?: 1
            val size = state.config.pageSize
            val result = newsApi.nbaIndex(page = page, size)
            newsDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteByQuery(query)
                    newsDao.deleteAll()
                    Timber.tag("MEDIATOR").d("delete remote key from db")
                }
                val remoteKey = RemoteKey(query, page + 1)
                Timber.tag("MEDIATOR").d("page --->  $remoteKey")
                remoteKeyDao.insertOrReplace(remoteKey)
                newsDao.insertAll(result.newsList)
                val remoteKey2 = remoteKeyDao.remoteKeyByQuery(query = query)
                Timber.tag("MEDIATOR").d("page --->  $remoteKey2")
            }

            MediatorResult.Success(endOfPaginationReached = result.newsList.size < size)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
