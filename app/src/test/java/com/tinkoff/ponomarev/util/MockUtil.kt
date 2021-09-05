package com.tinkoff.ponomarev.util

import com.tinkoff.ponomarev.data.entity.network.GifBody
import com.tinkoff.ponomarev.data.entity.network.response.GifsResponse
import com.tinkoff.ponomarev.domain.base.BaseResult
import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.ui.entity.SearchType
import com.tinkoff.ponomarev.ui.error.Error
import com.tinkoff.ponomarev.ui.fragment.UIState

/**
 * Класс для искусственого создания экземпляров
 */
class MockUtil {
    companion object {

        /**
         * Создания экземпляра [Gif] гиф-изображения
         */
        fun mockGif(): Gif =
            Gif(
                id = 1,
                description = "description",
                author = "author",
                gifURL = "http://gifurl.com"
            )

        /**
         * Создания экземпляра [Gif] списка гиф-изображений
         */
        fun mockGifList(): List<Gif> =
            listOf(mockGif())


        /**
         * Создания экземпляра [Gif] гиф-изображения с null ссылкой на изображение
         */
        fun mockGifWithNullUrl(): Gif =
            Gif(
                id = 1,
                description = "description",
                author = "author",
                gifURL = null
            )


        /**
         * Создания экземпляра [GifBody] гиф-изображения
         */
        fun mockGifBody(): GifBody =
            GifBody(
                id = 1,
                description = "description",
                author = "author",
                gifURL = "http://gifurl.com"
            )

        /**
         * Создания экземпляра [GifsResponse] ответа сервера с гиф-изображениями
         */
        fun mockGifsResponse(): GifsResponse =
            GifsResponse(
                result = mockGifBodyList()
            )

        /**
         * Создания экземпляра [GifBody] списка гиф-изображений
         */
        fun mockGifBodyList(): List<GifBody> =
            listOf(mockGifBody())

        /**
         * Создание экземпляра [GifBody] с null ссылкой
         */
        fun mockGifBodyWithNullGifURL(): GifBody =
            GifBody(
                id = 1,
                description = "description",
                author = "author",
                gifURL = null
            )

        /**
         * Создание экземпляра [SearchType.RANDOM]
         */
        fun mockSearchTypeRandom(): SearchType =
            SearchType.RANDOM

        /**
         * Создание экземпляра [BaseResult] с успешныи результатом
         */
        fun mockBaseResultSuccessGif(): BaseResult<Gif> =
            BaseResult.Success(mockGif())

        /**
         * Создание экземпляра [BaseResult] с успешным результатом и гифкой с null ссылкой
         */
        fun mockBaseResultSuccessGifWithNullUrl(): BaseResult<Gif> =
            BaseResult.Success(mockGifWithNullUrl())

        /**
         * Создание экземпляра [BaseResult] со списком гиф-изображений
         */
        fun mockBaseResultSuccessListGif(): BaseResult<List<Gif>> =
            BaseResult.Success(mockGifList())

        /**
         * Создание экземпляра [BaseResult] со статусом загрузки
         */
        fun mockBaseResultLoading(): BaseResult<Nothing> = BaseResult.Loading

        /**
         * Создание экземпляра [BaseResult] с неизвестной ошибкой
         */
        fun mockBaseResultErrorUnknown(): BaseResult<Nothing> =
            BaseResult.Error(Error.UnknownError)

        /**
         * Создание экземпляра [BaseResult] с ошибкой - пустой результат
         */
        fun mockBaseResultErrorEmptyResult(): BaseResult<Nothing> =
            BaseResult.Error(Error.EmptyResultError)

        /**
         * Создание экземпляра [BaseResult] с ошибкой - гиф-изображение с null ссылкой
         */
        fun mockBaseResultErrorNullGifUrl(): BaseResult<Nothing> =
            BaseResult.Error(Error.NullGifUrlError)

        /**
         * Создание экземпляра [UIState] состояния UI загрузки при показе первого гиф-иозбражения
         */
        fun mockUIStateLoadingWithoutPreviouslyButton(): UIState =
            UIState(
                visibilityOfButtonPreviously = false,
                visibilityOfLoadingIndicator = true,
                visibilityOfButtonNext = true,
                currentGif = null,
                currentError = null
            )

        /**
         * Создание экземпляра [UIState] состояния UI загрузки
         */
        fun mockUIStateLoading(): UIState =
            UIState(
                visibilityOfButtonPreviously = true,
                visibilityOfLoadingIndicator = true,
                visibilityOfButtonNext = true,
                currentGif = null,
                currentError = null
            )
    }
}