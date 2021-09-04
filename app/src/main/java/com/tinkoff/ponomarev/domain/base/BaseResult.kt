package com.tinkoff.ponomarev.domain.base

/**
 * Класс-статус запроса в сеть
 * @property T - тип получаемых данных
 */
sealed class BaseResult<out T> {

    /**
     * Статус, что получена ошибка при попытке получения данных из сети
     * @param exception - полученная ошибка
     */
    data class Error(var exception: Throwable) : BaseResult<Nothing>()

    /**
     * Статус, что данные успешно получены
     * @param data - полученные данные
     * @param T - тип полученных данных
     */
    data class Success<T>(var data: T) : BaseResult<T>()

    /**
     * Статус, что данные загружаются
     */
    object Loading : BaseResult<Nothing>()
}
