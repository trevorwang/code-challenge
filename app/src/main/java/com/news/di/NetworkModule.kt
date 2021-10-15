package com.news.di

import com.news.baseUrl
import com.news.data.remote.MockInterceptor
import com.news.data.remote.NewsApi
import com.news.data.remote.TodoApi
import com.news.data.remote.UserApi
import com.news.token
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Named
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @NewsRetrofit
    fun newsRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @TodoRetrofit
    fun todoRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://5d42a6e2bc64f90014a56ca0.mockapi.io/api/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideNewsApi(@NewsRetrofit retrofit: Retrofit): NewsApi {
        Timber.d("create news api")
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    fun provideUserApi(@NewsRetrofit retrofit: Retrofit): UserApi {
        Timber.d("create user api")
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    fun provideTodoApi(@TodoRetrofit retrofit: Retrofit): TodoApi {
        Timber.d("create todo api")
        return retrofit.create(TodoApi::class.java)
    }


    @Provides
    fun okHttpClient(mockInterceptor: MockInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addNetworkInterceptor(logging)
            .addInterceptor(mockInterceptor)
            .addInterceptor { chain ->
                val req = chain.request()
                val url = req.url.newBuilder().apply {
                    if (req.url.host == "api.tianapi.com") {
                        addQueryParameter("key", token)
                    }

                }.build()
                chain.proceed(req.newBuilder().url(url).build())
            }
            .build()
    }
}