package com.example.myapplication.data.net

import com.example.myapplication.data.entity.Result
import com.example.myapplication.data.entity.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {
    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Result<User>
}