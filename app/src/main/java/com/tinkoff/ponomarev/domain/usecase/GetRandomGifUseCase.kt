package com.tinkoff.ponomarev.domain.usecase

import com.tinkoff.ponomarev.domain.base.BaseResult
import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.domain.repository.GifRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRandomGifUseCase @Inject constructor(
    private val repository: GifRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<Gif>> = flow {
        emit(BaseResult.Loading)
        try{
            emit(BaseResult.Success(repository.fetchRandomGif()))
        }catch (exc: Throwable){
            emit(BaseResult.Error(exc))
        }
    }
}