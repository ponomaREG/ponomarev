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
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/**
 * Класс тестирования сценарий по получению гиф-изображений определенной секции
 */
class TestGetGifsBySectionUseCase {

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
    private val getGifsOfSectionUseCase = GetGifsOfSectionUseCase(gifRepository)

    /**
     * Тестирование выполнения сценария, если все проходит успешно
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testInvokeSuccess(): Unit = runBlockingTest{
        whenever(gifRepository.fetchGifsBySection("top",0))
            .thenReturn(MockUtil.mockGifList())
        val emits = getGifsOfSectionUseCase.invoke("top",0).toList()
        Assert.assertEquals(
            emits,
            listOf(
                MockUtil.mockBaseResultLoading(),
                MockUtil.mockBaseResultSuccessListGif()
            )
        )
    }

    /**
     * Тестирование выполнения сценария, если происходит неизвестная ошибка
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testInvokeUnknownError(): Unit = runBlockingTest{
        given(gifRepository.fetchGifsBySection("top", 0))
            .willAnswer{ throw Error.UnknownError }
        val emits = getGifsOfSectionUseCase.invoke("top", 0).toList()
        Assert.assertEquals(
            emits,
            listOf(
                MockUtil.mockBaseResultLoading(),
                MockUtil.mockBaseResultErrorUnknown()
            )
        )
    }

    /**
     * Тестирование выполнение сценария, если приходит пустой список
     */
    @ExperimentalCoroutinesApi
    @Test
    fun testInvokeEmptyResultError(): Unit = runBlockingTest{
        whenever(gifRepository.fetchGifsBySection("top", 0))
            .thenReturn(emptyList())
        val emits = getGifsOfSectionUseCase.invoke("top", 0).toList()
        Assert.assertEquals(
            emits,
            listOf(
                MockUtil.mockBaseResultLoading(),
                MockUtil.mockBaseResultErrorEmptyResult()
            )
        )
    }
}