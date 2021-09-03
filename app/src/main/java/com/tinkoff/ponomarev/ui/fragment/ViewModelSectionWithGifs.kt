package com.tinkoff.ponomarev.ui.fragment

import androidx.lifecycle.*
import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.domain.usecase.GetGifsOfSectionUseCase
import com.tinkoff.ponomarev.domain.usecase.GetRandomGifUseCase
import com.tinkoff.ponomarev.ui.entity.SearchType
import com.tinkoff.ponomarev.ui.error.Error
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

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
        setUiStateLoading()
        viewModelScope.launch(Dispatchers.IO){
            if(searchType == SearchType.RANDOM) {
                val gif = getRandomGifUseCase()
                loadedGifs.add(gif)
                withContext(Dispatchers.Main) {
                    emitData(gif)
                }
            }else {
                val gifs = getGifsOfSectionUseCase(searchType.sectionName, ++currentPage)
                loadedGifs.addAll(gifs)
                withContext(Dispatchers.Main){
                    emitData(gifs.first())
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
                currentGifUrl = gif.gifURLHttps,
                currentError = null
            )
    }

    private fun emitError(error: Error){
        _currentUiState.value =
            UIState(
                visibilityOfButtonPreviously = hasPreviouslyGif(),
                visibilityOfLoadingIndicator = false,
                visibilityOfButtonNext = hasNextGif(),
                currentGifUrl = null,
                currentError = error
            )
    }

    private fun setUiStateLoading(){
        _currentUiState.value =
            UIState(
                visibilityOfButtonPreviously = hasPreviouslyGif(),
                visibilityOfLoadingIndicator = true,
                visibilityOfButtonNext = true,
                currentGifUrl = null,
                currentError = null
            )
    }

}