package com.tinkoff.ponomarev.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.ui.entity.SearchType
import com.tinkoff.ponomarev.ui.error.Error
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

class ViewModelSectionWithGifs @AssistedInject constructor(
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
    }

    private fun loadGifFromCache(){
        emitData(loadedGifs[currentNumberOfGif])
    }

    private fun loadGifFromNetwork(){

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
                visibilityOfLoadingIndicator = hasNextGif(),
                currentGifUrl = gif.gifURL,
                currentError = null
            )
    }

    private fun emitData(error: Error){
        _currentUiState.value =
            UIState(
                visibilityOfButtonPreviously = hasPreviouslyGif(),
                visibilityOfLoadingIndicator = hasNextGif(),
                currentGifUrl = null,
                currentError = error
            )
    }

}