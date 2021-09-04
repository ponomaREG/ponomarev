package com.tinkoff.ponomarev.domain.usecase

import com.tinkoff.ponomarev.domain.base.BaseResult
import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.domain.repository.GifRepository
import com.tinkoff.ponomarev.ui.error.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGifsOfSectionUseCase @Inject constructor(
    private val repository: GifRepository
) {
    suspend operator fun invoke(section: String, page: Int): Flow<BaseResult<List<Gif>>>  = flow {
        emit(BaseResult.Loading)
        try {
            val gifs = repository.fetchGifsBySection(section, page)
            if(gifs.isEmpty()) throw Error.EmptyResultError
            emit(BaseResult.Success(gifs))
        }catch (exc: Throwable){
            emit(BaseResult.Error(exc))
        }
    }
}