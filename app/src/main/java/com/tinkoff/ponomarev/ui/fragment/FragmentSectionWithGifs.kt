package com.tinkoff.ponomarev.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.tinkoff.ponomarev.R
import com.tinkoff.ponomarev.databinding.FragmentPageBinding
import com.tinkoff.ponomarev.ui.entity.SearchType
import com.tinkoff.ponomarev.ui.error.Error
import com.tinkoff.ponomarev.ui.ext.loadGif
import com.tinkoff.ponomarev.ui.ext.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Фрагмент для отображения секции с гиф-изображениями
 * Отображает одну конкретную секцию из: [SearchType.RANDOM], [SearchType.TOP], [SearchType.HOT],
 * [SearchType.LATEST]
 */
@AndroidEntryPoint
class FragmentSectionWithGifs: Fragment() {

    /**
     * Assisted-фабрика для передачи [SearchType] в вьюмодель [ViewModelSectionWithGifs] при
     * ипользовании hilt
     */
    @Inject
    lateinit var viewModelAssistedFactory: ViewModelSectionWithGifs.AssistedFactory

    /**
     * Вьюбиндинг [R.layout.fragment_page]
     */
    private lateinit var binding: FragmentPageBinding

    /**
     * Вьюмодель, получаемая делегатом с Assisted-фабрикой и передаваемой [SearchType]
     */
    private val viewModel: ViewModelSectionWithGifs by viewModels {
        ViewModelSectionWithGifs.provideFactory(
            viewModelAssistedFactory,
            getCurrentTypeOfSearch()
        )
    }

    companion object{

        /**
         * Ключ для получения [SearchType] из bundle [getArguments]
         */
        const val KEY_TYPE = "KEY_TYPE"

        /**
         * Создание нового экземпляра фрагмента
         * @param searchType - тип секции(поиска)
         * @return - новый фрагмент
         */
        fun newInstance(searchType: SearchType): FragmentSectionWithGifs {
            val args = bundleOf(Pair(KEY_TYPE, searchType))
            val fragment = FragmentSectionWithGifs()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPageBinding.inflate(inflater, container, false)
        attachListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachObserversToDataSources()
    }

    /**
     * Получение типа поиска из [getArguments]
     */
    private fun getCurrentTypeOfSearch(): SearchType{
        return arguments?.getParcelable(KEY_TYPE) ?: SearchType.RANDOM
    }

    /**
     * Создание слушателей нажатий и присваивания ко вьюшкам
     * Все слушатели вызывают методы во вьюмоделе, так как именно она контролирует [UIState]
     * фрагмента
     */
    private fun attachListeners(){
        with(binding){
            fragmentPageButtonPreviously.setOnClickListener {
                viewModel.ueOnPreviouslyGifButtonClick()
            }
            fragmentPageButtonNext.setOnClickListener {
                viewModel.ueOnNextGifButtonClick()
            }
        }
    }

    /**
     * Наблюдение источником дата вьюмодели
     * Данный метод отвечает собственно за изменение фрагмента при изменении состояния [UIState]
     */
    private fun attachObserversToDataSources(){
        with(viewModel){
            currentUiState.observe(viewLifecycleOwner){ state ->
                binding.apply {
                    fragmentPageButtonPreviously.visible(state.visibilityOfButtonPreviously)
                    fragmentPageProgressIndicator.visible(state.visibilityOfLoadingIndicator)
                    state.currentGif?.let { gif ->
                        gif.gifURLHttps?.let { url -> fragmentPageGif.loadGif(url) }
                        fragmentPageGifTitle.text = gif.description
                    }
                    state.currentError?.let { error ->
                        showError(error)
                    }
                }
            }
        }
    }

    /**
     * Функция отображения ошибки [Error]
     * Здесь реализовывается необходимая логика для уведомления пользователя об ошибке
     * @param - полученная ошибка
     */
    private fun showError(error: Error){
        val message = when(error){
            is Error.EmptyResultError -> {
                binding.fragmentPageButtonNext.visible(false)
                getString(R.string.error_empty_result)
            }
            is Error.NullGifUrlError -> {
                getString(R.string.error_incorrect_input_data)
            }
            else -> {
                getString(R.string.error_unknown_error)
            }
        }
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}