package com.tinkoff.ponomarev.data.di

import com.tinkoff.ponomarev.data.constant.NetworkConstant
import com.tinkoff.ponomarev.data.ext.addHttpLoggingInterceptor
import com.tinkoff.ponomarev.data.ext.addJsonConverter
import com.tinkoff.ponomarev.data.network.GifService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Модуль DI для объектов, связанных с сетью
 */
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    /**
     * Создание экземпляра [Retrofit]
     */
    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideRetrofitObject(): Retrofit =
        Retrofit.Builder()
            .baseUrl(NetworkConstant.BASE_URL)
            .addHttpLoggingInterceptor()
            .addJsonConverter()
            .build()

    /**
     * Создание экземпляра [GifService] при помощи [Retrofit]
     */
    @Provides
    @Singleton
    fun provideGifsService(retrofit: Retrofit): GifService =
        retrofit.create(GifService::class.java)
}