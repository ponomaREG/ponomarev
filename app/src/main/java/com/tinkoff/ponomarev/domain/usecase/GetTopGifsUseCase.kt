package com.tinkoff.ponomarev.domain.usecase

import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.domain.repository.GifRepository
import javax.inject.Inject

class GetTopGifsUseCase @Inject constructor(
    private val repository: GifRepository
) {
    suspend operator fun invoke(page: Int): List<Gif> {
        return repository.fetchTopGifs(page)
    }
}