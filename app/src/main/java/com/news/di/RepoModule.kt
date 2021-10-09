package com.news.di

import com.news.repo.NewsRepo
import com.news.repo.UserRepo
import com.news.repo.impl.NewRepoImpl
import com.news.repo.impl.UserRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindNewsRepo(newRepoImpl: NewRepoImpl): NewsRepo

    @Binds
    abstract fun bindUserRepo(userRepoImpl: UserRepoImpl): UserRepo
}