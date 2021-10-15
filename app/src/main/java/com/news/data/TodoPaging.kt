package com.news.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.news.data.entity.News
import com.news.data.entity.RemoteKey
import com.news.data.entity.Todo
import com.news.data.local.TodoDatabase
import com.news.data.remote.TodoApi
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalPagingApi
class TodoRemoteMediator @Inject constructor(
    private val todoDatabase: TodoDatabase,
    private val todoApi: TodoApi,
) :
    RemoteMediator<Int, Todo>() {

    private val remoteKeyDao = todoDatabase.remoteKeyDao()
    private val todoDao = todoDatabase.todoDao()
    val query = "todo"


    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Todo>): MediatorResult {
        Timber.tag("MEDIATOR").d("load Type --->   $loadType")
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    todoDatabase.withTransaction {
                        remoteKeyDao.remoteKeyByQuery(query)
                    }.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            val page = loadKey ?: 1
            val size = state.config.pageSize
            val result = todoApi.todos()
            todoDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteByQuery(query)
                    todoDao.deleteAll()
                    Timber.tag("MEDIATOR").d("delete remote key from db")
                }
                val remoteKey = RemoteKey(query, page + 1)
                Timber.tag("MEDIATOR").d("page --->  $remoteKey")
                remoteKeyDao.insertOrReplace(remoteKey)
                todoDao.insertAll(result)
                val remoteKey2 = remoteKeyDao.remoteKeyByQuery(query = query)
                Timber.tag("MEDIATOR").d("page --->  $remoteKey2")
            }

            MediatorResult.Success(endOfPaginationReached = result.size < size)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
