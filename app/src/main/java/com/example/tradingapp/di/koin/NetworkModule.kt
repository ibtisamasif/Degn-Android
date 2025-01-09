package com.example.tradingapp.di.koin

import com.example.tradingapp.api.ApiService
import com.example.tradingapp.api.QuoteApiService
import com.example.tradingapp.utils.Constants.Companion.BASE_URL
import com.example.tradingapp.utils.Constants.Companion.QUOTE_API_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .build()
    }

    // BaseRetrofit configuration
    single(named("BaseRetrofit")) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // QuoteRetrofit configuration
    single(named("QuoteRetrofit")) {
        Retrofit.Builder()
            .baseUrl(QUOTE_API_BASE_URL)
            .client(get<OkHttpClient>()) // Use the custom OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ApiService for BaseRetrofit
    single {
        get<Retrofit>(named("BaseRetrofit")).create(ApiService::class.java)
    }

    // QuoteApiService for QuoteRetrofit
    single {
        get<Retrofit>(named("QuoteRetrofit")).create(QuoteApiService::class.java)
    }
}



