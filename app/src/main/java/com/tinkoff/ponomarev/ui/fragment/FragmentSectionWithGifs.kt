package com.tinkoff.ponomarev.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tinkoff.ponomarev.databinding.FragmentPageBinding
import com.tinkoff.ponomarev.ui.entity.SearchType
import com.tinkoff.ponomarev.ui.ext.loadGif
import com.tinkoff.ponomarev.ui.ext.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentSectionWithGifs: Fragment() {

    @Inject
    lateinit var viewModelAssistedFactory: ViewModelSectionWithGifs.AssistedFactory
    private lateinit var binding: FragmentPageBinding
    private val viewModel: ViewModelSectionWithGifs by viewModels {
        ViewModelSectionWithGifs.provideFactory(
            viewModelAssistedFactory,
            getCurrentTypeOfSearch()
        )
    }

    companion object{
        const val KEY_TYPE = "KEY_TYPE"
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

    private fun getCurrentTypeOfSearch(): SearchType{
        return arguments?.getParcelable(KEY_TYPE) ?: SearchType.RANDOM
    }

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

    private fun attachObserversToDataSources(){
        with(viewModel){
            currentUiState.observe(viewLifecycleOwner){ state ->
                binding.apply {
                    fragmentPageButtonPreviously.visible(state.visibilityOfButtonPreviously)
                    fragmentPageProgressIndicator.visible(state.visibilityOfLoadingIndicator)
                    state.currentGif?.let {
                        fragmentPageGif.loadGif(it.gifURL)
                        fragmentPageGifTitle.text = it.description
                    }
                }
            }
        }
    }
}