package com.tinkoff.ponomarev.domain.usecase

import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.domain.repository.GifRepository
import javax.inject.Inject

class GetHottestGifsUseCase @Inject constructor(
    private val repository: GifRepository
) {
    suspend operator fun invoke(page: Int): List<Gif> =
        repository.fetchHottestGifs(page)
}