package com.tinkoff.ponomarev.domain.usecase

import com.tinkoff.ponomarev.domain.base.BaseResult
import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.domain.repository.GifRepository
import com.tinkoff.ponomarev.ui.error.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRandomGifUseCase @Inject constructor(
    private val repository: GifRepository
) {
    suspend operator fun invoke(): Flow<BaseResult<Gif>> = flow {
        emit(BaseResult.Loading)
        try{
            val gif = repository.fetchRandomGif()
            emit(BaseResult.Success(gif))
            //API периодически бросает null ссылку
            if(gif.gifURLHttps == null) emit(BaseResult.Error(Error.NullGifUrlError))
        }catch (exc: Throwable){
            emit(BaseResult.Error(exc))
        }
    }
}