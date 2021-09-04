package com.tinkoff.ponomarev.data.repository

import com.tinkoff.ponomarev.data.mapper.GifMapper
import com.tinkoff.ponomarev.data.network.GifService
import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.domain.repository.GifRepository
import javax.inject.Inject

/**
 * Имплементация интерфейса репозитория [GifRepository]
 * @param gifMapper - класс-конвертер сущностей гифок
 * @param gifService - сервис, содержащий методы для получения данных из сети
 */
class GifRepositoryImpl @Inject constructor(
    private val gifService: GifService,
    private val gifMapper: GifMapper
) : GifRepository {

    /**
     * @see GifRepository.fetchGifsBySection
     */
    override suspend fun fetchGifsBySection(
        section: String,
        page: Int
    ): List<Gif> {
        return gifService.getGifsBySection(section, page).result.map(gifMapper::fromGifBody)
    }

    /**
     * @see GifRepository.fetchRandomGif
     */
    override suspend fun fetchRandomGif(): Gif {
        return gifMapper.fromGifBody(gifService.getRandomGif())
    }
}