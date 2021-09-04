package com.tinkoff.ponomarev.ui.error

/**
 * Класс, который отображает ошибку, полученную при попытке получить гиф-изображение из сети
 * @property EmptyResultError - ошибка: получен пустой результат (появляется при попытке загрузить
 * секцию hot)
 * @property NullGifUrlError - ошибка: не получена url-ссылка гиф-изображения (периодически
 * отсутствует ссылка при ответе сервера (видимо проблема на их стороне))
 * @property UnknownError - ошибка: неизвестная ошибка
 */
sealed class Error: Throwable(){
    object EmptyResultError: Error()
    object UnknownError: Error()
    object NullGifUrlError: Error()
}
