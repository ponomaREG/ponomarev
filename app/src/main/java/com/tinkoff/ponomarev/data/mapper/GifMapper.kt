package com.tinkoff.ponomarev.data.mapper

import com.tinkoff.ponomarev.data.entity.network.GifBody
import com.tinkoff.ponomarev.domain.entity.Gif
import javax.inject.Inject

class GifMapper @Inject constructor(

) {

    fun fromGifBody(gifBody: GifBody): Gif{
        return Gif(
            id = gifBody.id,
            description = gifBody.description,
            author = gifBody.author,
            gifURL = gifBody.gifURL
        )
    }
}