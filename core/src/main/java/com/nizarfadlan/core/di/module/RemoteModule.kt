package com.nizarfadlan.core.di.module

import com.nizarfadlan.core.BuildConfig
import com.nizarfadlan.core.data.remote.HeaderInterceptor
import com.nizarfadlan.core.data.remote.service.UserService
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule =
    module {
        single { provideHttpClient() }
        single { provideRetrofit(get()) }
        single { provideUserService(get()) }
    }

fun provideHttpClient(): OkHttpClient {
    val hostname = BuildConfig.BASE_URL.substringAfter("https://").substringBefore(":")
    val certificatePinner = CertificatePinner.Builder()
        .add(hostname, "sha256/GyhWVHsOXNZc6tGTNd15kXF9YD0kEZaGxYn6MUva5jY=")
        .add(hostname, "sha256/lmo8/KPXoMsxI+J9hY+ibNm2r0IYChmOsF9BxD74PVc=")
        .build()

    return OkHttpClient
        .Builder()
        .certificatePinner(certificatePinner)
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(loggingInterceptor)
        .readTimeout(120, TimeUnit.SECONDS)
        .connectTimeout(120, TimeUnit.SECONDS)
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private val loggingInterceptor =
    if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

fun provideUserService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)
