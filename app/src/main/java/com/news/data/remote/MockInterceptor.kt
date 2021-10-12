package com.news.data.remote

import com.news.data.entity.Result
import com.news.data.entity.User
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class MockInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val url = req.url
        val path = url.encodedPath
        if ("/login" == path) {

            return Response.Builder().apply {
                request(req)
                code(200)
                protocol(Protocol.HTTP_1_1)
                message("200")
                body(
                    """
            {
                "code": 200,
                "msg": "success", "data": {
                    "userId": 1,
                    "nickname": "demo",
                    "avatar": "https://img0.baidu.com/it/u=3088931474,1884625932&fm=26&fmt=auto"
                } 
            }
            """.toResponseBody("application/json; charset=utf-8".toMediaType())
                )
            }.build()
        } else {
            return chain.proceed(req)
        }
    }
}