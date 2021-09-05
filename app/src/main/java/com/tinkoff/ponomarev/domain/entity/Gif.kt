package com.tinkoff.ponomarev.domain.entity

/**
 * Сущность гиф-изображения уровня domain и ui
 * @param id - идентификатор гиф-изображения
 * @param description - описание гифки
 * @param author - автор гифки
 * @param gifURL - ссылка на изображение
 * @property gifURLHttps - api предоставляет ссылку [gifURL] с http, а так как
 * glide ругается на cleartext, пришлось реализовать это поле, которое меняет http на https
 */
data class Gif(
    val id: Int,
    val description: String,
    val author: String,
    val gifURL: String?
) {
    val gifURLHttps: String?
        get() {
            return gifURL?.replace("http", "https")
        }
}
