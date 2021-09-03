package com.tinkoff.ponomarev.ui.fragment

import com.tinkoff.ponomarev.ui.error.Error

data class UIState(
    var visibilityOfButtonPreviously: Boolean,
    var visibilityOfLoadingIndicator: Boolean,
    var currentGifUrl: String?,
    var currentError: Error?
)
