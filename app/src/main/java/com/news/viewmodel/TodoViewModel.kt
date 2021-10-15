package com.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.news.data.TodoRemoteMediator
import com.news.data.entity.Todo
import com.news.data.local.TodoDatabase
import com.news.data.remote.TodoApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalPagingApi
@ExperimentalTime
@HiltViewModel
class TodoViewModel @Inject constructor(val todoDatabase: TodoDatabase, todoApi: TodoApi) :
    ViewModel() {

    val list = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            initialLoadSize = 20,
            maxSize = 200,
        ),
        remoteMediator = TodoRemoteMediator(todoApi = todoApi, todoDatabase = todoDatabase)
    ) {
        todoDatabase.todoDao().pagingSource()
    }


    fun update(todo: Todo) {
        viewModelScope.launch {
            todoDatabase.todoDao().update(todo)
        }
    }
}