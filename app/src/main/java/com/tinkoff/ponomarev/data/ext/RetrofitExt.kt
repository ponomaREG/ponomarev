package com.tinkoff.ponomarev.data.ext

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tinkoff.ponomarev.BuildConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * Функцие-расширение для строителя Retrofit
 * Необходима для создания логера Http-запросов
 * @return [OkHttpClient]
 */
fun Retrofit.Builder.addHttpLoggingInterceptor(): Retrofit.Builder = apply {
    if (BuildConfig.DEBUG) {
        client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    })
                .build()
        )
    }
}

/**
 * Функцие-расширение для строителя Retrofit
 * Необходима для создания десерилизатора Json путем kotlin-serialization
 */
@ExperimentalSerializationApi
fun Retrofit.Builder.addJsonConverter(): Retrofit.Builder = apply {
    val json = Json { ignoreUnknownKeys = true }
    val contentType = "application/json".toMediaType()
    addConverterFactory(json.asConverterFactory(contentType))
}