package com.tinkoff.ponomarev.data.entity.network.response

import com.tinkoff.ponomarev.data.entity.network.GifBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Класс-результат запроса гиф-изображений из определнных секции
 * @param result - полученные гиф-изображения
 */
@Serializable
data class GifsResponse(
    @SerialName("result") val result: List<GifBody>
)