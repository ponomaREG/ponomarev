package com.tinkoff.ponomarev.domain.usecase

import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.domain.repository.GifRepository
import javax.inject.Inject

class GetGifsOfSectionUseCase @Inject constructor(
    private val repository: GifRepository
) {
    suspend operator fun invoke(section: String, page: Int): List<Gif> {
        return repository.fetchGifsBySection(section, page)
    }
}