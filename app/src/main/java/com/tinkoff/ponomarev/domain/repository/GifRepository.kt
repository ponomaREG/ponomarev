package com.tinkoff.ponomarev.domain.repository

import com.tinkoff.ponomarev.domain.entity.Gif

interface GifRepository{

    suspend fun fetchGifsBySection(section: String, page: Int): List<Gif>

    suspend fun fetchRandomGif(): Gif
}