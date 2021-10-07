package com.example.myapplication.di

import com.example.myapplication.repo.NewsRepo
import com.example.myapplication.repo.UserRepo
import com.example.myapplication.repo.impl.NewRepoImpl
import com.example.myapplication.repo.impl.UserRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindNewsRepo(newRepoImpl: NewRepoImpl): NewsRepo

    @Binds
    abstract fun bindUserRepo(userRepoImpl: UserRepoImpl): UserRepo
}