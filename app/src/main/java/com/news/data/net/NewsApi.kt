package com.news.data.net

import com.news.data.entity.NewsListResult
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("nba/index")
    suspend fun nbaIndex(
        @Query("page") page: Int = 1,
        @Query("num") size: Int = 20,
    ): NewsListResult


}