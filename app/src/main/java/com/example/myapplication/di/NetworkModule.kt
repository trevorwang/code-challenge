package com.example.myapplication.di

import com.example.myapplication.baseUrl
import com.example.myapplication.data.net.MockInterceptor
import com.example.myapplication.data.net.NewsApi
import com.example.myapplication.data.net.UserApi
import com.example.myapplication.token
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        Timber.d("create news api")
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi {
        Timber.d("create user api")
        return retrofit.create(UserApi::class.java)
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
                val url = req.url().newBuilder()
                    .addQueryParameter("key", token).build()
                chain.proceed(req.newBuilder().url(url).build())
            }
            .build()
    }
}