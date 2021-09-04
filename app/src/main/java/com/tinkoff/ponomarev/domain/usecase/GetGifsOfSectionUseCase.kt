package com.tinkoff.ponomarev.domain.usecase

import com.tinkoff.ponomarev.domain.base.BaseResult
import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.domain.repository.GifRepository
import com.tinkoff.ponomarev.ui.error.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Сценарий для получения гиф-изображения из сети для конкретной секции
 * @param repository - репозиторий для получения гиф-изображений
 */
class GetGifsOfSectionUseCase @Inject constructor(
    private val repository: GifRepository
) {

    /**
     * Метод запуска сценария
     * @param section - запрашиваемая секция (hot,latest,top)
     * @param page - текущая страница
     * @return - [Flow] с классом-статусом запроса [BaseResult]
     * Функция сначала емиттит [BaseResult.Loading] со статусом загрузки
     * Следом в зависимости от статуса запроса емиттит либо ошибку [BaseResult.Error],
     * либо полученные данные [BaseResult.Success]
     */
    suspend operator fun invoke(section: String, page: Int): Flow<BaseResult<List<Gif>>>  = flow {
        emit(BaseResult.Loading)
        try {
            val gifs = repository.fetchGifsBySection(section, page)
            if(gifs.isEmpty()) throw Error.EmptyResultError // Если результат пустой, то ошибка
            emit(BaseResult.Success(gifs))
        }catch (exc: Throwable){
            emit(BaseResult.Error(exc))
        }
    }
}