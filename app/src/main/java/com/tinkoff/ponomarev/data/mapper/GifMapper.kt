package com.tinkoff.ponomarev.data.mapper

import com.tinkoff.ponomarev.data.entity.network.GifBody
import com.tinkoff.ponomarev.domain.entity.Gif
import javax.inject.Inject

/**
 * Класс, который конвертирует класс [GifBody] гифки слоя сети в класс [Gif] гифки слоя domain
 */
class GifMapper @Inject constructor(

) {

    /**
     * Из [GifBody] в [Gif]
     */
    fun fromGifBody(gifBody: GifBody): Gif{
        return Gif(
            id = gifBody.id,
            description = gifBody.description,
            author = gifBody.author,
            gifURL = gifBody.gifURL
        )
    }
}