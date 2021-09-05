package com.tinkoff.ponomarev.data.network

import com.tinkoff.ponomarev.data.entity.network.GifBody
import com.tinkoff.ponomarev.data.entity.network.response.GifsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Класс, содержащий api-методы для получения данных из сети
 */
interface GifService {

    /**
     * Получение рандомной гифки
     * Пример запроса: https://developerslife.ru/random?json=true
     * @return [GifBody] гифка
     */
    @GET("random")
    suspend fun getRandomGif(
        @Query("json") json: Boolean = true
    ): GifBody

    /**
     * Получение страницы с гиф-изображениями из конкретной секции
     * Пример запроса: https://developerslife.ru/top/0?json=true
     * @param section - название секции (могут принимать значения "top", "hot", "latest")
     * @return [GifsResponse] ответ сервера с гифками
     * P.S. Не работает секция hot
     */
    @GET("{section}/{page}")
    suspend fun getGifsBySection(
        @Path("section") section: String,
        @Path("page") page: Int,
        @Query("json") json: Boolean = true
    ): GifsResponse
}