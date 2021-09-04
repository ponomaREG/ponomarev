package com.tinkoff.ponomarev.ui.fragment

import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.ui.error.Error

data class UIState(
    var visibilityOfButtonPreviously: Boolean,
    var visibilityOfLoadingIndicator: Boolean,
    var visibilityOfButtonNext: Boolean,
    var currentGif: Gif?,
    var currentError: Error?
)
