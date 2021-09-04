package com.tinkoff.ponomarev.data.entity.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Сущность гиф-изображения, полученная из сети
 * @param id - идентификатор гиф-изображения
 * @param description - описание гифки
 * @param author - автор гифки
 * @param gifURL - ссылка на изображение
 */
@Serializable
data class GifBody(
    @SerialName("id") val id:Int,
    @SerialName("description") val description: String,
    @SerialName("author") val author: String,
    @SerialName("gifURL") val gifURL: String?
)
