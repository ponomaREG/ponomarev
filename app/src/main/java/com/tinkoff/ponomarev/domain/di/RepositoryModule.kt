package com.tinkoff.ponomarev.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.tinkoff.ponomarev.data.repository.GifRepositoryImpl
import com.tinkoff.ponomarev.domain.repository.GifRepository
import javax.inject.Singleton

/**
 * Модуль DI для репозиториев
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    /**
     * Конвертирует [GifRepositoryImpl] в [GifRepository], чтобы работать именно с интерфейсом
     */
    @Binds
    @Singleton
    abstract fun bindGifRepository(gifRepositoryImpl: GifRepositoryImpl): GifRepository
}