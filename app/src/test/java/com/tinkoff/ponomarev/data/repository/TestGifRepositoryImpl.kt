package com.tinkoff.ponomarev.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tinkoff.ponomarev.data.mapper.GifMapper
import com.tinkoff.ponomarev.data.network.GifService
import com.tinkoff.ponomarev.util.MockUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

/**
 * Класс тестирования для класс [GifRepositoryImpl] по получению гиф-изображений
 */
class TestGifRepositoryImpl {

    /**
     * Мокнутый api-сервис
     */
    private val gifService: GifService = mock()

    /**
     * Конвертер сущностей
     */
    private val gifMapper: GifMapper = GifMapper()

    /**
     * Создание тестируемого экземпляра
     */
    private val gifRepositoryImpl = GifRepositoryImpl(
        gifService,
        gifMapper
    )

    /**
     * Тестирование функции получения рандомного гиф-изображения
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testFetchRandomGif(): Unit = runBlockingTest{
        whenever(gifService.getRandomGif()).thenReturn(MockUtil.mockGifBody())
        val gifFromRepository = gifRepositoryImpl.fetchRandomGif()
        Assert.assertEquals(MockUtil.mockGif(), gifFromRepository)
    }

    /**
     * Тестирование функции получения гиф-изображений определенной секции
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testFetchGifsBySection(): Unit = runBlockingTest{
        whenever(gifService.getGifsBySection("top", 0))
            .thenReturn(MockUtil.mockGifsResponse())
        val gifsFromRepository = gifRepositoryImpl.fetchGifsBySection("top", 0)
        Assert.assertEquals(MockUtil.mockGifList(), gifsFromRepository)
    }
}