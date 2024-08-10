package com.nizarfadlan.core.data.remote

import com.nizarfadlan.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token = BuildConfig.API_KEY
        val authorized = original.newBuilder()
                .addHeader("Authorization", "token $token")
                .addHeader("Content-Type", "application/json")
                .build()

        return chain.proceed(authorized)
    }
}