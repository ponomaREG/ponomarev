package com.tinkoff.ponomarev.domain.usecase

import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.domain.repository.GifRepository
import javax.inject.Inject

class GetRandomGifUseCase @Inject constructor(
    private val repository: GifRepository
) {
    suspend operator fun invoke(): Gif {
        return repository.fetchRandomGif()
    }
}