package com.news.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TodoRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsRetrofit