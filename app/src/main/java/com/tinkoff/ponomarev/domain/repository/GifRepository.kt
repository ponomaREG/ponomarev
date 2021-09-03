package com.tinkoff.ponomarev.domain.repository

import com.tinkoff.ponomarev.domain.entity.Gif

interface GifRepository{

    suspend fun fetchTopGifs(page: Int): List<Gif>

    suspend fun fetchLatestGifs(page: Int): List<Gif>

    suspend fun fetchHottestGifs(page: Int): List<Gif>

    suspend fun fetchRandomGif(): Gif
}