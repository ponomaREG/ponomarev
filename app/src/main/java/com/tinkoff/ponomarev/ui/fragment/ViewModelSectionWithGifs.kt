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

class ViewModelSectionWithGifs @AssistedInject constructor(
    private val getRandomGifUseCase: GetRandomGifUseCase,
    private val getGifsOfSectionUseCase: GetGifsOfSectionUseCase,
    @Assisted private val searchType: SearchType
): ViewModel() {

    companion object{
        fun provideFactory(
            assistedFactory: AssistedFactory,
            searchType: SearchType
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(searchType) as T
            }
        }
    }

    val currentUiState: LiveData<UIState>
    get() = _currentUiState

    private val _currentUiState: MutableLiveData<UIState> = MutableLiveData()
    private val loadedGifs:MutableList<Gif> = mutableListOf()
    private var currentNumberOfGif = -1
    private var currentPage = -1

    init {
        firstLoad()
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory{
        fun create(searchType: SearchType): ViewModelSectionWithGifs
    }

    fun ueOnNextGifButtonClick(){
        loadNextGif()
    }

    fun ueOnPreviouslyGifButtonClick(){
        loadPreviouslyGif()
    }

    private fun firstLoad(){
        loadNextGif()
    }

    private fun loadNextGif(){
        currentNumberOfGif++
        if(isGifInCache(currentNumberOfGif)){
            loadGifFromCache()
        }else loadGifFromNetwork()
    }

    private fun loadPreviouslyGif(){
        currentNumberOfGif--
        loadGifFromCache()
    }

    private fun loadGifFromCache(){
        emitData(loadedGifs[currentNumberOfGif])
    }

    private fun loadGifFromNetwork(){
        viewModelScope.launch(Dispatchers.IO){
            if(searchType == SearchType.RANDOM) {
                getRandomGifUseCase().collect { result ->
                    withContext(Dispatchers.Main) {
                        when (result) {
                            is BaseResult.Loading -> {
                                setUiStateLoading()
                            }
                            is BaseResult.Error -> {
                                val error =
                                    if(result.exception is Error) result.exception
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
            }else {
                getGifsOfSectionUseCase(searchType.sectionName, ++currentPage).collect { result ->
                    withContext(Dispatchers.Main) {
                        when (result) {
                            is BaseResult.Loading -> {
                                setUiStateLoading()
                            }
                            is BaseResult.Error -> {
                                val error =
                                    if(result.exception is Error) result.exception
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

    private fun isGifInCache(position: Int): Boolean{
        return loadedGifs.getOrNull(position) != null
    }

    private fun hasNextGif(): Boolean =
        isGifInCache(currentNumberOfGif + 1)

    private fun hasPreviouslyGif(): Boolean =
        currentNumberOfGif > 0

    private fun emitData(gif: Gif){
        _currentUiState.value =
            UIState(
                visibilityOfButtonPreviously = hasPreviouslyGif(),
                visibilityOfLoadingIndicator = false,
                visibilityOfButtonNext = true,
                currentGif = gif,
                currentError = null
            )
    }

    private fun emitError(error: Error){
        _currentUiState.value =
            UIState(
                visibilityOfButtonPreviously = hasPreviouslyGif(),
                visibilityOfLoadingIndicator = false,
                visibilityOfButtonNext = hasNextGif(),
                currentGif = null,
                currentError = error
            )
    }

    private fun setUiStateLoading(){
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