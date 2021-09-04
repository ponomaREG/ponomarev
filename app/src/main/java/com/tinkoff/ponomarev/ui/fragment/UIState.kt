package com.tinkoff.ponomarev.ui.fragment

import com.tinkoff.ponomarev.domain.entity.Gif
import com.tinkoff.ponomarev.ui.error.Error

/**
 * Класс, хранящий текущее состояние вью фрагмента
 * @param visibilityOfButtonNext - видимость кнопки "Следующая гифка"
 * @param visibilityOfButtonPreviously - видимость кнопки "Предыдущая гифка"
 * @param visibilityOfLoadingIndicator - видимость индикатора загрузки данных
 * @param currentError - текущая ошибка
 * @param currentGif - текущее гиф-изображение для отображения
 */
data class UIState(
    var visibilityOfButtonPreviously: Boolean,
    var visibilityOfLoadingIndicator: Boolean,
    var visibilityOfButtonNext: Boolean,
    var currentGif: Gif?,
    var currentError: Error?
)
