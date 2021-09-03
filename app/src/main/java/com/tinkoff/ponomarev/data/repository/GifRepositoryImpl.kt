package com.tinkoff.ponomarev.data.repository

import com.tinkoff.ponomarev.data.mapper.GifMapper
import com.tinkoff.ponomarev.data.network.GifService
import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.domain.repository.GifRepository
import javax.inject.Inject

class GifRepositoryImpl @Inject constructor(
    private val gifService: GifService,
    private val gifMapper: GifMapper
): GifRepository {

    override suspend fun fetchTopGifs(page: Int): List<Gif> {
        return gifService.getTopGifs(page).result.map(gifMapper::fromGifBody)
    }

    override suspend fun fetchLatestGifs(page: Int): List<Gif> {
        return gifService.getLatestGifs(page).result.map(gifMapper::fromGifBody)
    }

    override suspend fun fetchHottestGifs(page: Int): List<Gif> {
        return gifService.getHottestGifs(page).result.map(gifMapper::fromGifBody)
    }

    override suspend fun fetchRandomGif(): Gif {
        return gifMapper.fromGifBody(gifService.getRandomGif())
    }
}