package com.tinkoff.ponomarev.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.tinkoff.ponomarev.domain.repository.GifRepository
import com.tinkoff.ponomarev.rule.CoroutineTestRule
import com.tinkoff.ponomarev.ui.error.Error
import com.tinkoff.ponomarev.util.MockUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

/**
 * Класс тестирования сценария получения случайного гиф-изображения
 */
class TestGetRandomGifUseCase {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * Мокнутый репозиторий
     */
    private val gifRepository: GifRepository = mock()

    /**
     * Сам тестируемый сценарий
     */
    private val getRandomGifUseCase = GetRandomGifUseCase(gifRepository)

    /**
     * Тестирование выполнение сценария, если результат успешный
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testInvokeSuccess(): Unit = runBlockingTest{
        whenever(gifRepository.fetchRandomGif()).thenReturn(MockUtil.mockGif())
        val emits = getRandomGifUseCase.invoke().toList()
        assertEquals(
            emits,
            listOf(
                MockUtil.mockBaseResultLoading(),
                MockUtil.mockBaseResultSuccessGif()
            )
        )
    }

    /**
     * Тестирование выполнения сценария, если выбрасывается ошибка
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testInvokeUnknownError(): Unit = runBlockingTest{
        given(gifRepository.fetchRandomGif()).willAnswer{ throw Error.UnknownError }
        val emits = getRandomGifUseCase.invoke().toList()
        assertEquals(
            emits,
            listOf(
                MockUtil.mockBaseResultLoading(),
                MockUtil.mockBaseResultErrorUnknown()
            )
        )
    }

    /**
     * Тестирование выполнения сценария, если приходит гиф-изображение с null ссылкой
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testInvokeSuccessWithGifUrlNull(): Unit = runBlockingTest{
        whenever(gifRepository.fetchRandomGif())
            .thenReturn(MockUtil.mockGifWithNullUrl())
        val emits = getRandomGifUseCase.invoke().toList()
        assertEquals(
            emits,
            listOf(
                MockUtil.mockBaseResultLoading(),
                MockUtil.mockBaseResultSuccessGifWithNullUrl(),
                MockUtil.mockBaseResultErrorNullGifUrl()
            )
        )
    }
}