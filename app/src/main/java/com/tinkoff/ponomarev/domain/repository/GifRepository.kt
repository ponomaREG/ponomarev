package com.tinkoff.ponomarev.domain.repository

import com.tinkoff.ponomarev.domain.entity.Gif

/**
 * Интерфейс репозитория для получения данных
 */
interface GifRepository {

    /**
     * Получение гиф-изображений конкретной секции
     * @param section - секция (hot, latest, top)
     * @param page - страница
     * @return - список с гиф-изображениями
     */
    suspend fun fetchGifsBySection(section: String, page: Int): List<Gif>

    /**
     * Получение рандомного гиф-изображения из сети
     * @return - гиф-изображение
     */
    suspend fun fetchRandomGif(): Gif
}