package com.tinkoff.ponomarev.util

import com.tinkoff.ponomarev.data.entity.network.GifBody
import com.tinkoff.ponomarev.domain.entity.Gif

class MockUtil {
    companion object {
        fun mockGif(): Gif =
            Gif(
                id = 1,
                description = "description",
                author = "author",
                gifURL = "http://gifurl.com"
            )


        fun mockGifWithNullUrl(): Gif =
            Gif(
                id = 1,
                description = "description",
                author = "author",
                gifURL = null
            )


        fun mockGifBody(): GifBody =
            GifBody(
                id = 1,
                description = "description",
                author = "author",
                gifURL = "http://gifurl.com"
            )

        fun mockGifBodyWithNullGifURL(): GifBody =
            GifBody(
                id = 1,
                description = "description",
                author = "author",
                gifURL = null
            )
    }
}