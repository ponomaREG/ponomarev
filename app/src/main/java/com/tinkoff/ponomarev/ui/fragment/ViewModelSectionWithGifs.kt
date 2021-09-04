package com.tinkoff.ponomarev.ui.fragment

import androidx.lifecycle.*
import com.tinkoff.ponomarev.domain.base.BaseResult
import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.domain.usecase.GetGifsOfSectionUseCase
import com.tinkoff.ponomarev.domain.usecase.GetRandomGifUseCase
import com.tinkoff.ponomarev.ui.entity.SearchType
import com.tinkoff.ponomarev.ui.error.Error
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Вьюмодель, которая управляет текущим состоянием View
 * @param getGifsOfSectionUseCase - сценарий получения секции с гиф-изображениями из сети
 * @param getRandomGifUseCase - сценарий получения рандомного гиф-изображения из сети
 * @param searchType - текущий тип поиска, от которого и зависит выбор нужного сценария для
 * получения гиф-изображений ([getRandomGifUseCase] или [getGifsOfSectionUseCase])
 */
class ViewModelSectionWithGifs @AssistedInject constructor(
    private val getRandomGifUseCase: GetRandomGifUseCase,
    private val getGifsOfSectionUseCase: GetGifsOfSectionUseCase,
    @Assisted private val searchType: SearchType
) : ViewModel() {

    companion object {
        /**
         * Метод для опрокидывания фабрики для вьюмодели. Нужен для assited-инжекта текущего
         * типа поиска [searchType] из фрагмента
         * @param assistedFactory - assisted-фабрика для вьюмодели
         * @param searchType - тип поиска
         */
        fun provideFactory(
            assistedFactory: AssistedFactory,
            searchType: SearchType
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(searchType) as T
            }
        }
    }

    /**
     * Публичный живые данные с текущим состоянием UI
     */
    val currentUiState: LiveData<UIState>
        get() = _currentUiState

    /**
     * Приватные мутабельные живые данные с текущим состоянием UI
     * Изменяя данное значение, происходит изменение UI-компонента(фрагмента)
     */
    private val _currentUiState: MutableLiveData<UIState> = MutableLiveData()

    /**
     * Приватный изменяемый лист с уже загруженными объектами гиф-изображений [Gif]
     */
    private val loadedGifs: MutableList<Gif> = mutableListOf()

    /**
     * Текущий номер гиф-изображения
     */
    private var currentNumberOfGif = -1

    /**
     * Текущая страница. Изменяется только, когда используется сценарий поиска гиф-изображений
     * по секции [GetGifsOfSectionUseCase]
     */
    private var currentPage = -1

    /**
     * Блок, исполняемый при иницилизации вьюмодели
     */
    init {
        firstLoad()
    }

    /**
     * Интерфейс assisted-фабрики, используемый при [provideFactory]
     * и [FragmentSectionWithGifs.viewModelAssistedFactory]
     */
    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(searchType: SearchType): ViewModelSectionWithGifs
    }

    /**
     * Публичный метод пользовательского взаимодействия (ue = userEvent)
     * Вызывается при нажатии пользователем на кнопку следующего гиф-изображения
     * Вызывает приватный метод [loadNextGif]
     */
    fun ueOnNextGifButtonClick() {
        loadNextGif()
    }

    /**
     * Публичный метод пользовательского взаимодействия (ue = userEvent)
     * Вызывается при нажатии пользователем на кнопку предыдущего гиф-изображения
     * Вызывает приватный метод [loadPreviouslyGif]
     */
    fun ueOnPreviouslyGifButtonClick() {
        loadPreviouslyGif()
    }

    /**
     * Функция, вызываемая в блоке иницилизации вьюмодели, то есть при первом запуске
     * Нужна для загрузки первоначального гиф-изображения без взаимодействия со стороны
     * пользователя
     * Вызывает [loadNextGif]
     */
    private fun firstLoad() {
        loadNextGif()
    }

    /**
     * Приватная функция загрузки следующего гиф-изображения
     * Инкрементит текущий индекс, следом проверяет путем функции [isGifInCache]
     * находится ли гиф-изображением с позицией [currentNumberOfGif] в [loadedGifs]
     * Если же находится вызывается [loadGifFromCache], если нет [loadGifFromNetwork]
     */
    private fun loadNextGif() {
        currentNumberOfGif++
        if (isGifInCache(currentNumberOfGif)) {
            loadGifFromCache()
        } else loadGifFromNetwork()
    }

    /**
     * Приватная функция загрузки предыдущего гиф-изображения
     * Декрементит текущий индекс, следом загружает гиф-изображение из кеша, так как
     * если пользователь нажимает на кнопку предыдущего изображения, то это означает, что оно уже
     * было загружено
     */
    private fun loadPreviouslyGif() {
        currentNumberOfGif--
        loadGifFromCache()
    }

    /**
     * Загрузка гиф-изображения из [loadedGifs] и его отображение через [emitData]
     */
    private fun loadGifFromCache() {
        emitData(loadedGifs[currentNumberOfGif])
    }

    /**
     * Получение объекта [BaseResult] из сети путем [getRandomGifUseCase]
     * или [getGifsOfSectionUseCase] в зависимости от [searchType]
     * В зависимости от типа полученного результата производится соответствено:
     * Если [BaseResult.Error] - изменение [UIState] с ошибкой
     * Если [BaseResult.Loading] - изменение [UIState] с видимостью прогресс индикатора
     * Если [BaseResult.Success] - изменение [UIState] с полем [UIState.currentGif]
     */
    private fun loadGifFromNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            if (searchType == SearchType.RANDOM) {
                getRandomGifUseCase().collect { result ->
                    withContext(Dispatchers.Main) {
                        when (result) {
                            is BaseResult.Loading -> {
                                setUiStateLoading()
                            }
                            is BaseResult.Error -> {
                                val error =
                                    if (result.exception is Error) result.exception
                                    else Error.UnknownError
                                emitError(error as Error)
                            }
                            is BaseResult.Success -> {
                                loadedGifs.add(result.data)
                                emitData(result.data)
                            }
                        }
                    }
                }
            } else {
                getGifsOfSectionUseCase(searchType.sectionName, ++currentPage).collect { result ->
                    withContext(Dispatchers.Main) {
                        when (result) {
                            is BaseResult.Loading -> {
                                setUiStateLoading()
                            }
                            is BaseResult.Error -> {
                                val error =
                                    if (result.exception is Error) result.exception
                                    else Error.UnknownError
                                emitError(error as Error)
                            }
                            is BaseResult.Success -> {
                                loadedGifs.addAll(result.data)
                                emitData(result.data.first())
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Проверка существует ли [Gif] в [loadedGifs]
     * на текущей позиции [currentNumberOfGif]
     * @param position - текущий номер гиф-изображения
     * @return - Есть или нет
     */
    private fun isGifInCache(position: Int): Boolean {
        return loadedGifs.getOrNull(position) != null
    }

    /**
     * Приватный метод для определения существования гиф-изображения в [loadedGifs] на
     * следующей позиции после текущей [currentNumberOfGif] + 1
     */
    private fun hasNextGif(): Boolean =
        isGifInCache(currentNumberOfGif + 1)

    /**
     * Приватный метод для определения существования гиф-изображение
     * Проверяет [currentNumberOfGif] больше ли 0
     */
    private fun hasPreviouslyGif(): Boolean =
        currentNumberOfGif > 0

    /**
     * Изменение [_currentUiState] с новым [UIState] с новым гиф-изображением для отображением
     * Вызывается при успешном получении гифки из сети
     * @param gif - полученное гиф-изображение
     */
    private fun emitData(gif: Gif) {
        _currentUiState.value =
            UIState(
                visibilityOfButtonPreviously = hasPreviouslyGif(),
                visibilityOfLoadingIndicator = false,
                visibilityOfButtonNext = true,
                currentGif = gif,
                currentError = null
            )
    }

    /**
     * Изменение [_currentUiState] с ошибкой [Error]
     * Вызывается при получении ошибки при попытке получить гифку из сети
     * @param error - полученная ошибка
     */
    private fun emitError(error: Error) {
        _currentUiState.value =
            UIState(
                visibilityOfButtonPreviously = hasPreviouslyGif(),
                visibilityOfLoadingIndicator = false,
                visibilityOfButtonNext = hasNextGif(),
                currentGif = null,
                currentError = error
            )
    }

    /**
     * Изменение [_currentUiState] со статусом об загрузке
     * Вызывается при начале загрузки гиф-изображения из сети
     */
    private fun setUiStateLoading() {
        _currentUiState.value =
            UIState(
                visibilityOfButtonPreviously = hasPreviouslyGif(),
                visibilityOfLoadingIndicator = true,
                visibilityOfButtonNext = true,
                currentGif = null,
                currentError = null
            )
    }

}